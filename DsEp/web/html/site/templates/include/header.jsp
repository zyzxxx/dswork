<%@page language="java" pageEncoding="UTF-8"%>
<%request.setAttribute("header", cms.queryCategory(""));%>
<div class="header">&nbsp;</div>
<div class="nav">
    <ul class="left">
        <li><a href="${ctx}/index.html">首页</a></li>
        <c:forEach items="${header}" var="d" start="1" end="4">
		<li><a href="${ctx}${d.url}"</c:if>>${d.name}</a></li>
		</c:forEach>
        <li><a target="_bank" href="http://wsbs.gz.gov.cn/gz/index.jsp">办事大厅</a></li>
        <li><a target="_bank" href="http://www.weibo.com/">新浪微博</a></li>
        <li class="last"><a target="_blank" href="https://www.baidu.com/">百度搜索</a></li>
    </ul>
</div>
