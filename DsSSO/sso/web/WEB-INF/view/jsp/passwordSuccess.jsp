<%@page contentType="text/html; charset=UTF-8"%><%
response.setHeader("Cache-Control","no-store");
response.setDateHeader("Expires", 0);
response.setHeader("Pragma","no-cache"); 
%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Error</title>
<style>body{width: 40em;margin: 0 auto;font-family: Tahoma, Verdana, Arial, sans-serif, 微软雅黑;}</style>
</head>
<body>
<h1>信息提示。</h1>

<p>操作成功！您的密码已经修改！</p>

<p><em>system administrator.</em></p>
<c:if test="${returnPassword == 'true'}">
<script type="text/javascript">setTimeout("window.opener=null;window.open('','_self');window.close();", 5000);</script>
</c:if>
<c:if test="${returnPassword != 'true'}">
<script type="text/javascript">setTimeout("top.location.href='${serviceURL}';", 5000);</script>
</c:if>
</body>
</html>
