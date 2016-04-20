package dswork.core.upload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Timer;
import java.util.TimerTask;

import dswork.core.util.EnvironmentUtil;

/**
 * 临时文件上传服务类
 */
public class JskeyUpload extends Thread
{
	// ################################################################################################
	// 基本配置参数
	// ################################################################################################
	private static final String toPathString(String key)
	{
		String s = EnvironmentUtil.getToString(key, "");
		String c = s.substring(s.length()-1);
		if(!c.equals("\\") && !c.equals("/"))
		{
			return s + "/";
		}
		return s;
	}

	/**
	 * 是否定时执行临时目录清理工作，建议仅web项目清理
	 */
	public static final boolean UPLOAD_CLEAR = EnvironmentUtil.getToBoolean("jskey.upload.clear", false);
	/**
	 * 文件保存时长（毫秒）
	 */
	public static final long UPLOAD_TIMEOUT = EnvironmentUtil.getToLong("jskey.upload.timeout", 600000L);
	
	/**
	 * 临时上传总目录
	 */
	public static final String UPLOAD_SAVEPATH = JskeyUpload.toPathString("jskey.upload.savePath");
	/**
	 * 文件占用最大空间（bit）
	 */
	public static final long UPLOAD_MAXSIZE = EnvironmentUtil.getToLong("jskey.upload.maxSize", 10485760L);
	/**
	 * 默认允许上传的图片后缀
	 */
	public static final String UPLOAD_IMAGE = EnvironmentUtil.getToString("jskey.upload.image", "jpg,jpeg,gif,png").toLowerCase();
	/**
	 * 默认允许上传的文件后缀
	 */
	public static final String UPLOAD_FILE = EnvironmentUtil.getToString("jskey.upload.file", "jpg,jpeg,gif,png,bmp,doc,rtf,xls,txt,ppt,pdf,rar,zip,7z,docx,xlsx,pptx").toLowerCase();
	private static final String UPLOAD_CHECK = "," + UPLOAD_FILE + ",";

	


	// ################################################################################################
	// 定时任务相关
	// ################################################################################################
	private Timer _timer = null;
	private static int count = 0;
	private static ConcurrentMap<Long, Long> jskeyUploadMap = new ConcurrentHashMap<Long, Long>();
	private static synchronized int getCount() {return JskeyUpload.count;}
	// count仅用于标记是否启动任务，1为启动，0为不启动
	private static synchronized void setCount(int count) {JskeyUpload.count = count;}
	static//临时目录初始化，在服务器启动时执行
	{
		try
		{
			if(JskeyUpload.UPLOAD_CLEAR)
			{
				String path = JskeyUpload.UPLOAD_SAVEPATH;
				java.io.File f = new java.io.File(path);
				System.out.println(f.getPath());
				JskeyUpload.delete(f);//删除整个目录
			}
		}
		catch(Exception ex)
		{
		}
	}
	//判断Map是否有值存在
	private static synchronized boolean existMapValue()
	{
		return JskeyUpload.jskeyUploadMap.size() > 0;
	}
	//移除sessionKey
	private static synchronized void removeSessionKey(long sessionKey)
	{
		try
		{
			JskeyUpload.jskeyUploadMap.remove(sessionKey);//移除标识
		}
		catch(Exception ex)
		{
		}
	}
	//删除整个临时上传目录
	private static boolean delSessionKey(long sessionKey)
	{
		try
		{
			if(sessionKey <= 0)
			{
				return false;
			}
			StringBuffer sb = new StringBuffer(JskeyUpload.UPLOAD_SAVEPATH);
			java.io.File f = new java.io.File(sb.append(sessionKey).append(java.io.File.separatorChar).toString());
			sb = null;
			JskeyUpload.delete(f);//删除整个目录
			JskeyUpload.removeSessionKey(sessionKey);//移除标识
			return true;
		}
		catch(Exception ex)
		{
			JskeyUpload.removeSessionKey(sessionKey);//移除标识
		}
		return false;
	}

