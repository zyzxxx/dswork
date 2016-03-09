<%@page language="java" import="java.util.*, common.wx.*" pageEncoding="UTF-8"%><%
String code = request.getParameter("code");
//String state = request.getParameter("state");
String msg = "";
if(code == null){
	msg = "没有从微信登录，或授权失败";
}
else{
	String openid = WxExecute.getWxOpenid(code);
	msg = openid;
}
%><%=msg%>