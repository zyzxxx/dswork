<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript">
function chooseType(v)
{
	var $p = $("input[name='ispage']");
	var $c = $("input[name='iscategory']");
	if(v == "0"){
		$p.val("ispage");
		$c.val("");
	}else if(v == "1"){
		$p.val("");
		$c.val("iscategory");
	}
}
$(function(){
	$("#queryForm").submit(function(){return $jskey.validator.Validate("queryForm", $dswork.validValue || 3);});
	$("#site").change(function(){location.href = "getLog.htm?siteid="+$(this).val();});
	$("#category").change(function(){location.href = "getLog.htm?siteid="+$("#site").val()+"&categoryid="+$(this).val();});
});
</script>
</head> 
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">
			切换站点：<select id="site" v="${fn:escapeXml(siteid)}"><c:forEach items="${siteList}" var="d"><option value="${d.id}"<c:if test="${d.id==siteid}"> selected="selected"</c:if>>${fn:escapeXml(d.name)}</option></c:forEach></select>
			切换栏目：<select id="category" v="${fn:escapeXml(param.categoryid)}"><option value="0">全部栏目</option><c:forEach items="${list}" var="d"><option value="${d.id}">${d.label}${fn:escapeXml(d.name)}</option></c:forEach></select>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="queryForm" method="post" action="getLog.htm">
<table border="0" cellspacing="0" cellpadding="0" class="queryTable">
	<tr>
		<td class="input">
			<table>
				<tr>
					<td>&nbsp;标题：</td>
					<td><input type="text" name="title" value="${fn:escapeXml(param.title)}"></td>
					<td>&nbsp;类型：</td>
					<td>
						<select name="choosetype" onchange="chooseType(this.value);" v="${fn:escapeXml(param.choosetype)}">
							<option value="">全部</option>
							<option value="0">内容</option>
							<option value="1">栏目</option>
						</select>
						<input type="hidden" name="ispage" value="${fn:escapeXml(param.ispage)}">
						<input type="hidden" name="iscategory" value="${fn:escapeXml(param.iscategory)}">
					</td>
				</tr>
				<tr>
					<td>&nbsp;采编员：</td>
					<td><input type="text" name="editname" value="${fn:escapeXml(param.editname)}"></td>
					<td>&nbsp;采编时间：</td>
					<td>
						<input type="text" name="editname_left" class="WebDate" style="width:90px;" format="yyyy-MM-dd" value="${fn:escapeXml(param.editname_left)}" require="false" id="editname_left" /> 至
						<input type="text" name="editname_right" class="WebDate" style="width:90px;" format="yyyy-MM-dd" value="${fn:escapeXml(param.editname_right)}" require="false" datatype="DateCheck" operator=">=" to="editname_left" />
					</td>
				</tr>
				<tr>
					<td>&nbsp;审核员：</td>
					<td><input type="text" name="auditname" value="${fn:escapeXml(param.auditname)}"></td>
					<td>&nbsp;审核时间：</td>
					<td>
						<input type="text" name="auditname_left" class="WebDate" style="width:90px;" format="yyyy-MM-dd" value="${fn:escapeXml(param.auditname_left)}" require="false" id="auditname_left" /> 至
						<input type="text" name="auditname_right" class="WebDate" style="width:90px;" format="yyyy-MM-dd" value="${fn:escapeXml(param.auditname_right)}" require="false" datatype="DateCheck" operator=">=" to="auditname_left" />
					</td>
				</tr>
			</table>
		</td>
		<td class="query"><input id="_querySubmit_" type="button" class="button" style="width:50%;height:50%;font-size:20px;margin:5px" value="查询" /></td>
	</tr>
</table>
<input type="hidden" name="siteid" value="${fn:escapeXml(siteid)}" />
<input type="hidden" name="categoryid" value="${fn:escapeXml(param.categoryid)}" />
</form>
<div class="line"></div>
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:7%">类型</td>
		<td style="width:35%">标题</td>
		<td style="width:7%">页面类型</td>
		<td style="width:16%">操作人员</td>
		<td style="width:16%">操作时间</td>
		<td style="width:7%">操作</td>
	</tr>
<c:forEach items="${pageModel.result}" var="d">
	<tr>
		<td>${d.pageid==0?'栏目':'内容'}</td>
		<td>${d.title}</td>
		<td>${d.scope==1?'单页':d.scope==2?'外链':''}</td>
		<td>
		<c:if test="${!empty d.editname}">
			${fn:escapeXml(d.editname)} (${fn:escapeXml(d.editid)})
		</c:if>
		<c:if test="${!empty d.auditname}">
			${fn:escapeXml(d.auditname)} (${fn:escapeXml(d.auditid)})
		</c:if>
		</td>
		<td>
			${fn:escapeXml(d.edittime)}
			${fn:escapeXml(d.audittime)}
		</td>
		<td>
		<c:if test="${!empty d.editname}">
			${d.status==-1?'提交删除':d.status==0?'提交新增':d.status==1?'提交修改':d.status==4?'撤销提交':d.status==8?'信息发布':''}
		</c:if>
		<c:if test="${!empty d.auditname}">
			${d.auditstatus==2?'审核不通过':d.auditstatus==4?'审核通过':''}
		</c:if>
		</td>
	</tr>
</c:forEach>
</table>
<table border="0" cellspacing="0" cellpadding="0" class="bottomTable">
	<tr><td>${pageNav.page}</td></tr>
</table>
</body>
</html>
