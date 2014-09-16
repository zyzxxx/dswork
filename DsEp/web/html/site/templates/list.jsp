<%@page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%common.cms.CmsFactory cms = (common.cms.CmsFactory)request.getAttribute("cms");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title></title>
</head>
<body>
${category.name}
<c:forEach items="${datalist}" var="d">
<br /><a href="${d.url}">${d.title}, ${d.releasetime}</a>
</c:forEach>

<br /><br /><br />
${datapage}
</body>
</html>