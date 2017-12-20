<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%><%
common.auth.Auth auth = common.auth.AuthUtil.getLoginUser(request);
if(auth != null){
%><%=auth.getOwn()%><%
}
else{
	%>拿不到<%=dswork.sso.WebFilter.getAccount(request.getSession())%><%
}
%>