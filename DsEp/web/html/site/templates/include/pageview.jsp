<%@page language="java" pageEncoding="UTF-8"%>
${datapageview}
<%--
<br /><br />
<c:set var="viewpage" value="3" scope="request" />
<a${datapage.page == 1?' class="selected"':''}<c:if test="${datapage.page != 1}"> href="${ctx}/${datapage.firsturl}"</c:if>>1</a><c:if test="${datapage.last > 1}"
><c:if test="${datapage.page - viewpage > 2}"><a href="${ctx}/${datauri}_${datapage.page - viewpage - 1}.html">...</a></c:if
><c:forEach var="i" begin="2" end="${datapage.last}"><c:if test="${i<datapage.last && i >= datapage.page-3 && i <= datapage.page+3}"
	><a<c:if test="${datapage.page == i}"> class="selected"</c:if><c:if test="${datapage.page != i}"> href="${ctx}/${datauri}_${i}.html"</c:if>>${i}</a></c:if
></c:forEach
><c:if test="${datapage.page + viewpage + 1 < datapage.last}"><a href="${ctx}/${datauri}_${datapage.page + viewpage + 1}.html">...</a></c:if
><a${datapage.page == datapage.last?' class="selected"':''}<c:if test="${datapage.page != datapage.last}"> href="${ctx}/${datapage.lasturl}"</c:if>>${datapage.last}</a>
</c:if>

<br /><br />
<a${datapage.page == 1?' class="selected"':''}<c:if test="${datapage.page != 1}"> href="${ctx}/${datauri}.html"</c:if>>1</a><c:forEach var="i" begin="2" end="${datapage.total}"><a<c:if test="${datapage.page == i}"> class="selected"</c:if><c:if test="${datapage.page != i}"> href="${ctx}/${datauri}_${i}.html"</c:if>>${i}</a></c:forEach>

<br /><br />
<a<c:if test="${datapage.page == 1}"> onclick="return false;"</c:if> href="${ctx}/${datapage.firsturl}">首页</a>
<a<c:if test="${datapage.page == 1}"> onclick="return false;"</c:if> href="${ctx}/${datapage.prevurl}">上页</a>
${datapage.page}/${datapage.last}
<a<c:if test="${datapage.page == datapage.last}"> onclick="return false;"</c:if> href="${ctx}/${datapage.nexturl}">下页</a>
<a<c:if test="${datapage.page == datapage.last}"> onclick="return false;"</c:if> href="${ctx}/${datapage.lasturl}">尾页</a>
--%>