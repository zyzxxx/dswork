<%@page language="java" pageEncoding="UTF-8"%><c:if test="${datapage.last < 11}">${datapageview}</c:if>
<c:if test="${datapage.last > 10}"><%request.setAttribute("viewpage", 3);%>
<a${datapage.page == 1?' class="selected"':''}<c:if test="${datapage.page == 1}"> onclick="return false;"</c:if> href="${ctx}/${datapage.firsturl}">1</a><c:if test="${datapage.page - viewpage > 2 && datapage.page - viewpage < datapage.last}"><a href="${ctx}/${datauri}_${datapage.page - viewpage - 1}.html">...</a></c:if
><c:forEach var="i" begin="1" end="${datapage.last}"><c:if test="${i > 1 && i < datapage.last && i >= datapage.page - 3 && i <= datapage.page + 3}"><a${datapage.page == i?' class="selected"':''} href="${ctx}/${datauri}_${i}.html">${i}</a></c:if></c:forEach
><c:if test="${datapage.page + viewpage > 2 && datapage.page + viewpage + 1 < datapage.last}"><a href="${ctx}/${datauri}_${datapage.page + viewpage + 1}.html">...</a></c:if
><c:if test="${datapage.last != 1}"><a${datapage.page == datapage.last?' class="selected"':''}<c:if test="${datapage.page == datapage.last}"> onclick="return false;"</c:if> href="${ctx}/${datapage.lasturl}">${datapage.last}</a></c:if>
</c:if><%--
<a${datapage.page == 1?' class="selected"':''}<c:if test="${datapage.page != 1}"> href="${ctx}/${datauri}.html"</c:if>>1</a><c:forEach var="i" begin="2" end="${datapage.total}"><a<c:if test="${datapage.page == i}"> class="selected"</c:if><c:if test="${datapage.page != i}"> href="${ctx}/${datauri}_${i}.html"</c:if>>${i}</a></c:forEach>

<a<c:if test="${datapage.page == 1}"> onclick="return false;"</c:if> href="${ctx}/${datapage.firsturl}">首页</a>
<a<c:if test="${datapage.page == 1}"> onclick="return false;"</c:if> href="${ctx}/${datapage.prevurl}">上页</a>
<a<c:if test="${datapage.page == datapage.last}"> onclick="return false;"</c:if> href="${ctx}/${datapage.nexturl}">下页</a>
<a<c:if test="${datapage.page == datapage.last}"> onclick="return false;"</c:if> href="${ctx}/${datapage.lasturl}">尾页</a>
--%>