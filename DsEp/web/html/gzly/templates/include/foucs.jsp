<%@page language="java" pageEncoding="UTF-8"%>
<%
//图片新闻
request.setAttribute("picnews", cms.queryList(1, 4, true, false, true, 42));
%>
<div class="foucs">
	<ul class="img">
	<c:forEach items="${picnews}" var="d">
		<li href="${ctx}/${d.url}">
			<img alt="${d.title}" src="${d.img}"/><div></div><span>${d.title}</span>
		</li>
	</c:forEach>
	<%--
	<li href="/xxgk/jgjs/24325.html">
			<img alt="图片" src="1.jpg"/><div></div><span>把纪律挺在前面，不越底线，远离职务犯罪</span>
		</li>
		<li href="/gzdt/lyxw/24260.html">
			<img alt="图片" src="2.jpg"/><div></div><span>广东省旅游局局长杨荣森与农业厅厅长郑伟仪一行座谈广东省旅游局局长杨荣森与农业厅厅长郑伟仪一行座谈</span>
		</li>
		<li href="/gzdt/lyxw/24260.html">
			<img alt="图片" src="3.jpg"/><div></div><span>广东省旅游局局长杨荣森与农业厅厅长郑伟仪一行座谈</span>
		</li>
		<li href="/gzdt/lyxw/24260.html">
			<img alt="图片" src="4.jpg"/><div></div><span>广东省旅游局局长杨荣森与农业厅厅长郑伟仪一行座谈</span>
		</li>
	 --%>
	</ul>
	<ul class="menu">
	<c:forEach items="${picnews}" var="d">
		<li>
			<img alt="${d.title}" src="${d.img}"/>
		</li>
	</c:forEach>
	<%--
	<li><img alt="图片" src="1.jpg"/></li>
		<li><img alt="图片" src="2.jpg"/></li>
		<li><img alt="图片" src="3.jpg"/></li>
		<li><img alt="图片" src="4.jpg"/></li>
	 --%>
		
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