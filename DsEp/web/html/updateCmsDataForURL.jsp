<%@page import="dswork.core.util.FileUtil"%>
<%@page language="java" pageEncoding="UTF-8" import="
java.util.*,
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
private String cleanImage(String path, Map<String, String> map)
{
	String echo = "";
	File file = new File(path);
	if(file.isDirectory())
	{
		int fx = 0;
		for(File f : file.listFiles())
		{
			if(f.isFile()){
				System.out.println("正在处理文件:" + f.getPath());
				String msg = FileUtil.readFile(f.getPath(), "UTF-8");
				for(Map.Entry<String, String> e : map.entrySet())
				{
					msg = msg.replaceAll("/a/"+e.getValue()+"/", "/a/"+e.getKey()+"/");
				}
				FileUtil.writeFile(f.getPath(), msg, "UTF-8");
			}
		}
	}
	return echo;
}
%><%
try
{
	MyRequest req = new MyRequest(request);
	DsCmsPageService service = (DsCmsPageService)BeanFactory.getBean("dsCmsPageService");
	long siteid = req.getLong("siteid");
	DsCmsSite s = service.getSite(siteid);
	
	AnyDao dao = (AnyDao)BeanFactory.getBean("anyDao");
	String sql = "SELECT ID, FOLDER FROM DS_CMS_CATEGORY where SITEID=" + siteid;
	
	List<Map<String, Object>> list = (List<Map<String, Object>>)dao.executeSelectList(AnyDao.initSql(sql));
	Map<String, String> map = new HashMap<String, String>();
	for(Map<String, Object> m : list)
	{
		map.put(String.valueOf(m.get("ID")), String.valueOf(m.get("FOLDER")));
	}
	String htmlRoot = new File(getCmsRoot(request) + s.getFolder() + "/templates").getPath();
	String echo = cleanImage(htmlRoot, map);
	htmlRoot = new File(getCmsRoot(request) + s.getFolder() + "/templates/include").getPath();
	echo = echo + cleanImage(htmlRoot, map);
	out.print("1:" + echo);
}
catch(Exception e)
{
}
out.print("0:清理失败");
%>