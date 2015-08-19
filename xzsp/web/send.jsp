<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%!common.gov.DsXzspDao dao = null;%><%
try
{
	MyRequest req = new MyRequest(request);
	common.gov.DsXzsp m = new common.gov.DsXzsp();
	m.setId(dswork.core.util.UniqueId.genId());
	m.setSblsh(req.getString("sblsh").trim());
	m.setSptype(req.getString("sptype").trim());
	m.setSpobject(req.getString("spobject").trim());
	if(dao == null)
	{
		dao = (DsXzspDao) dswork.spring.BeanFactory.getBean("dsXzspDao");
	}
	dao.save(m);
	out.print(1);
}
catch(Exception ex)
{
	out.print(0);
}
%>