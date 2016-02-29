<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/upd.jsp" %>
<script type="text/javascript" src="/web/js/jskey/jskey_upload.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript">
$(function(){
	var myup = new $dswork.upload({sessionKey:"${v_session}",fileKey:"${v_file}",show:true});//ext默认是image,limit默认是10240,show默认是false
	var myinput = $("#upBtn");
	var ps = {
		url:myup.url + '?sessionkey=' + myup.sessionKey + '&filekey=' + myup.fileKey + '&ext=' + myup.ext + "&uploadone=true",
		browse_button : "upBtn",
		unique_names : false,
		filters : {
			max_file_size : myup.limit + "kb",
			mime_types: [{title : "zip,ZIP", extensions : "zip,ZIP"}]
		},
		debug:false,
		settings:{div:myup.show,success:function(data){// {arr:[{id,name,size,state,file,type,msg}],msg:"",err:""}
			if(typeof (data.err) == 'undefined'){alert("upload error");return false;}
			if(data.err != ''){alert(data.err);}
			var o,_has = myinput.attr("has") || "";
			var ok = false;
			for(var i = 0;i < data.arr.length;i++){o = data.arr[i];if(o.state == "1"){ok = true;}}
			myinput.val(ok ? myup.fileKey : _has);
			var v = myinput.val();
			if(v != "" && v != 0){myinput.attr("msg","");}
		}}
	};
	ps.custom_settings = ps.settings;
	ps.button_placeholder_id = ps.browse_button;
	ps.file_types = "zip";
	ps.file_size_limit = myup.limit;
	ps.init = ps.init || {};
	ps.init.FilesAdded = function(up, files){
		plupload.each(files, function(file){
			try{
				var arr = up.customSettings.uploadArray;
				up.customSettings.allSize+=file.size;
				arr[arr.length] = {"id":file.id,"name":file.name,"size":file.size,"state":"0","file":"","type":file.type,"msg":""},
				document.getElementById(up.customSettings.div).innerHTML = "等待中...";
			}
			catch(ex){
			}
		});
		layer.confirm('确认上传吗？', {icon: 3}, function(){
		    layer.msg('开始上传，请稍等...', {time:2000}, function(){
		    	up.start();
		    });
		});
	};
	
	ps.init.UploadComplete = ps.init.UploadComplete || function(up, files){
		try{
			var returnValue = {arr:[], msg:"", err:""};
			var o;
			for(var i = 0;i < up.customSettings.uploadArray.length;i++){
				o = up.customSettings.uploadArray[i];
		 		switch(o.state){
					case "-1":
						returnValue.err += o.name + o.msg + "\n";
						break;
					case "1":
						returnValue.msg += o.name + o.msg + "\n";
						break;
				}
				//id,name,size,state,file,type,msg
				returnValue.arr[returnValue.arr.length] = {"id":o.id,"name":o.name,"size":o.size,"state":o.state,"file":o.file,"type":o.type,"msg":o.msg};
			}
			
			up.customSettings.success(returnValue);
			$.post("uploadFile2.htm", {"siteid":"${siteid}","f_key":myup.fileKey, "path":"${path}"}, function(data){
				layer.msg('上传成功！', {icon: 1});
				setInterval(function(){parent.refreshNode(false);}, 2000);
			});
			up.customSettings.uploadArray = [];
		}
		catch(ex){
			alert(ex.message);
		}
	};
	
	$jskey.upload.init(ps);
});

</script>
<style type="text/css">
button{margin:30px auto;background-color:#00A2D4;color:#fff;top:50%;width:300px;height:80px;border:0px;font-size:32px;font-weight:bold;cursor:pointer}
button:hover{background-color:#0192D0;}
</style>
</head> 
<body style="background-color:#FAFAFA;text-align: center;">
	<button id="upBtn" name="upBtn" value="">点击上传文件</button>
	<div id="listShow" style="width:100%;"></div>
	<p style="text-align:left;margin-left:20px;color:red;font-size:16px;">文件上传注意事项</p>
	<hr style="width:100%;"></hr>
	<div style="margin:20px;text-align:left;">
		<p>1. 请需要上传的内容打包成"zip"文件</p>
		<p>2. 所有文件名仅允许大小写字母、数字、下划线、中划线</p>
		<p>3. 文件夹名称不能有小数点</p>
		<p>4. 文件名称必须带上后缀名，且后缀名不能有大写字母</p>
		<p>5. 允许文件类型：${hz}</p>
	</div>
</body>
</html>
