<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<%@include file="/commons/include/page.jsp" %>
	<script type="text/javascript">
	</script>
</head>
<body>
<div class="easyui-tabs" fit="true">
<div title="用户授权" style="padding:5px;overflow:hidden;">
	<iframe id="userFrame" name="userFrame" src="" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>
</div>
<div title="岗位授权" style="padding:5px;overflow:hidden;">
	<iframe id="postFrame" name="postFrame" src="" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>
</div>
</div>
</body>
</html>
