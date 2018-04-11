<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page language="java" pageEncoding="UTF-8" import="dswork.spring.BeanFactory,
dswork.web.MyRequest,
dswork.cms.model.DsCmsCategory,
dswork.cms.service.DsCmsEditService,
java.util.List,
java.util.ArrayList,
java.util.Map,
java.util.HashMap
"%><%
DsCmsEditService service = (DsCmsEditService)BeanFactory.getBean("dsCmsEditService");
List<DsCmsCategory> clist = service.queryListCategory(0L);
Map<Long, DsCmsCategory> map = new HashMap<Long, DsCmsCategory>();
for(DsCmsCategory m : clist)
{
	map.put(m.getId(), m);
}
List<DsCmsCategory> list = new ArrayList<DsCmsCategory>();
for(DsCmsCategory m : clist)
{
	if(m.getPid() > 0)
	{
		try
		{
			if(m.getScope() == 0 || m.getScope() == 1)// 过滤外链栏目
			{
				map.get(m.getPid()).add(m);// 放入其余节点对应的父节点
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();// 找不到对应的父栏目
		}
	}
	else if(m.getPid() == 0)
	{
		if(m.getScope() == 0 || m.getScope() == 1)// 过滤外链栏目
		{
			list.add(m);// 只把根节点放入list
		}
	}
}
request.setAttribute("list", dswork.cms.controller.DsCmsBaseController.categorySettingList(list));
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/updAjax.jsp" %>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
	location.reload();
}};
$(function(){
	try{$(".form_title").css("width", "30%");}catch(e){}
	$("#updtype1").prop("checked", true);
	if($("#updtype1").prop("checked")){
		document.getElementById("updsource1").style.display="none";
		document.getElementById("updsource2").style.display="none";
		document.getElementById("updtime").style.display="";
	}else{
		document.getElementById("updsource1").style.display="";
		document.getElementById("updsource2").style.display="";
		document.getElementById("updtime").style.display="none";
	}
	
});
function _vtype(v){
	if(v == 1){
		document.getElementById("updsource1").style.display="none";
		document.getElementById("updsource2").style.display="none";
		document.getElementById("updtime").style.display="";
	}else{
		document.getElementById("updsource1").style.display="";
		document.getElementById("updsource2").style.display="";
		document.getElementById("updtime").style.display="none";
	}
}
</script>
<style type="text/css">
.fsize{font-size: 14px;height: 40px;}
</style>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">批量更新发布时间</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updBatchPageAction.jsp">
<div style="margin:0 auto;width:800px;">
<table border="0" cellspacing="1" cellpadding="0" class="listTable" style="margin-top: 5%;">
<tr>
	<td class="form_title fsize">选择更新的内容</td>
	<td class="form_input">
		<label style="margin:0 10px;cursor:pointer;"><input type="radio" id="updtype1" onclick="_vtype(1)" name="updtype" value="1"/> 发布时间</label>
		<label style="margin:0 10px;cursor:pointer;"><input type="radio" id="updtype0" onclick="_vtype(0)" name="updtype" value="0"/> 信息来源</label>
	</td>
</tr>
<tr>
	<td class="form_title fsize">选择需要更新的栏目</td>
	<td class="form_input">
		<select id="category" name="categoryid">
			<option value="0">全部栏目</option>
		<c:forEach items="${list}" var="d">
			<option value="${d.id}">${d.label}${fn:escapeXml(d.name)}</option>
		</c:forEach>
		</select>
	</td>
</tr>
<tr>
	<td class="form_title fsize">选择需要更新的时间段</td>
	<td class="form_input">
		&nbsp;起始时间：<input type="text" name="begintime" id="begintime" class="WebDate" format="yyyy-MM-dd HH:mm:ss" value=""/>
		&nbsp;结束时间：<input type="text" name="endtime" id="endtime" dataType="DateCheck" require="false" operator=">=" to="begintime" class="WebDate" format="yyyy-MM-dd HH:mm:ss" value=""/>
	</td>
</tr>
<tr id="updtime"  style="display:">
	<td class="form_title fsize">选择替换后的时间</td>
	<td class="form_input">替换时间：<input type="text" name="time" class="WebDate" format="yyyy-MM-dd HH:mm:ss" value=""/></td>
</tr>
<tr id="updsource1" style="display:">
	<td class="form_title fsize">原信息来源</td>
	<td class="form_input"><input type="text" name="oldsource" value=""/></td>
</tr>
<tr id="updsource2" style="display:">
	<td class="form_title fsize">更新后的信息来源</td>
	<td class="form_input"><input type="text" name="newsource" value=""/></td>
</tr>
<tr>
	<td class="form_title fsize">操作</td>
	<td style="text-align: center;"><input type="button" id="dataFormSave" style="width:100px;height:30px;font-size: 14px;margin:5px;" value="确    定"/></td>
</tr>
</table>
</div>
</form>
</body>
</html>
