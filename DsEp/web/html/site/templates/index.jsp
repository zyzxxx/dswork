<%@page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%common.cms.CmsFactory cms = (common.cms.CmsFactory)request.getAttribute("cms");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0,minimal-ui"/>
<title>${site.name}</title>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/web.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/index.css"/>
</head>
<body>
<%@include file="include/header.jsp"%>
<div class="content w990 clearfix">
<div class="citys">
	<a href="#" target="_blank">越秀区</a>
	<a href="#" target="_blank">天河区</a>
	<a href="#" target="_blank">荔湾区</a>
	<a href="#" target="_blank">白云区</a>
	<a href="#" target="_blank">海珠区</a>
	<a href="#" target="_blank">番禺区</a>
	<a href="#" target="_blank">黄埔区</a>
	<a href="#" target="_blank">萝岗区</a>
	<a href="#" target="_blank">花都区</a>
	<a href="#" target="_blank">南沙区</a>
	<a href="#" target="_blank">增城市</a>
	<a href="#" target="_blank">从化市</a>
</div>
<div class="main left">
  <div class="hotNews">
	<div class="mid clear">
	<dl class="clearfix">
	  <dt>新闻头条</dt>
	  <dd style="overflow: hidden;padding: 6px 10px 0 0;width: 625px;">
		<div id="hot_news_box" style="height: 68px; overflow: hidden; width: 630px;">
			<table cellspacing="0" cellpadding="0" style="border-collapse:collapse;"><tr><td>
				<table cellspacing="0" cellpadding="0" style="border-collapse:collapse;"><tr><td>
					<div style="height:68px;">
						<h2 style="height: 25px;overflow: hidden;">
						<a style="font-weight: bold;" target="_blank" title="" href="#">广东省旅游局面向全省集中受理旅游市场违法违规案件举报</a></h2>
						<p>&#12288;&#12288;针对“不合理低价”导致的旅游市场秩序问题，广东省旅游局决定，面向全省集中受理旅游市场违法违规案件举报。对接到的举报案件将及时梳理分析、调查核实、依法处理，做到件件有着落，坚决...
						<a class="cRed" style="color:#FF6600;" target="_blank" title="" href="#">
						详细&gt;&gt;</a>
						</p>
					</div>
				</td></tr></table>
			</td></tr></table>
		</div>
 	  </dd>
	</dl>
	<div class="foucs left">
		<ul class="img">
			<li style="display: list-item;">
				<a href="#" title="" target="_blank"><img width="402" height="250" src="${ctx}/themes/images/1431678647109926085.jpg" alt=""></a>
				<span style="position: absolute;left: 0;bottom: 0;width: 402px;height: 32px;background: black;opacity: 0.5;-moz-opacity: 0.5;progid:DXImageTransform.Microsoft.Alpha(opacity=50);display: block;"></span>
				<a href="#" title="" target="_blank" class="tx">把纪律挺在前面，不越底线，远离职务犯罪</a>
			</li>
			<li style="display: none;">
				<a href="#" title="" target="_blank"><img width="402" height="250" src="${ctx}/themes/images/1431678647109926085.jpg" alt=""></a>
				<span style="position: absolute;left: 0;bottom: 0;width: 402px;height: 32px;background: black;opacity: 0.5;-moz-opacity: 0.5;progid:DXImageTransform.Microsoft.Alpha(opacity=50);display: block;"></span>
				<a href="#" title="" target="_blank" class="tx">把纪律挺在前面，不越底线，远离职务犯罪</a>
			</li>
			<li style="display: none;">
				<a href="#" title="" target="_blank"><img width="402" height="250" src="${ctx}/themes/images/1431678647109926085.jpg" alt=""></a>
				<span style="position: absolute;left: 0;bottom: 0;width: 402px;height: 32px;background: black;opacity: 0.5;-moz-opacity: 0.5;progid:DXImageTransform.Microsoft.Alpha(opacity=50);display: block;"></span>
				<a href="#" title="" target="_blank" class="tx">把纪律挺在前面，不越底线，远离职务犯罪</a>
			</li>
			<li style="display: none;">
				<a href="#" title="" target="_blank"><img width="402" height="250" src="${ctx}/themes/images/1431678647109926085.jpg" alt=""></a>
				<span style="position: absolute;left: 0;bottom: 0;width: 402px;height: 32px;background: black;opacity: 0.5;-moz-opacity: 0.5;progid:DXImageTransform.Microsoft.Alpha(opacity=50);display: block;"></span>
					<a href="#" title="" target="_blank" class="tx">把纪律挺在前面，不越底线，远离职务犯罪</a>
				</li>
			</ul>
			<ul class="menu clear">
				<li class="on">
					<a href=""><img width="98" height="56" src="${ctx}/themes/images/1431678647109926085.jpg" alt=""></a>
				</li>
				<li class="">
					<a href=""><img width="98" height="56" src="${ctx}/themes/images/1431678647109926085.jpg" alt=""></a>
				</li>
				<li class="">
					<a href=""><img width="98" height="56" src="${ctx}/themes/images/1431678647109926085.jpg" alt=""></a>
				</li>
				<li class="">
					<a href=""><img width="98" height="56" src="${ctx}/themes/images/1431678647109926085.jpg" alt=""></a>
				</li>
			</ul>
		</div>
		<div class="list left">
			<h3 class="cDOrg">	
				<a href="" title="" target="_blank" class="cDOrg">欧洲摄影师走进河源，赞叹：“万绿湖像天</a> 
			</h3>
			<ul class="listStyle">
				<li><a href="#" title="" target="_blank">欧洲知名摄影师到韶关市著名旅游景点拍摄</a></li>
				<li><a href="#" title="" target="_blank">欧洲摄影师看广东”走进潮州</a></li>
			</ul>
			<h3 class="cOrg"><a href="#" title="" target="_blank" class="cDOrg">汕尾市城区举办妈祖文化旅游节系列活动</a></h3>
			<ul class="listStyle">
				<li><a href="#" title="" target="_blank">中山文明旅游千人健步走活动在树木园举行</a></li>
				<li><a href="#" title="" target="_blank">中山文明旅游千人健步走活动在树木园举行</a></li>
				<li><a href="#" title="" target="_blank">中山文明旅游千人健步走活动在树木园举行</a></li>
				<li><a href="#" title="" target="_blank">中山文明旅游千人健步走活动在树木园举行</a></li>
				<li><a href="#" title="" target="_blank">中山文明旅游千人健步走活动在树木园举行</a></li>
				<li><a href="#" title="" target="_blank">中山文明旅游千人健步走活动在树木园举行</a></li>
				<li><a href="#" title="" target="_blank">中山文明旅游千人健步走活动在树木园举行</a></li>
				<li><a href="#" title="" target="_blank">中山文明旅游千人健步走活动在树木园举行</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="blank10"></div>
