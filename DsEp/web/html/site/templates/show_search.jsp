<%@page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%common.cms.CmsFactory cms = (common.cms.CmsFactory)request.getAttribute("cms");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0,minimal-ui"/>
<title>${category.name} - ${site.name}</title>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/web.css"/>
<meta name="keywords" content="${category.metakeywords}"/>
<meta name="description" content="${category.metadescription}"/>
</head>
<body>
<%@include file="include/header.jsp"%>
<script type="text/javascript" src="${ctx}/js/jskey_page.js"></script>
<script type="text/javascript" src="${ctx}/js/laytpl/laytpl.js"></script>
<div class="w990 clear">
  <div class="w990">
	<div class="listpage hei1 view">
	  <div class="logo">&nbsp;&nbsp;当前位置：${category.name}</div>
	  <div class="content">
		<div id="search" style="margin:10px 0;"></div>
		<div id="dpage"></div>
	  </div>
	</div>
  </div>
</div>
<%@include file="include/footer.jsp"%>
</body>
<script id="tpl" type="text/tmpl">
找到关于“<span style="color:red;font-weight:bold;">{{ d.keyvalue }}</span>”的结果约<span style="font-size:14px;font-weight:bold;color:red;"> {{ d.size }} </span>条结果 <br/><br/>
{{# for(var i=0,len=d.rows.length; i<len; i++){ }}
<div style="border-top:1px solid #ddd;padding:10px;height:70px;">
	<a target="_blank" style="text-decoration: none;" href="${ctx}{{ d.rows[i].url }}"><span class="myxx" style="line-height:25px;font-size:16px;color:#000;">{{ d.rows[i].title }}</span></a><br/>
	<a target="_blank" style="text-decoration: none;color:black;" href="${ctx}/d.rows[i].url"><span class="myxx" style="line-height:25px;font-size:14px;color:#999;">{{ d.rows[i].summary }}</span></a>
</div>
{{# } }}
</script>
<script type="text/javascript">
var _a=location.href.split("?");
if(_a.length>1){
  var v="";
  _a=_a[1].split("&");
  for(var i=0;i<_a.length;i++){var x=_a[i].split("=");if(x.length > 1 && x[0] == "v"){v = x[1];}}
  if(v == ""){
	document.getElementById('search').innerHTML = "请输入关键字";
  }else{
	var tpl = document.getElementById('tpl').innerHTML;
	var u = '${ctx}/search/search.jsp?siteid=${site.id}&v='+v+'&page=';
	$('#vv').val(decodeURI(decodeURI(v)));
	$jskey.page({object:'dpage', template:1, fn:function(e){
		$.getJSON(u+e.page+"&r="+new Date(), function(res){
			e.size = res.size;
			e.page = res.page;
			e.pagesize = res.pagesize;
			e.redo();
			res.keyvalue = $('#vv').val();
			laytpl(tpl).render(res, function(render){
			    document.getElementById('search').innerHTML = render;
			    var x=document.getElementsByTagName("span");
			    for(var i = 0; i < x.length; i++){
			    	if(x[i].className == 'myxx' && typeof(x[i])!="undefined"){
			    		var xt = x[i].innerHTML;
			    		var reg=new RegExp(res.keyvalue,"g"); 
			    		xt = xt.replace(reg, "<span style=\"color:red;font-weight:bold;\">"+res.keyvalue+"</span>");
			    		x[i].innerHTML = xt;
			    	}
			    }
			});
		});
	}});
  }
}
</script>
</html>