	/**
	 * 启动线程
	 */
	public void run()
	{
		if(!JskeyUpload.UPLOAD_CLEAR)
		{
			return;
		}
		if(JskeyUpload.getCount() != 0)//启动中
		{
			return;
		}
		if(!JskeyUpload.existMapValue())
		{
			return;//同样不需要启动
		}
		JskeyUpload.setCount(1);//标记启动
		try
		{
			TimerTask _timerTask = new TimerTask()
			{
				public void run()
				{
					try
					{
						if(JskeyUpload.existMapValue())
						{
							long currTime = Calendar.getInstance().getTimeInMillis();//取得当前时间
							for(ConcurrentMap.Entry<Long, Long> entry: JskeyUpload.jskeyUploadMap.entrySet())
							{
								if(currTime - entry.getValue().longValue() > JskeyUpload.UPLOAD_TIMEOUT)
								{
									JskeyUpload.delSessionKey(entry.getKey());//超时,移除
								}
							}
						}
						if(!JskeyUpload.existMapValue())//让再判断一次
						{
							_timer.cancel();
							JskeyUpload.setCount(0);
							System.out.println("--临时上传目录清理程序结束，清理完毕。--");
						}
					}
					catch(Exception ex)
					{
						try
						{
							_timer.cancel();
						}
						finally
						{
							JskeyUpload.setCount(0);
							System.out.println("--临时上传目录清理程序异常结束。--");
						}
					}
				}
			};
			_timer = new Timer(true);
			// Timer.schedule(TimerTask task, Date date, long period)// 从date开始,每period毫秒执行task.
			_timer.schedule(_timerTask, 0, JskeyUpload.UPLOAD_TIMEOUT);// 从服务器启动开始运行,每period毫秒执行
			System.out.println("--临时上传目录清理程序启动，已经添加任务调度表，清理间隔" + JskeyUpload.UPLOAD_TIMEOUT/1000 + "秒。--");
		}
		catch(Exception ex)
		{
			try {_timer.cancel();}catch(Exception timerEx) {}//尝试停止进程，即使它未初始化或未启动
			ex.printStackTrace();
			System.out.println("--临时上传目录清理程序异常结束。--");
			JskeyUpload.setCount(0);
		}
	}

