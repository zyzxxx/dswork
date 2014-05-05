<c:set var="ctx" value="${pageContext.request.contextPath}" /><%
response.setHeader("Cache-Control","no-store");
response.setDateHeader("Expires", 0);
response.setHeader("Pragma","no-cache"); 
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="/web/themes/default/frame.css" />
<script type="text/javascript" src="/web/js/jquery/jquery.js"></script>
<script type="text/javascript" src="/web/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="/web/js/jskey/jskey_core.js"></script>
<script type="text/javascript" src="/web/js/dswork/dswork.js"></script>
<script type="text/javascript" src="/web/js/dswork/mask/mask.js"></script>
<script type="text/javascript" src="/web/js/dswork/form.js"></script>
<script type="text/javascript">
$dswork.showNavTitle = "\u4fee\u6539";<%--upd--%>
$(function(){$dswork.showNavigation($dswork.showNavTitle);});
</script>