<div class="bBox shixiang">
	<div class="tit">
		<a href="" title="" target="_blank" class="more">更多&gt;&gt;</a>
		<h3>网上办事大厅</h3>
	</div>
	<div class="con clearfix">
		<div class="guidmain">
			<div class="service">
				<table cellspacing="0" cellpadding="0" border="0" class="servicebg">
					<tr>
						<td colspan="2" class="tit"><img height="25" src="${ctx}/themes/images/tt1.gif"></td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i16.gif"> </td>
						<td width="100"><a href="">公众互动帮助</a></td>
						</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i8.gif"></td>
						<td width="100"><a href="">投诉指南</a></td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i3.gif"></td>
						<td width="100"><a href="#">局长信箱</a></td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i1.gif"></td>
						<td width="100"><a href="#">旅游单位查询</a></td>
					</tr>
					<tr>
						<td colspan="2" class="more"><a target="_blank" href="#"><span class="textlink">更多&gt;&gt;</span></a></td>
					</tr>
				</table>
			</div>
			<div class="service">
				<table cellspacing="0" cellpadding="0" border="0" class="servicebg">
					<tr>
						<td colspan="2" class="tit"><img height="25" src="${ctx}/themes/images/tt2.gif"></td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i13.gif"></td>
						<td width="100"><a href="#">境内、入境业务旅行社审批</a></td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i14.gif"></td>
						<td width="100"><a href="#">旅游饭店星级评定</a></td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i12.gif"></td>
						<td width="100"><a href="#">港澳投资旅行社审批</a></td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i5.gif"></td>
						<td width="100"><a href="#">旅行社变更及注销事项备案办事指南</a></td>
					</tr>
					<tr>
						<td colspan="2" class="more"><a target="_blank" href="#"><span class="textlink">更多&gt;&gt;</span></a></td>
					</tr>
				</table>
			</div>
			<div class="service">
				<table cellspacing="0" cellpadding="0" border="0" class="servicebg">
					<tr>
						<td colspan="2" class="tit"><img height="25" src="${ctx}/themes/images/tt4.gif"></td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i15.gif"></td>
						<td width="100"><a href="#">办理导游证IC卡</a></td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i6.gif"></td>
						<td width="100"><a href="#">导游人员资格考试</a></td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i4.gif"></td>
						<td width="100"><a href="#">导游人员培训系统</a></td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i7.gif"></td>
						<td width="100"><a href="#">办证指南</a></td>
					</tr>
					<tr>
						<td colspan="2" class="more"><a target="_blank" href="#"><span class="textlink">更多&gt;&gt;</span></a></td>
					</tr>
				</table>
			</div>
		   <div class="service">
				<table cellspacing="0" cellpadding="0" border="0" class="servicebg">
					<tr>
						<td colspan="2" class="tit"><img height="25" src="${ctx}/themes/images/tt3.gif"></td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i17.gif"></td>
						<td width="100"><a href="">工作动态</a></td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i2.gif"></td>
						<td width="100"><a href="">相关法规标准</a></td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i11.gif"></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td width="40" height="40" class="ico"><img width="33" height="34" src="${ctx}/themes/images/i11.gif"></td>
						<td>&nbsp;</td></tr><tr><td colspan="2" class="more"><a target="_blank" href=""><span class="textlink">更多&gt;&gt;</span></a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
