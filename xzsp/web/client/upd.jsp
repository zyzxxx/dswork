<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page pageEncoding="UTF-8" import="
MQAPI.*,
common.gov.JSONUtil,
common.gov.DsXzsp,
common.gov.DsXzspService,
dswork.spring.BeanFactory,
dswork.web.MyRequest,
dswork.core.util.UniqueId,
dswork.core.page.PageRequest,
java.util.List
"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="/web/themes/default/frame.css" />
<script type="text/javascript" src="/web/js/jquery/jquery.js"></script>
<script type="text/javascript" src="/web/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="/web/js/jskey/jskey_core.js"></script>
<script type="text/javascript" src="/web/js/dswork/dswork.js"></script>
<script type="text/javascript" src="/web/js/dswork/form.js"></script>
<script type="text/javascript">
$dswork.callback = function(){
	if($dswork.result.type == 1){
		alert("操作成功！");
	}else{
		alert("操作失败！");
	}
	location.href = "about:blank";
};
$("#dataFormSaveSend").click(function(){
	$("#resend").val("1");
	$("#dataFormSave").click();
});
</script>
</head>
<body>
<div style="width:90%;float:left">
<form id="dataForm" method="post" action="updAction.jsp">
<table class="zwtable" border="0" cellspacing="1" cellpadding="0">
	<tr>
		<td style="width:10%;">字段名</td>
		<td style="width:10%;">注释</td>
		<td>内容</td>
	</tr>
	<%
try
{
	MyRequest req = new MyRequest(request);
	DsXzspService service = (DsXzspService) BeanFactory.getBean("dsXzspService");
	DsXzsp po = service.get(req.getLong("keyIndex"));
	switch(po.getSptype())
	{
		case 0: %><%@include file="0.jsp" %><%; break;
		case 1: %><%@include file="1.jsp" %><%; break;
		case 2: %><%@include file="2.jsp" %><%; break;
		case 3: %><%@include file="3.jsp" %><%; break;
		case 4: %><%@include file="4.jsp" %><%; break;
		case 5: %><%@include file="5.jsp" %><%; break;
		case 6: %><%@include file="6.jsp" %><%; break;
		case 7: %><%@include file="7.jsp" %><%; break;
		case 8: %><%@include file="8.jsp" %><%; break;
		case 9: %><%@include file="9.jsp" %><%; break;
	}
	request.setAttribute("po", po);
}
catch(Exception e)
{
	out.print(e.getMessage());
}
%>
	<tr>
		<td colspan="3" class="menuTool"><a class="save" id="dataFormSave" href="#">保存</a></td>
		<td colspan="3" class="menuTool"><a class="save" id="dataFormSaveSend" href="#">保存并重发</a></td>
	<tr>
</table>
<input type="hidden" name="keyIndex" value="${po.id}" />
<input type="hidden" id="resend" name="resend" value="0" />
</form>
</div>
</body>
</html>
