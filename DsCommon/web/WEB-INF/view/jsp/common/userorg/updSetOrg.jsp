<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<%@include file="/commons/include/upd.jsp" %>
	<script type="text/javascript">
	var data = parent.$jskey.dialog.dialogArguments.args.data;
	var list = data.uList;
	var arr = ["0", <c:forEach items="${list}" var="d">,"${d.userid}"</c:forEach>];
	for(var i=0; i<list.length; i++){
		var o = list[i];o.check = false;
		for(var j=1; j<arr.length; j++){if(o.id == arr[j]){o.check = true;}}
	}
	function refreshValue(){
		var ids = "0";
		$("input[name='keyIndex']:checked").each(function(){
			ids += "," + $(this).val();
		});
		alert(ids);
		parent.$jskey.dialog.returnValue = ids;
	}
	$(function(){
		t = $("#dataTable");
		for(var i = 0; i < list.length; i++){m=list[i];
			var r = $("<tr></tr>"),chk;
			if(m.check){
				chk = $("<input name='keyIndex' type='checkbox' checked='checked' value='' />");
			}
			else{
				chk = $("<input name='keyIndex' type='checkbox' value='' />");
			}
			chk.val(m.id);
			chk.click(function(){refreshValue();});
			$("<td></td>").append(chk).appendTo(r);
			$("<td></td>").html(m.name).appendTo(r);
			t.append(r);
		}
		refreshValue();
	});
	</script>
</head>
<body>
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:2%">&nbsp;</td>
		<td>姓名(帐号)</td>
	</tr>
</table>
</body>
</html>
