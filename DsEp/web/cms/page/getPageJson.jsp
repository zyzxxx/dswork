<%@page language="java" pageEncoding="UTF-8" import="
dswork.spring.BeanFactory,
dswork.web.MyRequest,
java.util.Map,
java.util.HashMap,
common.any.AnyDao
"%><%
Map<String, Object> map = new HashMap<String, Object>();
try
{
	MyRequest req = new MyRequest(request);
	AnyDao dao = (AnyDao)BeanFactory.getBean("anyDao");

	Map<String, Object> mapx = new HashMap<String, Object>();
	long siteid = req.getLong("siteid");
	String mindate = req.getString("mindate", null);
	String maxdate = req.getString("maxdate", null);

	String sql = "select c.NAME as name, (select count(1) from DS_CMS_PAGE p where p.CATEGORYID=c.ID";
	if(mindate != null && maxdate != null)
	{
		sql += " and (p.RELEASETIME >='" + mindate + "'and p.RELEASETIME <='" + maxdate + "')";
	}
	sql += ") as pagecount from DS_CMS_CATEGORY c where c.SITEID='" + siteid + "' order by c.SEQ";

	map.put("data", dao.executeSelectList(dao.initSql(sql)));
	map.put("status", "success");
	map.put("error", "");
}
catch(Exception e)
{
	map.put("status", "fail");
	map.put("error", "获取栏目统计数失败！");
}
out.print(common.json.GsonUtil.toJson(map));
%>