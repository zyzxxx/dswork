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
		location.href = "getPaperImages.action?pid=${pid}&ppid=${ppid}";
	}
};
</script>
</head>
<body>
<h1>上传图片</h1>
<hr>
<form id="dataForm" action="addPaperImage2.action" enctype="multipart/form-data" method="post">
 	图片：
 	<input type="file" name="myFile" value="" multiple="multiple"/><br>
	<input type="text" name="pid" value="${pid}"/>
	<input type="submit" id="dataFormSave" value="上传" />
</form>
</body>
</html>

