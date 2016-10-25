<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%><%
String jsoncallback  = String.valueOf(request.getParameter("jsoncallback")).replaceAll("<", "").replaceAll(">", "").replaceAll("\"", "").replaceAll("'", "");
System.out.println(request.getParameter("user"));
%><%=jsoncallback%>([
{id:1, pid:0, name:'门户菜单', img:"", imgOpen:"", url:""}
,{id:2, pid:1, name:'门户首页', img:"", imgOpen:"", url:"/frame/main.jsp"}
])