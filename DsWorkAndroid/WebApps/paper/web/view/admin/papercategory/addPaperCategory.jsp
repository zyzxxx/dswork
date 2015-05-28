<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<script type="text/javascript" src="<%=path%>/js/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/js/gwen/form.js"></script>
<script type="text/javascript">
$gwen.form.callback = function(){
	if($gwen.result.type == 1){
		location.href = "getPaperCategorys.action";
	}
};
</script>
</head>
<body>
<h1>新增纸模分类</h1>
<hr>
<form id="dataForm" action="addPaperCategory2.action" method="post">
分类名：<input type="text" name="po.name"><br>
排序号：<input type="text" name="po.sort"><br>
<input type="submit" id="dataFormSave" value="增加" />
</form>
</body>
</html>

