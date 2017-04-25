<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page pageEncoding="UTF-8" import="
common.gov.DsXzsp,
common.gov.DsXzspService,
dswork.spring.BeanFactory,
dswork.web.MyRequest,
dswork.core.util.UniqueId,
dswork.core.page.PageRequest,
java.util.List,
java.util.Map,
java.util.HashMap
"%><%
try
{
	MyRequest req = new MyRequest(request);
	DsXzspService service = (DsXzspService) BeanFactory.getBean("dsXzspService");
	Map<String, Object> filters = new HashMap<String, Object>();
	filters.put("desc_id", "desc_id");
	filters.put("fszt", "0");
	List<DsXzsp> list = service.queryList(filters);
	request.setAttribute("list", list);
}
catch(Exception e)
{
	out.print(e.getMessage());
}
%>
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

function showUpd(id){
	$("#iframe").attr("src", "upd.jsp?keyIndex=" + id);
}
$(function(){
	$("td.sptype").each(function(){
		var s = "";console.log($(this).html());
		switch($(this).html())
		{
			case "0":s="ApplicationOB"; break;
			case "1":s="PreAcceptOB"; break;
			case "2":s="AcceptOB"; break;
			case "3":s="SubmitOB"; break;
			case "4":s="CompleteOB"; break;
			case "5":s="BlockOB"; break;
			case "6":s="ResumeOB"; break;
			case "7":s="SupplyOB"; break;
			case "8":s="SupplyAcceptOB"; break;
			case "9":s="ReceiveRegOB"; break;
			default:
				break;
		}
		$(this).html(s);
	});
});
</script>
<style type="text/css">td.sptype,td.TL{text-align:left;} td.menuTool{text-align:center;}</style>
</head>
<body>
<div style="width:50%;height:100%;float:left;overflow:scroll;background-color:#00ff00;">
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:20%">类型</td>
		<td style="width:10%;">发送<br/>状态</td>
		<td style="width:10%;">发送<br/>次数</td>
		<td style="width:50%;">申办流水号<br />备注</td>
		<td style="width:auto;">操作</td>
	</tr>
<c:forEach items="${list}" var="d">
	<tr>
		<td class="sptype">${d.sptype}</td>
		<td>${d.fszt}</td>
		<td>${d.fscs}</td>
		<td class="TL"><span style="color:#ff0000;">${d.sblsh}</span><br />${d.memo}</td>
		<td class="menuTool">
			<a class="edit" onclick="showUpd(${d.id});return false;">修改</a>
		</td>
	</tr>
</c:forEach>
</table>
</div>
<div style="width:50%;height:100%;float:left;overflow:hidden;">
	<iframe id="iframe" style="width:100%;height:100%;border:0;"></iframe>
</div>
</body>
</html>
