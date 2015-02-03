package dswork.android.lib.core.util.bmputil;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import dswork.android.lib.core.util.DiskLruCache.Snapshot;
import dswork.android.lib.core.util.MD5Util;

public class BitmapLoadTask extends AsyncTask<String, Integer, Bitmap>
{
	private Context context;
	private ProgressBar pb;
	private FrameLayout rootLayout;
	private ImageView imageView;
	private CallbackFn mCallback;

	public BitmapLoadTask(Context context, View rootView, ImageView imageView, CallbackFn mCallback)
	{
		super();
		this.context = context;
		this.pb =  new ProgressBar(context);
		this.rootLayout = (FrameLayout)rootView;
		this.imageView = imageView;
		this.mCallback = mCallback;
	}

	@Override
	protected void onPreExecute() 
	{
		//创建ProgressBar
		pb.setVisibility(ProgressBar.VISIBLE);
		FrameLayout.LayoutParams pbViewParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		pbViewParams.gravity = Gravity.CENTER;
		rootLayout.addView(pb,pbViewParams);
	}
	
	@Override  
    protected Bitmap doInBackground(String... _params) 
    {//后台耗时操作，获取列表数据，不能在后台线程操作UI
		String imgUrl = _params[0];
		int reqWidth = _params[1]==null?100:Integer.parseInt(_params[1]);
		int reqHeight = _params[2]==null?100:Integer.parseInt(_params[2]);
		
        FileDescriptor fileDescriptor = null;  
        FileInputStream fileInputStream = null;  
        Snapshot snapShot = null;  
        try {  
            // 生成图片URL对应的key  
            final String key = MD5Util.hashKeyForDisk(imgUrl);  
            // 查找key对应的缓存  
            snapShot = BitmapLoader.mDiskLruCache.get(key);  
            if (snapShot == null) {  
                // 如果没有找到对应的缓存，则准备从网络上请求数据，并写入缓存 
            	BitmapLoader.writeToDiskLruCacheFromServer(imgUrl);
                // 缓存被写入后，再次查找key对应的缓存  
                snapShot = BitmapLoader.mDiskLruCache.get(key);  
            }  
            if (snapShot != null) {  
                fileInputStream = (FileInputStream) snapShot.getInputStream(0);  
                fileDescriptor = fileInputStream.getFD();  
            }  
            // 将缓存数据解析成Bitmap对象  
            Bitmap bitmap = null;  
            if (fileDescriptor != null) {
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            	System.out.println("压缩前----->"+bitmap.getByteCount());
        		bitmap = BitmapLoader.decodeSampledBitmapFromResource(fileDescriptor,reqWidth,reqHeight);
        		System.out.println("压缩后----->"+bitmap.getByteCount());
            }  
            if (bitmap != null) {
                // 将Bitmap对象添加到内存缓存当中  
            	BitmapLoader.addBitmapToMemoryCache(_params[0], bitmap);  
            }  
            return bitmap;  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (fileDescriptor == null && fileInputStream != null) {  
                try {  
                    fileInputStream.close();  
                } catch (IOException e) {  
                }  
            }  
        }  
        return null;
    }

	protected void onPostExecute(Bitmap bmp) 
	{// 后台任务执行完之后被调用，在ui线程执行
		if (bmp != null)
		{
			imageView.setImageBitmap(bmp);
			mCallback.onExecute(this);
		} 
		else 
		{
			Toast.makeText(context, "加载失败，网络异常", Toast.LENGTH_SHORT).show();
		}
		pb.setVisibility(ProgressBar.GONE);//隐藏圆形进度条
	}
	

	public static interface CallbackFn
	{
		void onExecute(BitmapLoadTask _this);
	}
}