<div class="blank10"></div>
<div class="h_adv clearfix"> 
	<a target="_blank" class="left" href="#"><img width="360" height="79" alt="" src="${ctx}/themes/images/ad_360x79_1.jpg"></a>
	<div style="display: block; position: relative; overflow: hidden; width: 360px;height: 79px;float: right;">
		<table style="left: 0px; position: absolute;" id="spe_ad">
			<tr>
				<td><a target="_blank" class="right" href="http://www.gd.gov.cn/gzhd/hdmyzj/qszfxxgk/index.htm"><img width="360" height="79" alt="" src="${ctx}/themes/images/ad_360x79_2.jpg"></a></td>
				<td><a target="_blank" class="right" href="http://www.gdta.gov.cn/sdlj/index.html"><img width="360" height="79" alt="" src="${ctx}/themes/images/ad2_lysczxzz.jpg"></a></td>
			</tr>
		</table>
	</div>
</div>
<div class="blank10"></div>
<div class="w360 left">
	<div class="sBox infoBox">
		<div class="tit">
			<a href="#" title="" target="_blank" class="more">更多&gt;&gt;</a>
			<h3>信息公开</h3>
		</div>
		<ul class="clearfix">
			<li style="height: 32px;line-height: 32px;overflow: hidden;font-size: 14px;"><a href="#" title="机构组织" target="_blank">机构组织</a></li>
			<li style="height: 32px;line-height: 32px;overflow: hidden;font-size: 14px;"><a href="#" title="部门文件" target="_blank">部门文件</a></li>
			<li style="height: 32px;line-height: 32px;overflow: hidden;font-size: 14px;"><a href="#" title="行政执法" target="_blank">行政执法</a></li>
			<li style="height: 32px;line-height: 32px;overflow: hidden;font-size: 14px;"><a href="#" title="机构组织" target="_blank">办事指南</a></li>
			<li style="height: 32px;line-height: 32px;overflow: hidden;font-size: 14px;"><a href="#" title="工作动态" target="_blank">工作动态</a></li>
			<li style="height: 32px;line-height: 32px;overflow: hidden;font-size: 14px;"><a href="#" title="财政预决算" target="_blank">财政预决算</a></li>
			<li style="height: 32px;line-height: 32px;overflow: hidden;font-size: 14px;"><a href="#" title="政府信息公开工作年度报告" target="_blank">政府信息公开工作年度报告</a></li>
			<li style="height: 32px;line-height: 32px;overflow: hidden;font-size: 14px;"><a href="#" title="其他" target="_blank">其他</a></li>
		</ul>
	</div>
