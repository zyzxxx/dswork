<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>错误信息</title>
</head>
<body>
<div>
<%
	String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
%>
	<h3>错误信息: <br /><%=exception.getMessage()%></h3>
	<br />

	<button onclick="window.history.back();">返回</button>
	<br />

	<p><a href="#" onclick="document.getElementById('detail_error_msg').style.display = '';">点击此处查看错误详细</a></p>

	<div id="detail_error_msg" style="display:none">
		<pre><%exception.printStackTrace(new java.io.PrintWriter(out));%></pre>
	</div>
</div>
</body>
</html>