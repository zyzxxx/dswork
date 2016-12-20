<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0,minimal-ui"/>
<title></title>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jskey_page.js"></script>
<script type="text/javascript" src="js/laytpl.js"></script>
<style type="text/css">
a:link,a:visited,a:active{outline:none;text-decoration:underline;}
a:hover{outline:none;text-decoration:underline;}
span.keyvalue {color:red;}
span.title {line-height:25px;font-size:16px;}
span.summary {line-height:20px;font-size:12px;color:black;}
</style>
</head>
<body>
<div id="search" style="margin:10px 0;"></div>
<div id="dpage"></div>
</body>
<script id="tpl" type="text/tmpl">
找到关于“<span style="color:red;font-weight:bold;">{{ d.keyvalue }}</span>”的结果约<span style="font-size:14px;font-weight:bold;color:red;"> {{ d.size }} </span>条结果 <br/><br/>
{{# for(var i=0,len=d.rows.length; i<len; i++){ }}
<div style="border-top:1px solid #ddd;padding:10px;">
	<a target="_blank" href="{{ d.rows[i].url }}"><span class="title">{{ d.rows[i].title }}</span></a>
	<br/><span class="summary">{{ d.rows[i].summary }}</span>
</div>
{{# } }}
</script>
<script type="text/javascript">
var _a = top.location.href.split("?");
if(_a.length > 1){
	var v = "";
	_a = _a[1].split("&");
	for(var i = 0; i < _a.length; i++){
		var x = _a[i].split("=");
		if(x.length > 1 && x[0] == "v"){v = x[1];}
	}
	if(v == ""){document.getElementById('search').innerHTML = "请输入关键字";}
	else{
		var tpl = document.getElementById('tpl').innerHTML;
		var u = "searchJson.jsp?v="+v+"&page=";
		var vv = decodeURIComponent(decodeURIComponent(v));
		try{parent.$('#vv').val(vv);}catch(ex){}
		$jskey.page({object:'dpage',template:1,fn:function(e){
			$.getJSON(u+e.page+"&r="+(new Date().getTime()), function(res){
				e.size = res.size;
				e.page = res.page;
				e.pagesize = res.pagesize;
				e.redo();
				res.keyvalue = vv;
				laytpl(tpl).render(res, function(render){
					document.getElementById('search').innerHTML = render;
					<%--
					var x = document.getElementsByTagName("span");
					for(var i = 0; i < x.length; i++){
						if(x[i].className == 'myxx' && typeof (x[i]) != "undefined"){
							var xt = x[i].innerHTML;
							var reg = new RegExp(res.keyvalue, "g");
							xt = xt.replace(reg, "<span style=\"color:red;font-weight:bold;\">" + res.keyvalue + "</span>");
							x[i].innerHTML = xt;
						}
					}
					--%>
					try{parent.document.getElementById("zwiframe").style.height = ($(document.body).height() + 100) + "px";}catch(ex){}
				});
			});
		}});
	}
}
</script>
</html>