<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Error</title>
<style>
    body {
        width: 40em;
        margin: 0 auto;
        font-family: Tahoma, Verdana, Arial, sans-serif, 微软雅黑;
    }
</style>
</head>
<body>
<h1>发生错误。</h1>
<p>很抱歉，您所要的页面目前无法使用。<br/>
请确定您有权访问该页面，或稍后再试。</p>
<p><em>system administrator.</em></p>
<p><a onclick="var o=document.getElementById('detail_error_msg');o.style.display=(o.style.display==''?'none':'');return false;" href="#">点击此处查看错误详情</a></p>
<div id="detail_error_msg" style="display:none">
	<pre><%exception.printStackTrace(new java.io.PrintWriter(out));%></pre>
</div>
</body>
</html>
