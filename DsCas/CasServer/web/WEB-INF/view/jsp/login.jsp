<%@page language="java" pageEncoding="utf-8"%><%
response.setHeader("Cache-Control","no-store");
response.setDateHeader("Expires", 0);
response.setHeader("Pragma","no-cache"); 
%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<script type="text/javascript" src="./jquery.js"></script>
<script type="text/javascript" src="/web/js/jskey/jskey_des.js"></script>
<script type="text/javascript">
function _$(id){return document.getElementById(id);}
if(top.location != this.location){top.location = this.location;}
function doclick(){
var s = "";
if(!_$('account').value){s += "账号不能为空\n";}
if(!_$('password').value){s += "密码不能为空\n";}
//if(!$('authcode').value){s += "验证码不能为空\n";}
if(s != ""){alert(s);return;}
_$('password').value = $jskey.encodeDes(_$('password').value, "${code}");
_$('v').submit();
}
function registKeydown(id) {
	$("#" + id).keydown(function(event) {
		if (event.keyCode == 13) {
			doclick();
		}
	});
}
$(function(){
	//登录后弹出提示框，如果登录出错。
	if ("${errorMsg}" != "") {
		alert("${errorMsg}");
	}
	//给表单注册回车即可提交。
	registKeydown("account");
	registKeydown("password");
	registKeydown("authcode");

	//账号获得焦点并且选中文本。
	$("#account").focus();
	$("#account").select();
	$("#account").val("${fn:escapeXml(account)}");
	$("#password").val("${fn:escapeXml(password)}");
});
</script>
<style type="text/css">
html,body{height:100%;margin:0px auto;}*{padding:0px;}
div, input{font-family:"宋体";font-weight:bold;font-size:20px;line-height:20px;}
.content{margin:0px auto;position:relative;width:1000px;height:473px;background:url(/web/share/login.gif) no-repeat center center;overflow:hidden;}
.title{color:#184760;width:100%;font-size:30px;text-align:center;padding:50px 0px;}
.login{position:absolute;left:545px;top:120px;width:400px;height:300px;overflow:hidden;border:#ccc solid 1px;border-radius:20px 0px;}

.loginTit{color:#184760;height:50px;line-height:50px;padding:10px 15px 10px 75px;font-size:22px;background:url(/web/share/user.gif) no-repeat 15px center;}
.box{overflow:hidden;padding:0px 0px 20px 70px;text-align:left;color:#666;}

input{width:180px;height:30px;border:#BAC7D2 solid 1px;vertical-align:middle;background-color:#f9f9f9;}
.code{width:80px;height:35px;line-height:35px;}
.button{width:120px;height:40px;line-height:40px;border-radius:4px;cursor:pointer;}
a {color:#0000ff;font-size:12px;font-weight:normal;line-height:40px;font-family:Arial;}
</style>
</head>
<body>
<div class="content">
  <div class="title">统一认证平台</div>
  <form id="v" action="loginAction.htm" method="post">
  <div class="login">
	<div class="loginTit">用户登录</div>
	<div class="box">
		用户名 <input type="text" title="账号" id="account" name="account" value="" />
	</div>
	<div class="box">
		密　码 <input type="password" title="密码" id="password" name="password" value="" />
	</div>
	<div class="box">
		验证码 <input type="text" placeholder="验证码" id="authcode" name="authcode" maxlength="4" class="code" value="" />
		<img src='./AuthCode?width=90&height=38' style="cursor:pointer;vertical-align:middle;" onclick="this.src='./AuthCode?width=90&height=38&id=' + Math.random();" />
	</div>
	<div class="box">
		<input type="button" class="button" style="background-color:#00a0f0;color:#fff;" value="登 录" onclick="doclick()" />
		<input type="reset"  class="button" style="background-color:#dadada;color:#000;" value="取消" />
	</div>
  </div>
  <input type="hidden" name="service" value="${service}" />
  </form>
</div>
<div style="margin:0px auto;border-top:solid #ccc 1px;width:1000px;color:#333;text-align:center;font-size:12px;font-weight:normal;line-height:40px;font-family:Arial;">
	Copyright &copy; 2014 skey_chen@163.com
</div>
</body>
</html>
