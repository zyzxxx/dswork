<%@page contentType="text/html; charset=UTF-8"%><%
response.setHeader("Cache-Control","no-store");
response.setDateHeader("Expires", 0);
response.setHeader("Pragma","no-cache"); 
%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><c:set var="ctx" value="${pageContext.request.contextPath}"
/><!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
<title>统一身份认证平台</title>
<script type="text/javascript">if(top.location != this.location){top.location = this.location;}</script>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/share/fonts/dsworkfont.css"/>
<style type="text/css">
html,body{height:100%;margin:0px auto;}
body {background-color:#fff;}
*{-webkit-appearance:none;padding:0;margin:0;font-family:arial,"microsoft yahei","宋体";border-radius:0;}
div,input,a{font-weight:bold;font-size:20px;line-height:38px;}
div,input,label{color:#333;}
a{text-decoration:underline;outline:none;}
a:link,a:visited,a:active{color:#0000bb;outline:none;}
a:hover{color:#0000ff;text-decoration:underline;}
.bg{width:100%;height:730px;position:absolute;top:70px;left:0;z-index:-1;background:url(${ctx}/themes/share/bg/login.gif) no-repeat top center;}
.view{position:relative;width:100%;min-width:300px;overflow:hidden;margin:0 auto;overflow:hidden;}
.title {color:#003c7b;font-size:38px;font-weight:bold;text-align:center;padding:80px 0 38px 0;}
.title img{height:60px;vertical-align:middle;}
.login{border:#003c7b solid 1px;padding:0;overflow:hidden;background-color:#fff;float:right;margin:0 50px 0 auto;width:360px;}
.box{overflow:hidden;text-align:center;width:100%;margin:0 auto 12px auto;padding:0;border:none;}
.box .title{background-color:#003c7b;border-bottom:#003c7b solid 1px;color:#fff;width:100%;padding:3px 0;line-height:50px;font-size:22px;text-align:center;margin:0 auto;}
.box .vbox{margin:0 auto;padding:0;overflow:hidden;text-align:left;vertical-align:middle;width:250px;}
.box .vbox span{font-family:dsworkfont;margin:0 10px;color:#003c7b;}
.box .vbox input{width:198px;height:35px;padding-left:8px;vertical-align:middle;background-color:inherit;border:#ccc solid 1px;}
.box .vbox input.code{width:98px;}
.box .vbox img{border:none;cursor:pointer;vertical-align:middle;}
.box .button{background-color:#003c7b;color:#eee;width:280px;height:50px;line-height:50px;cursor:pointer;border:none;}
.box .button:hover{background-color:#da3b01;color:#fff;}
.box .checkbox{vertical-align:middle;}
.box label{font-weight:bold;font-size:16px;line-height:18px;}
.box label.left{float:left;margin-left:38px;}
.box label.right{float:right;margin-right:38px;}
.box label a{font-size:16px;line-height:18px;text-decoration:none;}
.cp{color:#666;font-size:12px;width:80%;overflow:hidden;text-align:center;padding:15px 0;margin:20px auto 0 auto;border:none;}
.cp a {font-size:12px;font-weight:normal;font-family:arial;}

@media only screen and (min-width:768px) and (max-width:999px){.title{text-align:center;}}
@media only screen and (max-width:767px){.bg{background:none;}.title{font-size:22px;text-align:center;padding:40px 0 35px 0;}.login{float:none;margin:0 auto;}}
@media only screen and (max-width:480px){.bg{background:none;}.title{text-align:center;}.login{float:none;margin:0 auto;}}
@media only screen and (max-width:361px){.login{border-left:none;border-right:none;}}

</style>
</head>
<body>
<div class="bg"></div>
<div class="view">
  <div class="title"><canvas id="canvas"></canvas><img src="${ctx}/themes/share/bg/o.png" />&nbsp;统一身份认证平台</div>
  <form id="w" action="loginAction" method="post">
  <div class="login">
	<div class="box"><div class="title">用户登录</div></div>
	<div class="box"><div class="vbox">
		<span>&#xf1001;</span><input type="text" id="account" name="account" autocomplete="off" value="" title="账号" placeholder="账号" />
	</div></div>
	<div class="box"><div class="vbox">
		<span>&#xf1002;</span><input type="password" id="password" name="password" autocomplete="off" value="" title="密码" placeholder="密码" />
	</div></div>
	<div class="box"><div class="vbox">
		<span>&#xf1026;</span><input type="text" id="authcode" name="authcode" autocomplete="off" maxlength="4" class="code" value="" title="验证码" placeholder="" />
		<img id="mycode" alt="请点击" style="width:90px;height:38px;" src="about:blank" onclick="this.src='${ctx}/authcode?r=' + Math.random();" />
	</div></div>
	<div class="box">
		<input type="button" class="button" value="登 录" onclick="doclick()" />
	</div>
	<div class="box">
		<label class="right">&nbsp;&nbsp;<input id="savename" type="checkbox" autocomplete="off" class="checkbox" onclick="">&nbsp;记住用户名&nbsp;</label>
	</div>
  </div>
  <input type="hidden" name="service" value="${fn:escapeXml(service)}" />
  </form>
</div>
<div class="cp">
	Copyright &copy; 2014-2017
</div>
<div style="display:none;"><img id="logo" src="${ctx}/themes/share/bg/o.png" /></div>
<c:if test="${errorMsg != ''}"><script type="text/javascript">alert("${errorMsg}");</script></c:if>
</body>
<script type="text/javascript" src="${ctx}/js/jskey/jskey_md5.js"></script>
<script type="text/javascript">
function _$(id){return document.getElementById(id);}
var dd = document, cc = "coo" + "kie";
function setCoo(k,v,d){var x=new Date();x.setDate(x.getDate()+d);dd[cc]=k+"="+escape(v)+((d==null)?"":";expires="+x.toGMTString());}
function getCoo(k){if(dd[cc].length>0){var x1=dd[cc].indexOf(k+"=");if(x1!=-1){x1=x1+k.length+1;x2=dd[cc].indexOf(";",x1);if(x2==-1){x2=dd[cc].length;}return unescape(dd[cc].substring(x1,x2));}}return "";}
function doclick(){
	var s = "";
	if(!_$("account").value){s += "账号不能为空\n";}
	if(!_$("password").value){s += "密码不能为空\n";}
	if(!_$("authcode").value){s += "验证码不能为空\n";}
	if(s != ""){alert(s);return;}
	if(_$("savename").checked){setCoo("savename",_$("account").value,365);}else{setCoo("savename","",0);}
	try{_$("password").value = $jskey.md5($jskey.md5(_$("password").value)+_$("authcode").value);}catch(e){}
	_$("w").submit();
}
_$("mycode").click();
var _x = getCoo("savename");
if(_x.length > 0){
	_$("account").value = _x;
	_$("savename").checked = true;
}else {
	_$("account").value = "";
	_$("savename").checked = false;
}
_$("password").value = "";
_$("authcode").value = "";
_$((_$("account").value == "")?"account":"password").focus();

function registEvent($e, et, fn){$e.attachEvent ? $e.attachEvent("on"+et, fn) : $e.addEventListener(et, fn, false);}
function registKeydown(id){registEvent(_$(id), "keydown", function(event){if(event.keyCode == 13){doclick();}});}
registKeydown("account");
registKeydown("password");
registKeydown("authcode");
</script>

<script>
    var canvas = document.getElementById("canvas");
    var ctx = canvas.getContext('2d');
    var img;

    var mouseX = null, mouseY = null;
    var mouseRadius = 10;

    var RAF = (function () {
        return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function (callback) {
                    window.setTimeout(callback, 1000 / 60);
                };
    })();
    window.onmousemove = function (e) {
        if (e.target.tagName == "CANVAS") {
            mouseX = e.clientX - e.target.getBoundingClientRect().left;
            mouseY = e.clientY - e.target.getBoundingClientRect().top;
        } else {
            mouseX = null;
            mouseY = null;
        }
    };
    Array.prototype.forEach = function (callback) {
        for (var i = 0; i < this.length; i++) {
            callback.call((typeof this[i] === "object") ? this[i] : window, i, this[i]);
        }
    };
    var particleArray = [];
    var animateArray = [];
    var particleSize_x = 1;
    var particleSize_y = 2;
    var canvasHandle = {
        init: function () {
            this._reset();
            this._initImageData();
            this._execAnimate();
        },
        _reset: function () {
            particleArray.length = 0;
            animateArray.length = 0;
            this.ite = 100;
            this.start = 0;
            this.end = this.start + this.ite;
        },
        _initImageData: function () {
            this.imgx = (canvas.width - img.width) / 2;
            this.imgy = (canvas.height - img.height) / 2;
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            ctx.drawImage(img, this.imgx, this.imgy, img.width, img.height);
            var imgData = ctx.getImageData(this.imgx, this.imgy, img.width, img.height);
            for (var x = 0; x < img.width; x += particleSize_x) {
                for (var y = 0; y < img.height; y += particleSize_y) {
                    var i = (y * imgData.width + x) * 4;
                    if (imgData.data[i + 3] >= 125) {
                        var color = "rgba(" + imgData.data[i] + "," + imgData.data[i + 1] + "," + imgData.data[i + 2] + "," + imgData.data[i + 3] + ")";
                        var x_random = x + Math.random() * 20,
                                vx = -Math.random() * 200 + 400,
                                y_random = img.height/2 - Math.random() * 40 + 20,
                                vy;
                        if (y_random < this.imgy + img.height / 2) {
                            vy = Math.random() * 300;
                        } else {
                            vy = -Math.random() * 300;
                        }
                        particleArray.push(
                                new Particle(
                                        x_random + this.imgx,
                                        y_random + this.imgy,
                                        x + this.imgx,
                                        y + this.imgy,
                                        vx,
                                        vy,
                                        color
                                )
                        );
                        particleArray[particleArray.length - 1].drawSelf();
                    }
                }
            }
        },
        _execAnimate: function () {
            var that = this;
            particleArray.sort(function (a, b) {
                return a.ex - b.ex;
            });
            if (!this.isInit) {
                this.isInit = true;
                animate(function (tickTime) {
                    if (animateArray.length < particleArray.length) {
                        if (that.end > (particleArray.length - 1)) {
                            that.end = (particleArray.length - 1)
                        }
                        animateArray = animateArray.concat(particleArray.slice(that.start, that.end))
                        that.start += that.ite;
                        that.end += that.ite;
                    }
                    animateArray.forEach(function (i) {
                        this.update(tickTime);
                    })
                })
            }
        }
    }

    var tickTime = 16;
    function animate(tick) {
        if (typeof tick == "function") {
            var tickTime = 16;
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            tick(tickTime);
            RAF(function () {
                animate(tick)
            })
        }
    }
    function Particle(x, y, ex, ey, vx, vy, color) {
        this.x = x;
        this.y = y;
        this.ex = ex;
        this.ey = ey;
        this.vx = vx;
        this.vy = vy;
        this.a = 1500;
        this.color = color;
        this.width = particleSize_x;
        this.height = particleSize_y;
        this.stop = false;
        this.static = false;
        this.maxCheckTimes = 10;
        this.checkLength = 5;
        this.checkTimes = 0;
    }


    var oldColor = "";
    Particle.prototype = {
        constructor: Particle,
        drawSelf: function () {
            if (oldColor != this.color) {
                ctx.fillStyle = this.color;
                oldColor = this.color
            }
            ctx.fillRect(this.x - this.width / 2, this.y - this.height / 2, this.width, this.height);
        },
        move:function(tickTime){
            if (this.stop) {
                this.x = this.ex;
                this.y = this.ey;
            } else {
                tickTime = tickTime / 1000;
                var cx = this.ex - this.x;
                var cy = this.ey - this.y;
                var angle = Math.atan(cy / cx);
                var ax = Math.abs(this.a * Math.cos(angle));
                ax = this.x > this.ex ? -ax : ax
                var ay = Math.abs(this.a * Math.sin(angle));
                ay = this.y > this.ey ? -ay : ay;
                this.vx += ax * tickTime;
                this.vy += ay * tickTime;
                this.vx *= 0.95;
                this.vy *= 0.95;
                this.x += this.vx * tickTime;
                this.y += this.vy * tickTime;
                if (Math.abs(this.x - this.ex) <= this.checkLength && Math.abs(this.y - this.ey) <= this.checkLength) {
                    this.checkTimes++;
                    if (this.checkTimes >= this.maxCheckTimes) {
                        this.stop = true;
                    }
                } else {
                    this.checkTimes = 0
                }
            }
        },
        update: function (tickTime) {
            this.move(tickTime);
            this.drawSelf();
            this._checkMouse();
        },
        _checkMouse: function () {
            var that = this;
            if (!mouseX) {
                goback();
                return;
            }
            var distance = Math.sqrt(Math.pow(mouseX - this.x, 2) + Math.pow(mouseY - this.y, 2));
            var angle = Math.atan((mouseY - this.y) / (mouseX - this.x));
            if (distance < mouseRadius) {
                this.stop = false;
                this.checkTimes = 0;
                if (!this.recordX) {
                    this.recordX = this.ex;
                    this.recordY = this.ey;
                }
                this.a = 2000 + 1000 * (1-distance/mouseRadius);
                var xc = Math.abs((mouseRadius - distance) * Math.cos(angle));
                var yc = Math.abs((mouseRadius - distance) * Math.sin(angle));
                xc = mouseX > this.x ? -xc : xc;
                yc = mouseY > this.y ? -yc : yc;
                this.ex = this.x + xc;
                this.ey = this.y + yc;
            } else {
                goback();
            }
            function goback(){
                if (that.recordX) {
                    that.stop = false;
                    that.checkTimes = 0;
                    that.a = 1500;
                    that.ex = that.recordX;
                    that.ey = that.recordY;
                    that.recordX = null;
                    that.recordY = null;
                }
            }
        }
    };
    function useImage() {
        img = document.getElementById("logo");
        if (img.complete) {
            canvas.width = img.width;
            canvas.height = img.height;
            canvasHandle.init();
        } else {
            img.onload = function () {
                canvasHandle.init();
            }
        }
    }
    useImage()
</script>
</html>