	// ################################################################################################
	// 文件操作相关
	// ################################################################################################
	/**
	 * 将一个文件读成byte(但需要注意文件太大时，内存是否足够)
	 * @param filePath 文件名称(全路径)
	 * @return 成功返回byte[]，失败返回null
	 */
	public static byte[] getToByte(String filePath)
	{
		try
		{
			if (filePath == null)
			{
				return null;
			}
			java.io.File file = new java.io.File(filePath);
			FileInputStream fin = new FileInputStream(file);
			ByteArrayOutputStream bout = new ByteArrayOutputStream(4096);
			byte[] buffer = new byte[4096];
			int bytes_read;
			while ((bytes_read = fin.read(buffer)) != -1)// Read until EOF
			{
				bout.write(buffer, 0, bytes_read);
			}
			fin.close();
			bout.close();
			return bout.toByteArray();
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//删除文件或清空文件夹
	private static boolean delete(File file)
	{
		try
		{
			if(file.isDirectory())
			{
				String[] children = file.list();
				for(int i = 0; i < children.length; i++)
				{
					JskeyUpload.delete(new java.io.File(file, children[i]));
				}
			}
			return file.delete();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	// ################################################################################################
	// 用户上传目录下的文件操作相关
	// ################################################################################################
	/**
	 * 返回用于保存的临时目录，以"/"结尾
	 * @param sessionKey 用户临时主目录
	 * @param fileKey 用户临时子目录
	 * @return String
	 */
	public static String getSavePath(long sessionKey, long fileKey)
	{
		StringBuffer sb = new StringBuffer(JskeyUpload.UPLOAD_SAVEPATH).append(sessionKey).append(File.separatorChar).append(fileKey).append(File.separatorChar);
		return sb.toString();
	}

	/**
	 * 根据参数取得上传的文件列表
	 * @param sessionKey 用户临时主目录
	 * @param fileKey 用户临时子目录
	 * @return File[]
	 */
	public static File[] getFile(long sessionKey, long fileKey)
	{
		try
		{
			if(sessionKey <= 0 || fileKey <= 0)
			{
				return null;
			}
			StringBuffer sb = new StringBuffer(JskeyUpload.UPLOAD_SAVEPATH).append(sessionKey).append(File.separatorChar).append(fileKey).append(File.separatorChar);
			java.io.File f = new java.io.File(sb.toString());
			sb = null;
			if(f.isDirectory())//正常情况下只会是文件夹或不存在，而不可能是文件
			{
				return f.listFiles();//正常情况下只会是文件列表，不会存在子文件夹
			}
			JskeyUpload.delete(f);//不正常文件，直接删除
		}
		catch(Exception ex)
		{
		}
		return null;
	}
	/**
	 * 删除指定的临时子目录
	 * @param sessionKey 用户临时主目录
	 * @param fileKey 用户临时子目录
	 * @return boolean
	 */
	public static boolean delFile(long sessionKey, long fileKey)
	{
		try
		{
			if(sessionKey <= 0 || fileKey <= 0)
			{
				return false;
			}
			StringBuffer sb = new StringBuffer(JskeyUpload.UPLOAD_SAVEPATH);
			java.io.File f = new java.io.File(sb.append(sessionKey).append(File.separatorChar).append(fileKey).append(File.separatorChar).toString());
			sb = null;
			JskeyUpload.delete(f);//删除整个目录
			return true;
		}
		catch(Exception ex)
		{
		}
		return false;
	}
	/**
	 * 返回允许上传的后缀名字符串
	 * @param ext 需要过滤的上传文件后缀名，格式为"***,***"
	 * @return String
	 */
	public static final String getUploadExt(String ext)
	{
		if(ext == null)
		{
			return JskeyUpload.UPLOAD_FILE;//默认的后缀名字符串//jpg,jpeg,gif,png,bmp,doc,rtf,xls,txt,ppt,pdf,rar,zip,7z
		}
		ext = ext.trim().toLowerCase();
		if(ext.length() == 0 || ext.equals("file"))
		{
			return JskeyUpload.UPLOAD_FILE;//默认的后缀名字符串//jpg,jpeg,gif,png,bmp,doc,rtf,xls,txt,ppt,pdf,rar,zip,7z
		}
		else if(ext.equals("image") || ext.equals("img"))
		{
			return JskeyUpload.UPLOAD_IMAGE;//默认的图片后缀名字符串//jpg,jpeg,gif,png,bmp
		}
		else
		{
			StringBuffer sb = new StringBuffer();
			String _tmp = sb.append(",").append(ext).append(",").toString();//前后加上","
			sb.setLength(0);
			String[] extarr = ext.split(",");
			for(String s : extarr)
			{
				sb.setLength(0);
				s = sb.append(",").append(s).append(",").toString();//前后加上","
				if(JskeyUpload.UPLOAD_CHECK.indexOf(s) == -1);//用户设置的上传后缀，但需要过滤掉不在默认的后缀名字符串中的后缀名
				{
					_tmp.replaceAll(s, ",");//移除非法后缀
				}
			}
			sb = null;
			_tmp = _tmp.substring(1, _tmp.length() - 1);//移除前后的","
			if(ext.length() == 0)
			{
				return JskeyUpload.UPLOAD_FILE;
			}
			return _tmp;
		}
	}
	
	/**
	 * 启动临时上传目录清理程序,在目录生成或文件上传后,才需要将sessionKey加入管理程序
	 * @param sessionKey 用户临时主目录
	 */
	public static final synchronized void toStart(long sessionKey)
	{
		JskeyUpload.jskeyUploadMap.put(sessionKey, System.currentTimeMillis());//更新时间标识
		JskeyUpload clearDao = new JskeyUpload();
		clearDao.start();//启动临时上传目录清理程序
	}
	// ################################################################################################
	/*
	 * 简单说明：sessionKey是一次会话对应的值,根据它生成一个sessionKey目录,该目录下有多个fileKey目录,一个fileKey对应一次上传提交
	 * 1 调用getSessionKey()获取sessionKey
	 * 2 页面传递一个当前session下唯一的long型fileKey
	 * 3 上传前后调用toStart(),启动定时清理程序将生成的sessionKey目录加入清理队列
	 * 4 已处理的文件调用delFile进行删除,节约服务器空间
	 */
	// ################################################################################################
	private static final String UPLOAD_KEY = "JSKEY_UPLOAD_KEY";
	private static long uploadSession = (new java.util.Random().nextInt(Integer.MAX_VALUE));
	private static final long getNewSessionKey()
	{
		if(uploadSession >= Long.MAX_VALUE || uploadSession < 0)
		{
			uploadSession = 0L;// 还原
		}
		uploadSession++;
		return uploadSession;
	}

	/**
	 * 获取sessionKey，没有则新创建一个
	 * @param request HttpServletRequest
	 * @return long
	 */
	public static final long getSessionKey(javax.servlet.http.HttpServletRequest request)
	{
		Object obj = String.valueOf(request.getSession().getAttribute(JskeyUpload.UPLOAD_KEY));
		long key = 0L;
		if(obj != null)
		{
			try
			{
				key = Long.parseLong(String.valueOf(obj));
				if(key > 0)
				{
					request.getSession().setAttribute(JskeyUpload.UPLOAD_KEY, key);
					return key;
				}
			}
			catch(NumberFormatException e)
			{
			}
		}
		if(key > 0)
		{
			return key;
		}
		key = JskeyUpload.getNewSessionKey();
		request.getSession().setAttribute(JskeyUpload.UPLOAD_KEY, key);
		return key;
	}

	@Deprecated
	public static final long refreshSessionKey(javax.servlet.http.HttpServletRequest request)
	{
		return getSessionKey(request);
	}
}