</div>
<div class="w360 right">
	<div class="sBox tongzhi">
		<div class="tit">
			<a href="#" title="" target="_blank" class="more">更多&gt;&gt;</a>
			<h3>通知公告</h3>
		</div>
		<div class="con">
			<ul class="listStyle">
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="blank10"></div>
<div class="w360 left">
	<div class="sBox fagui">
		<div class="tit">
			<a href="#" title="" target="_blank" class="more">更多&gt;&gt;</a>
			<h3>法规文件</h3>
		</div>
		<div class="con">
			<ul class="listStyle">
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="w360 right">
	<div class="sBox ziliao">
		<div class="tit">
			<a href="#" title="" target="_blank" class="more">更多&gt;&gt;</a>
			<h3>资料下载</h3>
		</div>
		<div class="con">
			<ul class="listStyle">
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="blank10"></div>
<div class="w360 left">
	<div class="sBox hangye">
		<div class="tit">
			<a href="#" title="" target="_blank" class="more">更多&gt;&gt;</a>
			<h3>行业管理</h3>
		</div>
		<div class="con">
			<ul class="listStyle">
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="w360 right">
	<div class="sBox zhuangti">
		<div class="tit">
			<a href="#" title="" target="_blank" class="more">更多&gt;&gt;</a>
			<h3>特色专题</h3>
		</div>
		<div class="con">
			<ul class="listStyle">
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
				<li><a href="#" title="" target="_blank">全省旅游住宿设施接待过夜游客情况（2014年12月</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="blank10"></div>
<div class="bBox tousu">
	<div class="tit">
		<a href="" title="" target="_blank" class="more">更多&gt;&gt;</a>
		<h3>旅游投诉</h3>
	</div>
	<div class="con clearfix">
		<div class="sx-l left">
			<h4 class="cDOrg">最新投诉</h4>
			<ul class="listStyle">
				<li>受理号2015****5387&nbsp;&nbsp;未受理&nbsp;<span style="float: right;">2015-05-20 09:07</span></li>
			<li>受理号2015****5387&nbsp;&nbsp;未受理&nbsp;<span style="float: right;">2015-05-20 09:07</span></li>
			<li>受理号2015****5387&nbsp;&nbsp;未受理&nbsp;<span style="float: right;">2015-05-20 09:07</span></li>
			<li>受理号2015****5387&nbsp;&nbsp;未受理&nbsp;<span style="float: right;">2015-05-20 09:07</span></li>
			<li>受理号2015****5387&nbsp;&nbsp;未受理&nbsp;<span style="float: right;">2015-05-20 09:07</span></li>
		</ul>
	</div>
	<div class="sx-r right">
		<h4 class="cDOrg">旅游投诉查询</h4>
		<div class="complainTable">
			<form target="_blank" method="post" action="/complain-lyts/" id="complain_searhc_form">
				<table cellspacing="0" cellpadding="0" border="0" style="float: left;" class="complainTable">
					<tbody>
						<tr>
							<td align="right" width="80">受理号:</td>
							<td><input title="请输入12位受理号" placeholder="请输入12位受理号" class="txt number" maxlength="12" name="acceptNo" id="acceptNo"></td>
						</tr>
						<tr>
							<td align="right" width="80">投诉人姓名:</td>
							<td><input title="请输入投诉人姓名" placeholder="请输入投诉人姓名" class="txt" maxlength="15" name="name" id="senderName"></td>
						</tr>
						<tr>
							<td align="right" width="80">联系电话:</td>
							<td><input title="请输入联系电话" placeholder="请输入联系电话" class="txt" maxlength="18" name="phone" id="senderPhone"></td>
						</tr>
						</tbody>
				  </table>
				  <table cellspacing="0" cellpadding="0" border="0" style="float: left;height: 80px;" class="complainTable">
					 <tbody>
						<tr>
							<td style="padding-left: 5px;">
								<a title="查询" onClick="" style="width: 44px;display: inline-block;height: 33px;line-height: 33px;color: #fff;text-align: center;background: url('images/aide_btn.gif') no-repeat;" href="#"><span>查询</span></a>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="b_adv">
	<div style="border:0px solid #eee;text-align:center;width:735; height:80">
	<a target="_blank" href="http://www.visitgd.com/ch/index.aspx"><img width="735" height="80" style="vertical-align:middle" src="${ctx}/themes/images/index_06.jpg"></a>
	<a target="_blank" href="http://www.citie.gov.cn/default.html"><img width="735" height="80" style="vertical-align:middle" src="${ctx}/themes/images/index_14.jpg"></a>
	</div>
