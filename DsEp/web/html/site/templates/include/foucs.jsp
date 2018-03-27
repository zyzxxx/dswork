<%@page language="java" pageEncoding="UTF-8"%>
<%
//图片新闻
request.setAttribute("picnews", cms.queryList(1, 4, true, false, true, 42));
%>
<div class="foucs">
	<ul class="img">
	<c:forEach items="${picnews}" var="d"  begin="0" end="4">
		<li href="${ctx}/${d.url}">
			<img alt="${d.title}" src="${d.img}"/><div></div><span>${d.title}</span>
		</li>
	</c:forEach>
	</ul>
	<ul class="menu">
	<c:forEach items="${picnews}" var="d" begin="0" end="4">
		<li>
			<img alt="${d.title}" src="${d.img}"/>
		</li>
	</c:forEach>
	</ul>
</div>
<script type="text/javascript">
;!function(){
	var oFocus = $('.foucs');
	var oList = oFocus.find('.img li');
	var oMenu = oFocus.find('.menu li');
	var index = 0, time =false;
	oMenu.eq(0).addClass('on');
	oFocus.hover(function(){
		if(time){clearInterval(time);}
	},function(){
		paly();
	});
	oMenu.click(function(){
		index = oMenu.index(this);
		play();return false;
	});
	oList.click(function(){
		window.open($(this).attr("href"));
		return false;
	});
	function play(){
		if(index>=oList.length) index = 0;
		oList.stop(true).eq(index).show().fadeIn(300).siblings().hide();
		oMenu.eq(index).addClass('on').siblings().removeClass('on');
	};
	function paly(){
		play();
		time = setInterval(function(){index ++;play();},3000)
	}
	paly();
}();
</script>
