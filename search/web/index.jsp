<%@page contentType="text/html; charset=UTF-8"%><!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
<title></title>
<style>body{width:38em;margin:0 auto;font-family:Tahoma, Verdana, Arial, sans-serif, 微软雅黑;}</style>
<script type="text/javascript" src="/search/js/jquery.js"></script>
<script type="text/javascript">
function my()
{
	top.location.href="/search/search.jsp?v=" + encodeURI(encodeURI(document.getElementById('vv').value));
}
</script>
</head>
<body>
	<input id="vv" type="text" name=vv"" value="" />
	<input type="button" value="搜索" onclick="my();" />
</body>
</html>
