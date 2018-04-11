<%@page import="common.lucene.LuceneUtil"%>
<%@page contentType="text/html; charset=UTF-8"%><%
String[] arr = LuceneUtil.getTypeArray();
%><!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
<title></title>
<style>
html, body{width:100%;height:100%;}
body{margin:0 auto;font-family:Tahoma, Verdana, Arial, sans-serif, 微软雅黑;}
</style>
<script type="text/javascript" src="/search/js/jquery.js"></script>
<script type="text/javascript">
function my(){
	var s = "";
	$("input[name='type']").each(function(){ 
		if($(this).attr("checked")){
			if(s != ""){
				s += ",";
			}
			s += $(this).val();
		}
	});
	if(s != ""){
		s = "&type=" + encodeURIComponent(encodeURIComponent(s));
	}
	document.getElementById("zwiframe").src="/search/search.jsp?v=" + encodeURIComponent(encodeURIComponent(document.getElementById('vv').value)) + s;
}
</script>
</head>
<body>
<%
for(int i=0; i<arr.length; i++){
	%><label><input type="checkbox" name="type" value="<%=arr[i]%>"/><%=arr[i]%></label>&nbsp;&nbsp;&nbsp;<%
}
%>
	<input id="vv" type="text" name="vv" value="" />
	<input type="button" value="搜索" onclick="my();" /><br />
	<iframe id="zwiframe" src="about:blank" style="border:none;width:100%;height:80%;"></iframe>
</body>
</html>
