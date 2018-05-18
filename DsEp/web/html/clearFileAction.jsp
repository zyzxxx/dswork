<%@page language="java" pageEncoding="UTF-8" import="
dswork.spring.BeanFactory,
java.io.File,
dswork.web.MyRequest,
dswork.cms.model.DsCmsSite,
dswork.cms.dao.DsCmsSiteDao,
dswork.cms.dao.DsCmsAnyDao
"%><%!
private String getCmsRoot(HttpServletRequest request)
{
	return request.getSession().getServletContext().getRealPath("/") + "/html/";
}
private String cleanImage(String path, String htmlRoot)
{
	File file = new File(path);
	if(file.isDirectory())
	{
		String echo = "";
		int fx = 0;
		for(File f : file.listFiles())
		{
			String x = cleanImage(f.getPath(), htmlRoot);
			if(x.length() > 0)
			{
				echo += x;
			}
			else
			{
				fx++;
			}
		}
		if(fx > 0)
		{
			echo = file.getPath().replace(htmlRoot, "").replace("\\", "/") + "有" + fx + "个文件正在使用中<br>" + echo;
		}
		return echo;
	}
	else
	{
		String u = file.getName().split("\\.", -1)[0];
		String pp = file.getPath().replace(htmlRoot, "").replace("\\", "/");
		DsCmsAnyDao dao = (DsCmsAnyDao)BeanFactory.getBean("dsCmsAnyDao");
		String sql1 = "SELECT COUNT(1) FROM DS_CMS_CATEGORY WHERE CONTENT LIKE '%" + u + "%' OR IMG LIKE '%" + u + "%' OR SUMMARY LIKE '%" + u + "%'";
		int count = 0;
		count = (Integer)dao.executeCount(DsCmsAnyDao.initSql(sql1));
		if(count > 0)
		{
			return "";
		}
		String sql2 = "SELECT COUNT(1) FROM DS_CMS_PAGE WHERE CONTENT LIKE '%" + u + "%' OR IMG LIKE '%" + u + "%' OR SUMMARY LIKE '%" + u + "%'";
		count = (Integer)dao.executeCount(DsCmsAnyDao.initSql(sql2));
		if(count > 0)
		{
			return "";
		}
		file.renameTo(new File("/WorkServer/cmsbak/" + file.getName()));
		return "清理：" + pp+ "<br>";
	}
}
%><%
try
{
	File bakfile = new File("/WorkServer/cmsbak");
	if(!bakfile.isDirectory())
	{
		bakfile.mkdir();
	}
	MyRequest req = new MyRequest(request);
	DsCmsSiteDao sdao = (DsCmsSiteDao)BeanFactory.getBean("dsCmsSiteDao");
	long siteid = req.getLong("siteid");
	String path = req.getString("path");
	DsCmsSite s = (DsCmsSite)sdao.get(siteid);
	String htmlRoot = new File(getCmsRoot(request) + s.getFolder() + "/html").getPath();
	if(path.indexOf("..") == -1)
	{
		String echo = cleanImage(htmlRoot + "/f/file/" + path, htmlRoot);
		out.print("1:" + echo);
		return;
	}
}
catch(Exception e)
{
}
out.print("0:清理失败");
%>