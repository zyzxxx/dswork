package dswork.android.lib.core.util.bmputil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import dswork.android.lib.core.util.DiskLruCache;
import dswork.android.lib.core.util.MD5Util;
import dswork.android.lib.core.util.WorkUtil;
import dswork.android.lib.core.util.webutil.HttpActionObj;
import dswork.android.lib.core.util.webutil.HttpUtil;

public class BitmapLoader
{
	public static LruCache<String, Bitmap> mMemoryCache = null;
	public static DiskLruCache mDiskLruCache = null;

	public static Set<BitmapLoadTask> taskCollection =  new HashSet<BitmapLoadTask>() ;
	
	/**
	 * 创建图片缓存
	 * @param ctx
	 * @param divisor 设置图片缓存大小为程序最大可用内存的1/n
	 */
	public static void createBitmapCache(Context ctx, int divisor)
	{
        // 获取应用程序最大可用内存  
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        System.out.println("app可用内存："+(maxMemory/1024/1024)+" MB");
        int cacheSize = maxMemory / 8;  
        System.out.println("可用图片缓存："+(cacheSize/1024/1024)+" MB");
        // 设置图片缓存大小为程序最大可用内存的1/8  
        BitmapLoader.mMemoryCache = new LruCache<String, Bitmap>(cacheSize) 
        {  
            @Override  
            protected int sizeOf(String key, Bitmap bitmap) 
            {  
                return bitmap.getByteCount();  
            }  
        };  
		// 创建DiskLruCache实例
		BitmapLoader.openDisCacheDir(ctx);
	}
	
