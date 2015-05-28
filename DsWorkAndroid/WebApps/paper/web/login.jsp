<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPEhtmlPUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>登录</title>
</head>
<%--
<body onload="document.loginForm.j_username.focus();">
${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message} <!-- 输出异常信息 -->
<form name="loginForm" action ="${pageContext.request.contextPath}/j_spring_security_check" method="POST">
<table>
	<tr>
		<td>用户:</td>
		<td><input type ='text' name='j_username'></td>
	</tr>
	<tr>
		<td>密码:</td>
		<td><input type ='password' name='j_password'></td>
	</tr>
	<tr>
		<td><input name ="reset" type="reset"></td>
		<td><input name ="submit" type="submit"></td>
	</tr>
</table>
</form>
</body>
 --%>
<body>
<form name="loginForm" action ="${pageContext.request.contextPath}/auth/login.action" method="POST">
<table>
	<tr>
		<td>用户:</td>
		<td><input type ='text' name='user.account' value="admin"></td>
	</tr>
	<tr>
		<td>密码:</td>
		<td><input type ='password' name='user.password' value="admin"></td>
	</tr>
	<tr>
		<td><input name ="reset" type="reset"></td>
		<td><input name ="submit" type="submit"></td>
	</tr>
</table>
</form>
</body>
</html>