</div>
</div>
<div class="side right">
  <div class="s_tips"> <img alt="旅游提示" src="${ctx}/themes/images/sTip_top.gif">
	<ul class="listStyle2">
		<li><i>&gt;</i> <a href="" title="" target="_blank">谨防低价旅游陷阱</a></li>
		<li><i>&gt;</i> <a href="" title="" target="_blank">防御暴雨安全提示</a></li>
		<li><i>&gt;</i> <a href="" title="" target="_blank">五一旅游消费提示</a></li>
		<li><i>&gt;</i> <a href="" title="" target="_blank">旅游安全预警提示</a></li>
	</ul>
  </div>
  <div class="s_tool">
	<ul>
		<li><a target="_blank" href="#">局长信箱</a></li>
		<li><a target="_blank" href="http://my.gz.gov.cn/">市民网页</a></li>
		<li><a target="_blank" href="http://www.visitgz.com/channels/173.html">问卷调查</a></li>
		<li><a target="_blank" href="#">互动帮助</a></li>
	</ul>
  </div>
  <div class="s_adList">
	<a target="_blank" href="http://wsbs.gz.gov.cn/gz/index.jsp"><img width="240" border="0" alt="广东省网上办事大厅" src="${ctx}/themes/images/ad_bsdt.jpg"></a>
	<a target="_blank" href="http://121.8.226.112:443/gztbs/dms/loginEp.html"><img width="193" border="0" height="36" src="${ctx}/themes/images/s_1.jpg"></a>
	<a target="_blank" href="http://121.8.226.112:443/gzlycj/enterprise/login.action"><img width="193" border="0" height="36" src="${ctx}/themes/images/s_2.jpg"></a>
	<a target="_blank" href="http://www.gzly.gov.cn/SYS/ExPortal.aspx?Page=NullMenu&Menu=lanmu_zwfw&SubMenu=menu_lycyry&SubMenuType=3&SubSubMenu=lanmu_dyrypxxt"><img width="193" border="0" height="36" src="${ctx}/themes/images/s_3.jpg"></a>
	<a target="_blank" href="http://www.gzlytj.com/Note/note_list.aspx"><img width="193" border="0" height="36" src="${ctx}/themes/images/s_4.jpg"></a>
	<a target="_blank" href="http://daoyou-chaxun.cnta.gov.cn/single_info/selectlogin_1.asp"><img width="193" border="0" height="36" src="${ctx}/themes/images/s_5.jpg"></a>
	<a target="_blank" href="http://www.visitgz.com/contents/48/12800.html"><img width="193" border="0" height="36" src="${ctx}/themes/images/s_6.jpg"></a>
	<a target="_blank" href="http://112.124.51.37/login.jsp"><img width="193" border="0" height="36" src="${ctx}/themes/images/s_7.jpg"></a>
	<a target="_blank" href="http://www.visitgz.com/channels/54.html"><img width="193" border="0" height="36" src="${ctx}/themes/images/s_8.jpg"></a>
  </div>
  <div class="s_box s_vedio" style="display:none;">
	<div class="tit">
		<a href="#" title="宣传视频" target="_blank" class="more">更多</a>
		<h3>宣传视频</h3>
	</div>
	<div class="con">
		<div id="xcsp_first"><img src="${ctx}/themes/images/11854.png" width="222" height="182"></div>
		<ul>
			<li class="hot"><span class="price"></span><i>1</i><a href="#" target="_blank">广东旅游宣传片 </a></li>
			<li><span style="line-height: 18px;" class="price"></span><i>2 </i><a href="#" target="_blank">广东海上休闲旅游 </a> </li>
		</ul>
	</div>
  </div>
  <div class="s_box s_zhushou">
	<div class="tit">
		<h3>出行助手</h3>
	</div>
	<div class="con">
		<ul class="clearfix">
		<li class="L1"><a href="http://www.gdta.gov.cn/gzdt/lycs/" title="旅游常识" target="_blank">旅游常识</a></li>
		<li class="L2"><a href="http://www.gdta.gov.cn/xxgk/fggw/" title="法规公文" target="_blank">旅游法规</a></li>
		<li class="L3"><a href="#">电子地图</a></li>
		<li class="L4"><a target="_blank" href="http://www.weather.com.cn/">天气预报</a></li>
		<li class="L5"><a href="#">旅行社</a></li>
		<li class="L6"><a href="#">酒店宾馆</a></li>
		<li class="L7"><a target="_blank" href="http://www.gdcx.gov.cn/web/gov/share/BigMap.aspx?lat=22.836945920943854&lng=113.455810546875&z=7">交通信息</a></li>
		<li class="L8"><a href="http://www.gdta.gov.cn/cyhm/" title="常用号码" target="_blank">常用号码</a></li>
		</ul>
	</div>
  </div>
  <div class="s_box s_vote" style="display:none;">
	<div class="tit">
		<h3>在线调查</h3>
	</div>
	<div class="con">
		<form action="" id="sur_form">
			<input type="hidden" value="5000" name="">
			<ul>
				<h4>您选择旅游景点考虑的因素有哪些？</h4>
				<li><input type="radio" class="q_item" qid="q0" value="5000" name="">&nbsp;时间和精力</li>
				<li><input type="radio" class="q_item" qid="q0" value="5001" name="">&nbsp;旅游支出费用</li>
				<li><input type="radio" class="q_item" qid="q0" value="5002" name="">&nbsp;兴趣爱好</li>
				<li><input type="radio" class="q_item" qid="q0" value="5003" name="">&nbsp;朋友建议</li>
				<li><input type="radio" class="q_item" qid="q0" value="5004" name="">&nbsp;旅行社的推荐</li>
			</ul>
			<div class="action"><a class="B1" id="btn_vote" name="" href="#">投票</a><a class="B2" target="_blank" href="#">查看结果</a></div>
		</form>
	</div>
  </div>
  <div class="s_search" style="display:none;">
	<div class="tit">全站搜索</div>
	<div class="con">
		<p class="tag"><a class="on" href="#">景区</a><a href="#">酒店</a><a href="#">旅行社</a></p>
		<p class="action"><input class="text"><a target="_blank" class="btn" href="#">搜索</a></p>
	</div>
  </div>
</div>
<div class="blank10"></div>
<div class="links">
  <div class="hd">
	<div class="right">
		<select onChange="">
			<option value="">各省市旅游局网站</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
		</select>
		<select onChange="">
			<option value="">市有关单位网站</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
		</select>
		<select onChange="">
			<option value="">市旅游企业网站</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
		</select>
		<select onChange="">
			<option value="">行业协会</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
			<option value="#">劳动和社会保障部</option>
		</select>
	</div>
	<h2>友情链接</h2>
  </div>
  <div class="bd">
	<ul>
		<li><a href="#" target="_blank">国民旅游休闲网</a></li>
		<li><a href="#" target="_blank">第七届旅游博览会</a></li>
		<li><a href="#" target="_blank">国民旅游休闲网</a></li>
		<li><a href="#" target="_blank">第七届旅游博览会</a></li>
		<li><a href="#" target="_blank">国民旅游休闲网</a></li>
		<li><a href="#" target="_blank">第七届旅游博览会</a></li>
		<li><a href="#" target="_blank">国民旅游休闲网</a></li>
		<li><a href="#" target="_blank">第七届旅游博览会</a></li>
	</ul>
  </div>
</div>
</div>
	
<%@include file="include/footer.jsp"%>
</body>
</html>