<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%><%
String jsoncallback  = String.valueOf(request.getParameter("jsoncallback")).replaceAll("<", "").replaceAll(">", "").replaceAll("\"", "").replaceAll("'", "");
System.out.println(request.getParameter("user"));
%><%=jsoncallback%>([
{id:1, pid:0, name:'CasClient菜单', img:"", imgOpen:"", url:""}
,{id:2, pid:1, name:'CasClient菜单1', img:"", imgOpen:"", url:"/1.jsp"}
,{id:3, pid:1, name:'CasClient菜单2', img:"", imgOpen:"", url:"/2.jsp"}
])