<%@page language="java" contentType="text/html;charset=UTF-8" import="dswork.web.*,dswork.sso.service.*"%><%
String path = request.getContextPath();
boolean isOk = false;
String account = "";
try{
MyCookie cookie = new MyCookie(request, response);
String cookieTicket = cookie.getValue(dswork.sso.listener.SessionListener.COOKIETICKET);
if(cookieTicket != null){// 有cookie存在
	account = TicketService.getAccountByTicket(cookieTicket);
	if(account != null){isOk = true;}
}
}catch(Exception e){
}
%>
<!DOCTYPE html>
<html>
<html>
<head>
<meta charset="UTF-8" />
<title>密码重置</title>
<style type="text/css">
@charset "utf-8";
*{word-wrap:break-word;}
html,body,p,form,input{margin:0;padding:0;}
ul,ol,dl{list-style-type:none;}
html,body{*position:static;}
html{font-family:sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;}
input{font-family:inherit;font-size:inherit;font-style:inherit;font-weight:inherit;}
input{overflow:visible;vertical-align:middle;outline:none;}
body,input{font-family:"Microsoft Yahei","Hiragino Sans GB","Helvetica Neue",Helvetica,tahoma,arial,Verdana,sans-serif,"\5B8B\4F53";font-size:14px;color:#333;-webkit-font-smoothing:antialiased;-moz-font-smoothing:antialiased;}
body{line-height:1.6;}

.pt{padding-top:0;}
.pr{padding-right:0;}
.pb{padding-bottom:0;}
.pl{padding-left:0;}
.pd{padding:0;}
.ph{padding-left:0;padding-right:0;}
.pv{padding-top:0;padding-bottom:0;}

.pd-5{padding:5px;}.ph-5{padding-left:5px;padding-right:5px;}.pv-5{padding-top:5px;padding-bottom:5px;}
.pd-10{padding:10px;}.ph-10{padding-left:10px;padding-right:10px;}.pv-10{padding-top:10px;padding-bottom:10px;}
.pd-15{padding:15px;}.ph-15{padding-left:15px;padding-right:15px;}.pv-15{padding-top:15px;padding-bottom:15px;}
.pd-20{padding:20px;}.ph-20{padding-left:20px;padding-right:20px;}.pv-20{padding-top:20px;padding-bottom:20px;}
.pd-30{padding:30px;}.ph-30{padding-left:30px;padding-right:30px;}.pv-30{padding-top:30px;padding-bottom:30px;}
.pd-50{padding:50px;}.ph-50{padding-left:50px;padding-right:50px;}.pv-50{padding-top:50px;padding-bottom:50px;}

body{padding-top:50px;font-size:120%;line-height:2em;}
form{width:650px;margin:0 auto;border:3px solid #2996ff;border-radius:3px;}
div.fbox {}
.fbox .box {display:block;}

.box .left {float:left;display:block;width:180px;text-align:right;}
.box .right{display:block;text-align:left;} 
.box p{background-color:#2996ff;color:#fff;}

input.text{border:#c2c2c2 solid 1px;min-height:32px;width:300px;}
input.text:hover{border:#0192d0 solid 1px;}
input.button{border:none;background:none;line-height:1.5em;cursor:pointer;background-color:#2996ff;color:#fff;border-radius:3px;}
input.button:hover{background-color:#317ef3;}

#div_maskContainer{display:none;}
#div_maskBackground{z-index:9999;position:absolute;left:0px;top:0px;background-color:#fff;}
#div_maskMessage{z-index:10000;width:300px;padding:40px;font-site:14px;font-weight:bold;position:absolute;text-align:center;background-color:#fff;border:#999 solid 3px;}

</style>
<script type="text/javascript" src="<%=path%>/js/mini/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/mini/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/js/mini/jskey/jskey_mini.js"></script>
<script type="text/javascript">
$(function(){
	$dswork.callback = function(){if($dswork.result.type == 1){
		try{window.close();}catch(e){};
		location.href = "<%=path%>/pwd/pwd.jsp?x=" + Math.random();
	}};
});
</script>
</head>
<body>
<%if(isOk){%>
<form id="dataForm" method="post" action="pwdAction.jsp">
<div class="fbox">
	<div class="box clearfix">
		<p class="pd-20">您好<%=account%>，请填写相关信息。</p>
	</div>
	<div class="box clearfix pv-5 pb">
		<div class="left">原密码：</div>
		<div class="right"><input class="text" type="password" id="oldpassword" name="oldpassword" style="width:250px;" dataType="Require" maxlength="32" value="" /></div>
	</div>
	<div class="box clearfix pv-5 pb">
		<div class="left">新密码：</div>
		<div class="right"><input class="text" type="password" id="password" name="password" style="width:250px;" dataType="Require" maxlength="32" value="" /></div>
	</div>
	<div class="box clearfix pv-5 pb">
		<div class="left">确认密码：</div>
		<div class="right"><input class="text" type="password" id="password2" name="password2" style="width:250px;" dataType="Repeat" to="password" msg="两次输入的密码不一致" value="" /></div>
	</div>
	<div class="box clearfix pv-5 pb">
		<div class="left">验证码：</div>
		<div class="right"><input type="text" class="text" name="authcode" style="width:100px;" dataType="RequireCompact" msg="必填" maxlength="4" value="" /><img src="<%=path%>/authcode?width=90&height=32" style="cursor:pointer;vertical-align:middle;" onclick="this.src='<%=path%>/authcode?width=90&height=32&id=' + Math.random();" /></div>
	</div>
	
	<div class="box clearfix pv-5 pb">
		<div class="left">&nbsp;</div>
		<div class="right"><input id="dataFormSave" type="button" style="padding:10px 100px;" class="button" value="提交" /></div>
	</div>
</div>
</form>
<%} %>
</body>
</html>