	 /** 
     * 将一张图片存储到LruCache中。 
     *  
     * @param key 
     *            LruCache的键，这里传入图片的URL地址。 
     * @param bitmap 
     *            LruCache的键，这里传入从网络上下载的Bitmap对象。 
     */  
    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) 
    {  
        if (getBitmapFromMemoryCache(key) == null)
            mMemoryCache.put(key, bitmap);  
    }  
  
    /** 
     * 从LruCache中获取一张图片，如果不存在就返回null。 
     *  
     * @param key 
     *            LruCache的键，这里传入图片的URL地址。 
     * @return 对应传入键的Bitmap对象，或者null。 
     */  
    public static Bitmap getBitmapFromMemoryCache(String key) 
    {  
        return mMemoryCache.get(key);  
    } 
	
	/**
	 * 从网络图片存储到磁盘缓存
	 * @param imageUrl
	 */
	public static void writeToDiskLruCacheFromServer(String imageUrl)
	{
		try
		{
			String key = MD5Util.hashKeyForDisk(imageUrl);  
	        DiskLruCache.Editor editor = mDiskLruCache.edit(key);  
	        if (editor != null) 
	        {  
	            OutputStream outputStream = editor.newOutputStream(0);  
	            if (downloadUrlToStream(imageUrl, outputStream))
	            {
	            	System.out.println("------------->editor.commit()");
	                editor.commit();
	            }
	            else
	            {
	            	System.out.println("------------->editor.abort()");
	                editor.abort();
	            }
	        }  
	        mDiskLruCache.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取磁盘缓存图片
	 * @param imageUrl
	 * @return
	 */
	@SuppressWarnings("finally")
	public static Bitmap readFromDiskLruCache(String imageUrl)
	{
		Bitmap bmp = null;
		try
		{  
		    String key = MD5Util.hashKeyForDisk(imageUrl);  
		    DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);  
		    if (snapShot != null) 
		    {  
		        InputStream is = snapShot.getInputStream(0);  
		        bmp = BitmapFactory.decodeStream(is); 
		    }  
		} 
		catch (IOException e) 
		{  
		    e.printStackTrace();
		}  
		finally
		{
			return bmp;
		}
	}
	
	/**
	 * 移除磁盘图片缓存
	 * @param imageUrl
	 */
	public static void removeFromDiskLruCache(String imageUrl)
	{
		try 
		{  
		    String key = MD5Util.hashKeyForDisk(imageUrl);    
		    mDiskLruCache.remove(key);  
		} 
		catch (IOException e) 
		{  
		    e.printStackTrace();  
		}
	}
	
	/** 
     * 将缓存记录同步到journal文件中。 
     */
	public static void fluchCache() 
	{  
        if (mDiskLruCache != null) 
        {  
            try 
            {  
                mDiskLruCache.flush();  
            } 
            catch (IOException e) 
            {  
                e.printStackTrace();  
            }  
        }  
    } 
	
	/**
	 * 获取图片缓存路径
	 * @param context
	 * @param uniqueName
	 * @return
	 */
	public static File getDiskCacheDir(Context context, String uniqueName) 
	{
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) 
		{
			// path: /sdcard/Android/data/<application package>/cache 
			cachePath = context.getExternalCacheDir().getPath();
		} 
		else 
		{
			// path: /data/data/<application package>/cache
			cachePath = context.getCacheDir().getPath();
		}
		System.out.println("cachePath:"+cachePath);
		return new File(cachePath + File.separator + uniqueName);
	}

	/**
	 * 创建DiskLruCache的实例
	 * @param ctx
	 */
	public static void openDisCacheDir(Context ctx)
	{
		try 
		{
	        // 获取图片缓存路径  
			File cacheDir = getDiskCacheDir(ctx, "bitmap");
			if (!cacheDir.exists()) cacheDir.mkdirs();
			// 创建DiskLruCache实例，初始化缓存数据  
			mDiskLruCache = DiskLruCache.open(cacheDir, WorkUtil.getAppVersion(ctx), 1, 10 * 1024 * 1024);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 计算图片压缩的SampleSize
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
	{  
	    // 源图片的高度和宽度  
	    final int height = options.outHeight;  
	    final int width = options.outWidth;  
	    int inSampleSize = 1;  
	    if (height > reqHeight || width > reqWidth)
	    {  
	        // 计算出实际宽高和目标宽高的比率  
	        final int heightRatio = Math.round((float) height / (float) reqHeight);  
	        final int widthRatio = Math.round((float) width / (float) reqWidth);  
	        // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高  
	        // 一定都会大于等于目标的宽和高。  
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;  
	    }
	    System.out.println("inSampleSize is "+inSampleSize);
	    return inSampleSize;  
	}
	
	/**
	 * 压缩图片
	 * @param fd FileDescriptor
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(FileDescriptor fd, int reqWidth, int reqHeight) 
	{  
	    // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小  
	    final BitmapFactory.Options options = new BitmapFactory.Options();  
	    options.inJustDecodeBounds = true;  
	    BitmapFactory.decodeFileDescriptor(fd, null, options);  
	    // 调用上面定义的方法计算inSampleSize值  
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);  
	    // 使用获取到的inSampleSize值再次解析图片  
	    options.inJustDecodeBounds = false;  
	    return BitmapFactory.decodeFileDescriptor(fd, null, options);
	}
	/**
	 * 压缩图片
	 * @param res
	 * @param resId
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) 
	{  
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小  
		final BitmapFactory.Options options = new BitmapFactory.Options();  
		options.inJustDecodeBounds = true;  
		BitmapFactory.decodeResource(res, resId, options);  
		// 调用上面定义的方法计算inSampleSize值  
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);  
		// 使用获取到的inSampleSize值再次解析图片  
		options.inJustDecodeBounds = false;  
		return BitmapFactory.decodeResource(res, resId, options);  
	}
	
	/**
	 * 下载到OutputStream
	 * @param url
	 * @param outputStream
	 * @return
	 */
	public static boolean downloadUrlToStream(String url, OutputStream outputStream) 
	{
		BufferedOutputStream out = null;
		BufferedInputStream in = null;
		try 
		{
			HttpActionObj o = new HttpActionObj(url, new HashMap<String, String>());
			in = new BufferedInputStream(HttpUtil.sendHttpAction(o, InputStream.class).getData(), 8 * 1024);
			out = new BufferedOutputStream(outputStream, 8 * 1024);
			int b;
			while ((b = in.read()) != -1) 
			{
				out.write(b);
			}
			return true;
		} 
		catch (final IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try
			{
				if (out != null) out.close();
				if (in != null) in.close();
			} 
			catch (final IOException e) 
			{
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 加载图片
	 * @param ctx
	 * @param rootView
	 * @param imageView
	 * @param imageUrl
	 * @param reqWidth 压缩图片的宽度（为null时，默认100）
	 * @param reqHeight 压缩图片的高度（为null时，默认100）
	 */
	public static void loadBitmaps(Context ctx, View rootView, ImageView imageView, String imageUrl, String reqWidth, String reqHeight)
	{
		try 
		{  
            Bitmap bitmap = BitmapLoader.getBitmapFromMemoryCache(imageUrl);  
            if (bitmap == null) 
            {  
            	BitmapLoadTask task = new BitmapLoadTask(ctx, rootView, imageView, 
            		new BitmapLoadTask.CallbackFn()
	            	{
						@Override
						public void onExecute(BitmapLoadTask _this)
						{
							taskCollection.remove(_this);
						}
					}
            	);  
                taskCollection.add(task);
                task.execute(imageUrl,reqWidth,reqHeight);
            } 
            else 
            {  
                if (imageView != null && bitmap != null)
                {  
                    imageView.setImageBitmap(bitmap);  
                }  
            }  
        } 
		catch (Exception e) 
		{  
            e.printStackTrace();  
        }
	}
	
	 /** 
     * 取消所有正在下载或等待下载的任务。 
     */  
    public static void cancelAllTasks() 
    {  
        if (taskCollection != null) 
        {  
            for (BitmapLoadTask task : taskCollection) 
            {  
                task.cancel(false);  
            }  
        }  
    }
}
