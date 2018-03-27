<%@page language="java" pageEncoding="UTF-8" import="
dswork.spring.BeanFactory,
java.io.File,
dswork.web.MyRequest,
dswork.cms.model.DsCmsSite,
dswork.cms.service.DsCmsPageService,
common.any.AnyDao
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
		AnyDao dao = (AnyDao)BeanFactory.getBean("anyDao");
		String sql = "select sum(xx.alldata) from ( " +
				"select count(1) as alldata from DS_CMS_CATEGORY where CONTENT like '%" + u + "%' or IMG like '%" + u + "%' or SUMMARY like '%" + u + "%' " +
				"union all " +
				"select count(1) as alldata from DS_CMS_CATEGORY_EDIT where CONTENT like '%" + u + "%' or IMG like '%" + u + "%' or SUMMARY like '%" + u + "%' " +
				"union all " +
				"select count(1) as alldata from DS_CMS_PAGE where CONTENT like '%" + u + "%' or IMG like '%" + u + "%' or SUMMARY like '%" + u + "%' " +
				"union all " +
				"select count(1) as alldata from DS_CMS_PAGE_EDIT where CONTENT like '%" + u + "%' or IMG like '%" + u + "%' or SUMMARY like '%" + u + "%' " +
			  ") xx";
		int count = (Integer)dao.executeCount(AnyDao.initSql(sql));
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
	DsCmsPageService service = (DsCmsPageService)BeanFactory.getBean("dsCmsPageService");
	long siteid = req.getLong("siteid");
	String path = req.getString("path");
	DsCmsSite s = service.getSite(siteid);
	String htmlRoot = new File(getCmsRoot(request) + s.getFolder() + "/html").getPath();
	if(path.indexOf("..") == -1)
	{
		String echo = cleanImage(htmlRoot + "/f/img/" + path, htmlRoot);
		out.print("1:" + echo);
		return;
	}
}
catch(Exception e)
{
}
out.print("0:清理失败");
%>