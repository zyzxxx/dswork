<%@page import="java.security.Provider.Service"%>
<%@page import="com.mysql.fabric.Server"%>
<%@page import="dswork.core.page.PageRequest"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page pageEncoding="UTF-8" import="
MQAPI.*,
common.gov.DsXzsp,
common.gov.DsXzspService,
dswork.spring.BeanFactory,
dswork.web.MyRequest,
dswork.core.util.UniqueId,
dswork.core.page.PageRequest,
java.util.List,
common.gov.JSONUtil
"%><%
try
{
	MyRequest req = new MyRequest(request);
	DsXzspService service = (DsXzspService) BeanFactory.getBean("dsXzspService");
	DsXzsp po = service.get(req.getLong("keyIndex"));
	po.setFscs(0);
	po.setFszt(0);
	service.update(po);
	out.print(1);
}
catch(Exception e)
{
	out.print("0:" + e.getMessage());
}
%>
