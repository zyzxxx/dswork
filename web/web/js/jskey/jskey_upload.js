if(typeof ($jskey) != "object")
{
	$jskey = {};
}
$jskey.formatBytes = function(bytes)
{
	var s = ['Byte', 'KB', 'MB', 'GB', 'TB', 'PB'];
	var e = Math.floor(Math.log(bytes)/Math.log(1024));
	return (bytes/Math.pow(1024, Math.floor(e))).toFixed(2)+" "+s[e];
};

$jskey.upload =
{

//js的引用路径
jsPath:document.getElementsByTagName("script")[document.getElementsByTagName("script").length - 1].src.substring(0, document.getElementsByTagName("script")[document.getElementsByTagName("script").length - 1].src.lastIndexOf("/") + 1),

init:function(s)
{
	s.url = s.url;
	s.flash_swf_url = '/web/js/plupload/Moxie.swf';
	s.silverlight_xap_url = '/web/js/plupload/Moxie.xap';
	s.runtimes = s.runtimes || 'html5,flash,silverlight,html4';
	s.unique_names = s.unique_names || false;//当值为true时会为每个上传的文件生成一个唯一的文件名，并作为额外的参数post到服务器端，参数明为name,值为生成的文件名。
	//s.browse_button = s.browse_button;
	s.multipart = true;////为true时将以multipart/form-data的形式来上传文件，为false时则以二进制的格式来上传文件。html4上传方式不支持以二进制格式来上传文件，在flash上传方式中，二进制上传也有点问题。并且二进制格式上传还需要在服务器端做特殊的处理。一般我们用multipart/form-data的形式来上传文件就足够了。
	//s.file_data_name = s.file_data_name || "file";
	//s.multipart_params : {},// {one:'1',four:['6','7','8'],three:{a:'4',b:'5'}}
	s.filters = s.filters || {};
	s.filters.max_file_size = s.filters.max_file_size || "10240kb";
	s.filters.mime_types = s.filters.mime_types || "10240kb";
	//s.filters.prevent_duplicates = true;//不允许选取重复文件，如果两个文件的文件名和大小都相同，则会被认为是重复的文件
	s.max_retries = 0;//错误时重试次数，0为不重试
	s.chunk_size = s.chunk_size || 0;
	        // PreInit events, bound before the internal events
	s.preinit = s.preinit || {};
	s.preinit.Init = s.preinit.Init || function(up, info){log('[Init]');};
	s.preinit.UploadFile = s.preinit.UploadFile || function(up, file) {log('[UploadFile]');};
	 
	s.init = s.init || {};
	s.init.PostInit = s.init.PostInit || function(){log('[PostInit]');};//document.getElementById('uploadfiles').onclick = function(){uploader.start();return false;};

	s.init.Browse = s.init.Browse || function(up){log('[Browse]');};
	s.init.Refresh = s.init.Refresh || function(up){log('[Refresh]');};
	s.init.StateChanged = s.init.StateChanged || function(up){log('[StateChanged]', up.state == plupload.STARTED ? "STARTED" : "STOPPED");};

	s.init.QueueChanged = s.init.QueueChanged || function(up){log('[QueueChanged]');};
	s.init.OptionChanged = s.init.OptionChanged || function(up, name, value, oldValue){log('[OptionChanged]', 'Option Name: ', name, 'Value: ', value, 'Old Value: ', oldValue);};

	s.init.BeforeUpload = s.init.BeforeUpload || function(up, file){
		log('[BeforeUpload]', 'File: ', file);
		try
		{
			document.getElementById(up.customSettings.div).innerHTML = file.name + "开始上传...";
		}
		catch(ex)
		{
		}
		return true;
	};
	s.init.UploadProgress = s.init.UploadProgress || function(up, file){
		log('[UploadProgress]', 'File:', file, "Total:", up.total);
		try
		{
			var p = up.customSettings;
			var allPercent = Math.ceil((p.uploadSize+file.loaded)/p.allSize*100);
			if(allPercent == 100)
			{
				document.getElementById(p.div).innerHTML = "上传已完成：" + "100% (" + $jskey.formatBytes(p.allSize) + ")";
			}
			else
			{
				var percentText = $jskey.formatBytes(p.uploadSize+file.loaded)+' / '+$jskey.formatBytes(p.allSize);
				document.getElementById(p.div).innerHTML = "上传已完成：" + allPercent + "% (" + percentText + ")<br />" + file.name + "上传了：" + file.percent + "%";
			}
		}
		catch(ex)
		{
			alert(ex.message);
		}
	};

	s.init.FileFiltered = s.init.FileFiltered || function(up, file){log('[FileFiltered]', 'File: ', file);};
	 

	s.init.FilesAdded = s.init.FilesAdded || function(up, files){
		log('[FilesAdded]');
		plupload.each(files, function(file){
			try
			{
				up.customSettings.allSize+=file.size;
				//id,name,size,state,file,type,msg
				arr[arr.length] = {"id":file.id,"name":file.name,"size":file.size,"state":"0","file":"","type":file.type,"msg":""},
				document.getElementById(up.customSettings.div).innerHTML = "等待中...";
			}
			catch(ex)
			{
			}
			log('      File:', file);}
		);
		up.start();
	};
	s.init.FilesRemoved = s.init.FilesRemoved || function(up, files){log('[FilesRemoved]');plupload.each(files, function(file) {log('      File:', file);});};

	s.init.FileUploaded = s.init.FileUploaded || function(up, file, info){
		log('[FileUploaded] File:', file, "Info:", info);
		try
		{
			eval("var d = " + info.response + ";");
			var o;
			up.customSettings.uploadSize += file.size;
			for(var i = 0;i < up.customSettings.uploadArray.length;i++)
			{
				o = up.customSettings.uploadArray[i];
				if(file.id == o.id)
				{
					if(d != undefined && d.msg != undefined && !d.err)
					{
						o.state = "1";
				 		o.msg = "上传成功";
				 		o.file = d.msg;// 后台保存后的文件名
					}
					else
					{
						o.state = "-1";
				 		o.msg = d.err || "上传失败";
					 	o.file = "";
					}
					break;
				}
			}
		}
		catch(ex)
		{
		}
	};
	s.init.ChunkUploaded = s.init.ChunkUploaded || function(up, file, info){log('[ChunkUploaded] File:', file, "Info:", info);};
	s.init.UploadComplete = s.init.UploadComplete || function(up, files){
		log('[UploadComplete]');
		try
		{
			var returnValue = {arr:[], msg:"", err:""};
			var o;
			for(var i = 0;i < up.customSettings.uploadArray.length;i++)
			{
				o = up.customSettings.uploadArray[i];
		 		switch(o.state)
				{
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
			up.customSettings.uploadArray = [];// 清空
		}
		catch(ex)
		{
		}
	};

	s.init.Destroy = s.init.Destroy || function(up){log('[Destroy]');};
	s.init.Error = s.init.Error || function(up, args){
		log('[Error] ',args);
		alert("ERROR:" + args.file.name + "," + args.message);
	};
	var mup = new plupload.Uploader(s);
	mup.customSettings = s.custom_settings || {};
	mup.customSettings.success = s.custom_settings.success || function(){};
	mup.customSettings.allSize = 0;
	mup.customSettings.uploadSize = 0;
	mup.customSettings.uploadArray = [];
	mup.init();
	return mup;
}
};

    function log() {
        var str = "";
 
        plupload.each(arguments, function(arg) {
            var row = "";
 
            if (typeof(arg) != "string") {
                plupload.each(arg, function(value, key) {
                    // Convert items in File objects to human readable form
                    if (arg instanceof plupload.File) {
                        // Convert status to human readable
                        switch (value) {
                            case plupload.QUEUED:
                                value = 'QUEUED';
                                break;
 
                            case plupload.UPLOADING:
                                value = 'UPLOADING';
                                break;
 
                            case plupload.FAILED:
                                value = 'FAILED';
                                break;
 
                            case plupload.DONE:
                                value = 'DONE';
                                break;
                        }
                    }
 
                    if (typeof(value) != "function") {
                        row += (row ? ', ' : '') + key + '=' + value;
                    }
                });
 
                str += row + " ";
            } else {
                str += arg + " ";
            }
        });
 
        var log = document.getElementById('console');
        log.innerHTML += str + "\n";
    }