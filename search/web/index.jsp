<%@page contentType="text/html; charset=UTF-8"%><!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
<title></title>
<style>
html, body{width:100%;height:100%;}
body{margin:0 auto;font-family:Tahoma, Verdana, Arial, sans-serif, 微软雅黑;}
</style>
<script type="text/javascript" src="/search/js/jquery.js"></script>
<script type="text/javascript">
function my()
{
	window.frames["zwiframe"].src="/search/search.jsp?v=" + encodeURIComponent(encodeURIComponent(document.getElementById('vv').value));
}
</script>
</head>
<body>
	<input id="vv" type="text" name="vv" value="" />
	<input type="button" value="搜索" onclick="my();" /><br />
	<iframe id="zwiframe" src="about:blank;" style="border:none;width:100%;height:80%;"></iframe>
</body>
</html>
