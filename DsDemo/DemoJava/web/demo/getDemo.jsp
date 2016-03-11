<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
<script type="text/javascript" src="/web/js/jquery/jquery.js"></script>
<script type="text/javascript" src="/web/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="/web/js/jskey/jskey_core.js"></script>
<script type="text/javascript" src="/web/js/layui/laytpl.js"></script>

</head> 
<body>
<div style="width:1000px;margin:50px auto;">
	<div id="dview" style="margin:10px 0;"></div>
	<div id="dpage"></div>
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
			<a href="/DsWorkJava/manage/my/delete/Demo.htm?keyIndex={{ d.rows[i].id }}">删除</a> | 
			<a href="getDemoById.jsp?keyIndex={{ d.rows[i].id }}">明细</a>
		</td>
	</tr>
{{# } }}
</table>
</script>
<script type="text/javascript">
var tpl = document.getElementById('tpl').innerHTML;
$jskey.page({object:'dpage', size:data.size, pagesize:data.pagesize, page:1, template:1, jump:function(e){
	$.getJSON('/DsWorkJava/manage/my/page/Demo.htm?page='+e.page+"&r="+new Date(), function(res){
		e.size = res.size;
		laytpl(tpl).render(res, function(render){
		    document.getElementById('dview').innerHTML = render;
		    
		});
	});
}});
</script>
</body>
</html>
