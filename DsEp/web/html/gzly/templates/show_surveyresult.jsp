<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="common.cms.CmsFactory"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%CmsFactory cms = (CmsFactory)request.getAttribute("cms");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0,minimal-ui"/>
<title>${category.name} - ${site.name}</title>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/web.css"/>
<script src="/zw/zwclient/js/jquery/amcharts.js" type="text/javascript"></script>
<script src="/zw/zwclient/js/jquery/serial.js" type="text/javascript"></script>
<meta name="keywords" content="${category.metakeywords}"/>
<meta name="description" content="${category.metadescription}"/>
</head>
<body>
<%@include file="include/header.jsp"%>
<div class="w990 clear">
  <div class="gk w240 left">
	<%@include file="include/tree.jsp"%>
  </div>
  <div class="w735 right">
	<div class="listpage hei1 view">
	  <div class="logo">&nbsp;&nbsp;当前位置：${category.name}</div>
	  <div class="content">
		<div id="chartdiv" style="margin-top:10%;width: 600px; height: 400px;"></div>
	  </div>
	</div>
  </div>
</div>
<%@include file="include/footer.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {
		var sHref = window.location.href;
		var retval = "";
		var args = sHref.split("?");
		if (args[0] == sHref) /*参数为空*/
		{
			return retval; /*CuPlayer.com提示：无需做任何处理*/
		}
		var str = args[1];
		args = str.split("&");
		for (var i = 0; i < args.length; i++) {
			str = args[i];
			var arg = str.split("=");
			if (arg.length <= 1)
				continue;
			if (arg[0] == "keyIndex")
				retval = arg[1];
		}
		var columnData = new Array();
		AmCharts.ready(function() {
			//alert();
			$.getJSON('/zw/zwclient/survey/surveyResultJson.jsp?keyIndex=' + retval, function(data) {
			//alert(111);
			var option = data.option;
			var result = data.result;
			for(var j=1; j<16; j++){
				if (option["option"+j] != "") {
					var points = 0;
					if (result["option"+j] == "") {
						points = 0;
					} else {
						points = result["option"+j];
					}
					var chartData = {
						"name" : option["option"+j],
						"points" : points
					}
					columnData.push(chartData);
				}
			}
			var chart = new AmCharts.AmSerialChart();
			chart.addTitle(option.title + "\n"+option.createtime, 16);
			chart.dataProvider = columnData;
			chart.categoryField = "name";
			chart.startDuration = 1;

			var categoryAxis = chart.categoryAxis;
			categoryAxis.labelRotation = 90;
			categoryAxis.gridPosition = "start";

			var graph = new AmCharts.AmGraph();
			graph.valueField = "points";
			graph.balloonText = "[[category]]: <b>[[value]]</b>";
			graph.type = "column";
			graph.lineAlpha = 0;
			graph.fillAlphas = 0.8;
			chart.addGraph(graph);

			var chartCursor = new AmCharts.ChartCursor();
			chartCursor.cursorAlpha = 0;
			chartCursor.zoomable = false;
			chartCursor.categoryBalloonEnabled = false;
			chart.addChartCursor(chartCursor);

			chart.creditsPosition = "top-right";

			chart.write("chartdiv");
		});
	});
});
</script>
</body>
</html>