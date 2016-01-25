<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<title>DEMO</title>
	<script type="text/javascript" src="./js/jquery/jquery.js"></script>
	<script type="text/javascript" src="./js/jskey/jskey_core.js"></script>
	<script type="text/javascript" src="./js/laytpl/laytpl.js"></script>
<script type="text/javascript">
// 扩展菜单写法
function changeLis(){
	var demo = $("#demo").val();
	if(demo == "Demo")
		location.href="/DsWorkJava/demo/getDemo.jsp";
	else
		location.href="/DsWorkJava/demo2/getDemo.jsp";
}
</script>
<style>
table.zwtable{width:100%;background-color:#000;}
table.zwtable td{background-color:#fff;line-height:20px;font-size:14px;text-align:left;padding:5px;}
table.zwtable td.rt{text-align:right;}
table.zwtable td.tt{text-align:center;background-color: #187FC4;color: #fff;}
</style>
</head> 
<body>
<div style="width:1000px;margin:50px auto;">
	<section>
		<div>
			<select id="demo" name="demo" style="width:135px;" onchange="changeLis()">
				<option value="Demo2">Demo2</option>
				<option value="Demo">Demo</option>
			</select>
		</div>
		<div id="dview" style="margin:10px 0;"></div>
		<div id="dpage"></div>
	</section>
</div>
<br />
<script id="tpl" type="text/tmpl">
<table border="0" cellpadding="0" cellspacing="1" class="zwtable">
<tr><td colspan="3"><a href="addDemo.jsp">添加</a></td></tr>
<tr><td class="tt">标题</td><td class="tt" style="width:25%">时间</td><td class="tt" style="width:15%">操作</td></tr>
{{# for(var i=0,len=d.rows.length; i<len; i++){ }}
	<tr>
		<td>{{ d.rows[i].title }}</td>
		<td style="text-align:center;">{{ d.rows[i].foundtime }}</td>
		<td style="text-align:center;">
			<a href="updDemo.jsp?keyIndex={{ d.rows[i].id }}">修改</a> | 
			<a href="/DsWorkJava/manage/my/delete/Demo2.htm?keyIndex={{ d.rows[i].id }}">删除</a> | 
			<a href="getDemoById.jsp?keyIndex={{ d.rows[i].id }}">明细</a>
		</td>
	</tr>
{{# } }}
</table>
</script>
<script type="text/javascript">
var tpl = document.getElementById('tpl').innerHTML;
$.getJSON('/DsWorkJava/manage/my/page/Demo.htm?page=1'+new Date(), function(data){
	$jskey.page({object:'dpage', size:data.size, pagesize:data.pagesize, page:1, template:1, jump:function(e){
		$.getJSON('/DsWorkJava/manage/my/page/Demo.htm?page='+e.page+"&r="+new Date(), function(res){
			e.size = res.size;
			laytpl(tpl).render(res, function(render){
			    document.getElementById('dview').innerHTML = render;
			});
		});
	}});
});
</script>
</body>
</html>
