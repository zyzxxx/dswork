<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.axis2.Constants,
				 org.apache.axis2.description.AxisOperation" %>
<%@ page import="org.apache.axis2.description.AxisService" %>
<%@ page import="org.apache.axis2.description.Parameter" %>
<%@ page import="org.apache.axis2.engine.AxisConfiguration" %>
<%@ page import="org.apache.axis2.util.JavaUtils" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.Iterator" %>
<!DOCTYPE HTML>
<html>
<head>
<title>List Services</title>
<style type="text/css">
	div, a{line-height:2em;}
	.v0 {color:#000000;font-size:20px;font-weight:bold;}
	.v1 a {color:#0000ff;font-size:16px;font-weight:bold;}
	.v2   {color:#000;;font-size:12px;}
</style>
</head>
<body>
<div class="v0">Available services</div>
<%
String prefix = request.getContextPath() + "/services/";
HashMap serviceMap = (HashMap) request.getSession().getAttribute(Constants.SERVICE_MAP);
request.getSession().setAttribute(Constants.SERVICE_MAP, null);
Hashtable errornessservice = (Hashtable) request.getSession().getAttribute(Constants.ERROR_SERVICE_MAP);
boolean status = false;
if (serviceMap != null && !serviceMap.isEmpty())
{
	Iterator opItr;
	String serviceName;
	Collection servicecol = serviceMap.values();
	for (Iterator iterator = servicecol.iterator(); iterator.hasNext();)
	{
		AxisService axisService = (AxisService) iterator.next();
		opItr = axisService.getOperations();
		serviceName = axisService.getName();
		%><div class="v1"><a href="<%=prefix + axisService.getName()%>?wsdl"><%=serviceName%></a></div><%
		boolean disableREST = false;
		AxisConfiguration axisConfiguration = axisService.getAxisConfiguration();
		Parameter parameter ;
		parameter = axisConfiguration.getParameter(Constants.Configuration.DISABLE_REST);
		if (parameter != null) {
			disableREST = !JavaUtils.isFalseExplicitly(parameter.getValue());
		}
		if (!disableREST ) {
		}
		String serviceDescription = axisService.getServiceDescription();
		if (serviceDescription == null || "".equals(serviceDescription)) {
			serviceDescription = "No description available for this service";//服务描述
		}
		%>
		<%--Service Description : <font color="black"><%=serviceDescription%>--%>
		<div class="v2">Service EPR : <%=prefix + axisService.getName()%></div>
		<div class="v2">Service Status : <%=axisService.isActive() ? "Active" : "InActive"%></div>
		<div class="v2"><%=(opItr.hasNext()?"Available Operations":"There are no Operations specified")%></div>
		<ul><%
			opItr = axisService.getOperations();
			while (opItr.hasNext()) {
				AxisOperation axisOperation = (AxisOperation) opItr.next();
				%><li><%=axisOperation.getName().getLocalPart()%></li>
			<%
			}
			%>
		</ul>
		<%
		status = true;
	}
}
if (!status){%>No services listed! Try hitting refresh. <%}%>
</body>
</html>
