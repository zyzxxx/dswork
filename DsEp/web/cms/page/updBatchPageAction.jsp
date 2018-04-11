<%@page language="java" pageEncoding="UTF-8" import="dswork.spring.BeanFactory,
dswork.web.MyRequest,
common.any.AnyDao
"%><%
try
{
	MyRequest req = new MyRequest(request);
	String begintime = req.getString("begintime", "");
	String endtime = req.getString("endtime", "");
	long categoryid = req.getLong("categoryid", 0L);
	int updtype = req.getInt("updtype", -1);
	
	AnyDao dao = (AnyDao)BeanFactory.getBean("anyDao");
	if(updtype == 1)//更新发布时间
	{
		String time = req.getString("time", "");
		if(!time.equals(""))
		{
			String sql_1 = "update ds_cms_page_edit set RELEASETIME=\'"+time+"\' where SITEID=0";
			String sql_2 = "update ds_cms_page set RELEASETIME=\'"+time+"\' where SITEID=0";
			if(categoryid > 0)
			{
				sql_1 += " and CATEGORYID="+categoryid;
				sql_2 += " and CATEGORYID="+categoryid;
			}
			if(!begintime.equals(""))
			{
				sql_1 += " and RELEASETIME >='"+begintime+"'";
				sql_2 += " and CATEGORYID="+categoryid;
			}
			if(!endtime.equals(""))
			{
				sql_1 += " and RELEASETIME <='"+endtime+"'";
				sql_2 += " and CATEGORYID="+categoryid;
			}
			dao.executeUpdate(AnyDao.initSql(sql_1));
			dao.executeUpdate(AnyDao.initSql(sql_2));
			out.print("1:更新成功！");
		}
		else
		{
			out.print("0:替换时间不能为空！");
		}
	}
	else if(updtype == 0)
	{
		String oldsource = req.getString("oldsource", "");
		String newsource = req.getString("newsource", "");
		if(newsource.equals("")&&oldsource.equals(""))
		{
			out.print("0:原信息来源和更新后的信息来源不能同时为空！");
		}
		else
		{
			String sql = "update ds_cms_page set RELEASESOURCE=\'"+newsource+"\' where SITEID=0";
			if(!oldsource.equals(""))
			{
				sql = sql + " and RELEASESOURCE=\'"+oldsource+"\'";
			}
			if(categoryid > 0)
			{
				sql = sql + " and CATEGORYID="+categoryid;
			}
			if(!begintime.equals(""))
			{
				sql = sql + " and RELEASETIME >=\'"+begintime+"\'";
			}
			if(!endtime.equals(""))
			{
				sql = sql + " and RELEASETIME <=\'"+endtime+"\'";
			}
			dao.executeUpdate(AnyDao.initSql(sql));
			out.print("1:更新成功！");
		}
	}
}
catch(Exception e)
{
	out.print("0:" + e.getMessage());
}
%>