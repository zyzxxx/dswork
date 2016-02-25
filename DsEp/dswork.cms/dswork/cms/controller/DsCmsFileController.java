/**
 * 功能:公共Controller
 */
package dswork.cms.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import common.cms.CmsFactory;
import dswork.cms.model.DsCmsSite;
import dswork.cms.service.DsCmsSiteService;
import dswork.core.page.PageRequest;
import dswork.core.upload.JskeyUpload;
import dswork.core.util.UniqueId;
import dswork.mvc.BaseController;

@Scope("prototype")
@Controller
@RequestMapping("/cms/file")
public class DsCmsFileController extends BaseController
{
	@Autowired
	private DsCmsSiteService service;

	/**
	 * 文件保存的路径
	 */
	private static String FilePath = "";
	/**
	 * 文件引用的网址
	 */
	private static String UseUrl = "";
	/**
	 * 站点文件路径
	 */
	private static String SitePath = "";
	/**
	 * 获取cms根路径
	 * @return
	 */
	private String getCmsRoot()
	{
		return request.getSession().getServletContext().getRealPath("/html") + "/";
	}

	/**
	 * 获取附件树，对附件进行管理
	 * @param sitename 站点文件夹名称
	 * @return
	 */
	@RequestMapping("/getFileTree")
	public String getFileTree(String sitename)
	{
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("id", 0);
		json.put("pid", null);
		json.put("name", "文件夹");
		// 将跟地址显示为$root
		// 判断sitename是否为空，如果不为空
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("own", getOwn());
		PageRequest rq = new PageRequest(map);
		List<DsCmsSite> list = service.queryList(rq);
		
		if(sitename == null && list.size() > 0)//如果sitename为空时，设置为第一个站点文件名
		{
			sitename = list.get(0).getFolder();
		}
		FilePath = getCmsRoot() + sitename + "/html/f/res";
		SitePath = getCmsRoot() + sitename + "/html";
		FilePath = FilePath.replace("\\", "/");
		SitePath = SitePath.replace("\\", "/");
		json.put("path", getVirtualFileName(FilePath));
		json.put("parent", "");
		put("po", json);
		put("siteList", list);
		long siteid = req.getLong("siteid", (Long) list.get(0).getId());
		UseUrl = service.get(siteid).getUrl();
		return "/manage/file/getFileTree.jsp";
	}

	/**
	 * 获取某个文件夹下的文件列表
	 * @return
	 */
	@RequestMapping("/getFile")
	public String getFile()
	{
		String path = req.getString("path");
		long pid = req.getLong("pid", 0);
		put("list", fileLoad(getRealFileName(path), pid));
		put("path", path);
		return "/manage/file/getFile.jsp";
	}

