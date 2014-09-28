<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<title></title>
	<style type="text/css">
.listTable {width:100%;background-color:#c2c2c2;border:none;}/*数据表格*/
.listTable tr {height:25px;padding:0px 2px 0px 2px;}
.listTable td {vertical-align:middle;text-align:center;background-color:#ffffff;color:#2b3e44;word-wrap:break-word;word-break:break-all;}
.listTable tr.list_title {height:30px;}
.listTable tr.list_title td{background-color:#dfe9fd;color:#24373d;}

td.form_title, td.form_input {height:25px;background-color:#dfe9fd;padding-left:3px;padding-right:3px;}
td.form_title {text-align:right;}
td.form_input {text-align:left;background-color:#e6eefe;}

		td.memory {line-height:28px;vertical-align:top;}
		td.memory div {line-height:28px;height:28px;text-align:left;overflow:hidden;}
		td.memory div span {line-height:28px;height:28px;text-align:left;overflow:hidden;font-weight:bold;}
	</style>
	<script type="text/javascript" src="/web/js/jquery/jquery.js"></script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">监控系统运行状态</td>
	</tr>
</table>
<div class="line"></div>
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td colspan="4">系统信息</td>
	</tr>
	<tr>
		<td class="form_title" style="width:20%;">操作系统(架构)：</td>
		<td class="form_input" style="width:30%;" id="osname"></td>
		<td class="form_title" style="width:20%;">内存总量：</td>
		<td class="form_input" style="width:30%;" id="osmemory"></td>
	</tr>
	<tr>
		<td class="form_title">CPU数量：</td>
		<td class="form_input" id="oscpu"></td>
		<td class="form_title">物理内存：</td>
		<td class="form_input" id="osphysicalmemory"></td>
	</tr>
</table>
<div class="line"></div>
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:50%;">系统内存运行状态监控</td>
		<td style="width:50%;">JAVA虚拟机内存状态监控</td>
	</tr>
	<tr>
		<td class="form_input memory">
			<div>
				已用物理内存：<span id="memoryBusy"></span>
			</div>
			<div>
				可用物理内存：<span id="memoryFree"></span>
			</div>
			<div style="width:99%;;background:url('free.jpg') repeat-x;">
				<div id="memoryBusyShow" style="float:left;width:5%;background:url('busy.jpg') repeat-x;text-align:right;">
					0%
				</div>
			</div>
			<div>
				可用内存（物理内存+虚拟内存）：<span id="memoryFreeSwapSpace"></span>
			</div>
		</td>
		<td class="form_input memory">
			<div>
				占用内存：<span id="runtotal"></span>
			</div>
			<div>
				空闲内存：<span id="runfree"></span>
			</div>
			<div>
				试图使用的最大内存：<span id="runmax"></span>
			</div>
			<div>
				可用CPU数：<span id="runcpu"></span>
			</div>
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
	function getInfo()
	{
		$.ajax({
			url:"os_info.jsp?" + (new Date().getTime()),
			type:"post",
			data:{},
			success:
				function(jsobj)
				{
					try
					{
						eval("jsobj=" + jsobj + ";");
						$("#osname").html(jsobj.osname + " (" + jsobj.osarch + ")");
						//$("#osversion").html(jsobj.osversion);
						$("#oscpu").html(jsobj.oscpu);
						$("#osmemory").html(jsobj.osmemory + " MB");
						var memoryTotal = jsobj.osphysicalmemory;
						$("#osphysicalmemory").html(memoryTotal + " MB");
						var percent = parseInt((1-(jsobj.free)/memoryTotal)*100);
						$("#memoryBusyShow").css("width", percent + "%");
						$("#memoryBusyShow").html(percent + "%");
						$("#memoryBusy").html(percent + "%&nbsp;&nbsp;" + (memoryTotal - jsobj.free) + " MB");
						$("#memoryFree").html((100-percent) + "%&nbsp;&nbsp;" + jsobj.free + " MB");
						$("#memoryFreeSwapSpace").html(jsobj.swap + " MB");
						$("#runtotal").html(jsobj.runtotal + " MB");
						$("#runfree").html(jsobj.runfree + " MB");
						$("#runmax").html(jsobj.runmax + " MB");
						$("#runcpu").html(jsobj.runcpu);
					}
					catch(e)
					{
						alert(e.message);
					}
					setTimeout(getInfo, 2000);
				},
			error:
				function()
				{
					alert("加载信息出错，请重试");
					setTimeout(getInfo, 2000);
				}
		});
	}
	setTimeout(getInfo, 1);
</script>
</html>
