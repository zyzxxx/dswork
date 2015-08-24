<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<%=dswork.cas.AuthFactory.getOrg("1").getName() %>
<br />


<%=dswork.cas.AuthFactory.getUser("admin").getName() %>
<br />


<br />
</body>
</html>