	/**
	 * 获取文件树
	 */
	@RequestMapping("/tree")
	public void tree()
	{
		// 判断传入对象是否为一个文件夹对象
		File f = null;
		String path = getRealFileName(req.getString("path", ""));
		long pid = req.getLong("pid", 0);
		if(path.equals(""))
		{
			f = new File("\\\\");
		}
		else
		{
			f = new File(path);
		}
		//判断该文件是不是文件夹
		if(!f.isDirectory())
		{
			if(pid == 0)
			{
				print("文件打开失败！");
			}
			else
			{
				try
				{
					fileRead(path);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			print(fileLoad(path, pid));
		}
	}

	/**
	 * 传入虚拟的文件地址，转成真实的文件地址并返回。
	 * @param path 虚拟的文件地址
	 * @return
	 */
	private String getRealFileName(String path)
	{
		return path.replace("$root", FilePath);
	}
	/**
	 * 传入真实的文件地址，转成虚拟的文件地址并返回。
	 * @param path 真实的文件地址
	 * @return
	 */
	private String getVirtualFileName(String path)
	{
		return path.replace(FilePath, "$root");
	}
	
	/**
	 * 传入文件的磁盘地址，转化成可以引用的访问地址并返回
	 * @param path
	 * @return
	 */
	private String getUseUrl(String path)
	{
		return path.replace(SitePath, UseUrl);
	}

	/**
	 * 文件加载
	 * @param path
	 * @param pid
	 * @return
	 */
	public List<Map<String, Object>> fileLoad(String path, long pid)
	{
		File f = null;
		f = new File(path);
		File[] t = f.listFiles();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// file type
		for(int i = 0; i < t.length; i++)
		{
			Map<String, Object> json = new HashMap<String, Object>();
			// 判断文件列表中的对象是否为文件夹对象，如果是则执行tree递归，直到把此文件夹中所有文件输出为止
			if(t[i].isDirectory())
			{
				// System.out.println("文件夹：" + t[i].getName() + "   路径：" + t[i].getPath());
				try
				{
					json.put("id", UniqueId.genId());
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				json.put("pid", pid);
				json.put("name", t[i].getName());
				json.put("isParent", true);
				json.put("path", getVirtualFileName(t[i].getPath().replace("\\", "/")));
				json.put("thispath", getVirtualFileName(t[i].getPath().substring(path.length(), t[i].getPath().length()).replace("\\", "/")));
				json.put("parent", getVirtualFileName(t[i].getParent().replace("\\", "/")));
				list.add(json);
			}
		}
		// document type
		for(int i = 0; i < t.length; i++)
		{
			Map<String, Object> json = new HashMap<String, Object>();
			// 判断文件列表中的对象是否为文件夹对象，如果是则执行tree递归，直到把此文件夹中所有文件输出为止
			if(!t[i].isDirectory())
			{
				try
				{
					json.put("id", UniqueId.genId());
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				json.put("pid", pid);
				json.put("name", t[i].getName());
				json.put("isParent", false);
				json.put("path", getVirtualFileName(t[i].getPath().replace("\\", "/")));
				json.put("thispath", getVirtualFileName(t[i].getPath().substring(path.length(), t[i].getPath().length()).replace("\\", "/")));
				json.put("parent", getVirtualFileName(t[i].getParent().replace("\\", "/")));
				json.put("filetype", getExtensionName(t[i].getName()));
				json.put("useurl", getUseUrl(t[i].getPath().replace("\\", "/")));
				list.add(json);
			}
		}
		return list;
	}

	/**
	 * 跳转文件上传页面
	 * @return
	 */
	@RequestMapping("/uploadFile1")
	public String uploadTemplate1()
	{
		put("path", req.getString("path").replace("\\", "/"));
		put("v_file", System.currentTimeMillis());
		put("v_session", JskeyUpload.getSessionKey(request));
		return "/manage/file/uploadFile.jsp";
	}

	@RequestMapping("/uploadFile2")
	public void uploadFile2()
	{
		// 文件keyID
		String f_key = req.getString("f_key");
		// 文件保存路径
		String path = getRealFileName(req.getString("path"));
		if(!f_key.equals(""))
		{
			long fj = Long.parseLong(f_key);
			try
			{
				File file = JskeyUpload.getFile(JskeyUpload.getSessionKey(request), fj)[0];
				if(file != null)
				{
					unzipFile(file.getPath(), getRealFileName(path));
					file.delete();
				}
			}
			catch(Exception exx)
			{
			}
		}
		print(1);
	}

	/**
	 * 解压文件
	 * @param zipFilePath //要解压得文件路径
	 * @param targetPath //要解压得目标路径
	 */
	public void unzipFile(String zipFilePath, String targetPath)
	{
		try
		{
			File zipFile = new File(zipFilePath);
			InputStream is = new FileInputStream(zipFile);
			// 设置字符编码Charset.forName("GBK")
			ZipInputStream zis = new ZipInputStream(is, Charset.forName("GBK"));
			ZipEntry entry = null;
			System.out.println("开始解压:" + zipFile.getName() + "...");
			while((entry = zis.getNextEntry()) != null)
			{
				String zipPath = entry.getName();
				try
				{
					if(entry.isDirectory())
					{
						File zipFolder = new File(targetPath + File.separator + zipPath);
						if(!zipFolder.exists())
						{
							zipFolder.mkdirs();
						}
					}
					else
					{
						File file = new File(targetPath + File.separator + zipPath);
						if(!file.exists())
						{
							File pathDir = file.getParentFile();
							pathDir.mkdirs();
							file.createNewFile();
						}
						FileOutputStream fos = new FileOutputStream(file);
						int bread;
						while((bread = zis.read()) != -1)
						{
							fos.write(bread);
						}
						fos.close();
					}
					System.out.println("成功解压:" + zipPath);
				}
				catch(Exception e)
				{
					System.out.println("解压" + zipPath + "失败");
					continue;
				}
			}
			zis.close();
			is.close();
			System.out.println("解压结束");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String fileRead(String path)
	{
		File file = new File(path);
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String text = null;
			// repeat until all lines is read
			while((text = reader.readLine()) != null)
			{
				contents.append(text).append(System.getProperty("line.separator"));
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(reader != null)
				{
					reader.close();
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		// show file contents here
		// System.out.println(contents.toString());
		return contents.toString();
	}

	/**
	 * 获取文件后缀
	 * @param filename
	 * @return
	 */
	public String getExtensionName(String filename)
	{
		if((filename != null) && (filename.length() > 0))
		{
			int dot = filename.lastIndexOf('.');
			if((dot > -1) && (dot < (filename.length() - 1)))
			{
				// System.out.println(filename.substring(dot + 1));
				return filename.substring(dot + 1);
			}
		}
		// System.out.println(filename);
		return filename;
	}

	private String getOwn()
	{
		return common.auth.AuthLogin.getLoginUser(request, response).getOwn();
	}
}
