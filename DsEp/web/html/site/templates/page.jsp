<%@page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%common.cms.CmsFactory cms = (common.cms.CmsFactory)request.getAttribute("cms");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>${site.name}</title>
<meta name="keywords" content="${category.metakeywords}" />
<meta name="description" content="${category.metadescription}" />
</head>
<body>
${title}<br />
${content}
</body>
</html>