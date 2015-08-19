<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%!common.gov.DsXzspDao dao = null;%><%
try
{
	dswork.web.MyRequest req = new dswork.web.MyRequest(request);
	common.gov.DsXzsp m = new common.gov.DsXzsp();
	m.setId(dswork.core.util.UniqueId.genId());
	m.setSblsh(req.getString("sblsh").trim());
	m.setSptype(req.getInt("sptype"));
	m.setSpobject(req.getString("spobject").trim());
	if(m.getSptype() >= 0 && m.getSptype() <= 9)
	{
		if(dao == null)
		{
			dao = (common.gov.DsXzspDao) dswork.spring.BeanFactory.getBean("dsXzspDao");
		}
		dao.save(m);
		out.print(1);
	}
	else
	{
		out.print(0);
	}
}
catch(Exception ex)
{
	out.print(0);
}
session.invalidate();
%>