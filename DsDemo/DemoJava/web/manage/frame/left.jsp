<%@page language="java" contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title></title>
<meta charset="UTF-8" />
<link rel="stylesheet" type="text/css" href="js/jskey/themes/menu/jskey.menu.css" />
<script type="text/javascript" src="js/jskey/jskey.menu.js"></script>
<script type="text/javascript" src="admin.json.js"></script>
</head>
<body onselectstart="return false;" oncontextmenu="return true;">
</body>
<script type="text/javascript">
	var jsonData = treedata;
	$jskey.menu.root = "<%=request.getContextPath()%>";
	//$jskey.menu.show(jsonData, false);// 可打开多个
	$jskey.menu.show(jsonData, true);// 只能打开一个，<!DOCTYPE html>
	$jskey.menu.clickBar(0);
</script>
</html>
