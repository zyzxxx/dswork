<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="common.cms.CmsFactory"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%CmsFactory cms = (CmsFactory)request.getAttribute("cms");%>
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
<div class="w990 clear">
  <div class="w990 right">
	<div class="listpage hei1 view">
	  <div class="logo">&nbsp;&nbsp;当前位置：${category.name}</div>
	  <div class="content">
		<div id="zxdc"></div>
	  </div>
	</div>
  </div>
</div>
<%@include file="include/footer.jsp"%>
<script type="text/javascript">
$.getJSON('/zw/zwclient/survey/surveyJsonAction.jsp', function(data){
	var result = data.result;
	var zxdc="";
	for(var i = 0; i < result.length; i++){
		var d = result[i];
		var t;
// 		if(i == 0){
			t = "<div class=\"vline\">&nbsp;</div><a style=\"text-decoration: none;\" onclick=\"showLogo("+i+");return false;\" href=\"#\"><div id=\"logo"+(i+1)+"\" class=\"r_logo\"><span style=\"font-size:20px;\">在线调查"+(i+1)+"</span></div></a><div style=\"display:;\" id=\"box"+(i+1)+"\" class=\"box\"><form style=\"padding-left:10px;\" method=\"post\" action=\"/zw/zwclient/survey/surveyAction.jsp?keyIndex="+d.id+"\">";
// 		}else{
// 			t = "<div class=\"vline\">&nbsp;</div><a style=\"text-decoration: none;\" onclick=\"showLogo("+i+");return false;\" href=\"#\"><div id=\"logo"+(i+1)+"\" class=\"r_logo\"><span>在线调查"+(i+1)+"</span></div></a><div style=\"display:;\" id=\"box"+(i+1)+"\" class=\"box\"><form style=\"padding-left:10px;\" method=\"post\" action=\"/zw/zwclient/surveyAction.jsp?keyIndex="+d.id+"\">";
// 		}
		
		t = t + "<ul style=\"list-style:none;\"><h4>"+d.title+"</h4>";
		for(var j=1; j<16; j++){
			if(d["option"+j] != ""){
				t = t + "<li><label><input type=\"radio\" id=\"choice" + j + "\" name=\"choice\" value=\"" + j + "\"/>&nbsp;&nbsp;"+d["option"+j]+"</label></li>";
			}
		}
		t = t+"</ul><br/><input type=\"submit\" style=\"width: 50px;height:20px;\" value=\"投票\"/>  <a style=\"text-decoration: none;\" href=\"/zw/zwclient/survey/surveyResult.html?keyIndex="+d.id+"\"\">查看结果</a></form><div class=\"vline\">&nbsp;</div></div>";
		zxdc = zxdc + t;
	}
	document.getElementById('zxdc').innerHTML = zxdc;
});
</script>
</body>
</html>