<%@page language="java" pageEncoding="UTF-8" import="
java.net.URLEncoder,
dswork.web.MyRequest
"%><%
MyRequest req = new MyRequest(request);
String serviceURL = req.getString("service", "about:blank");
serviceURL = URLEncoder.encode(serviceURL, "UTF-8");
serviceURL = "http://localhost:8080/sso/ssologinAction.jsp?service=" + serviceURL;
serviceURL = URLEncoder.encode(serviceURL, "UTF-8");
String url = "http://localhost:8080/websso/login.jsp?serviceURL=" + serviceURL;
request.setAttribute("url", url);
%><html>
<body>
<script type="text/javascript">
location.href = '${url}';
</script>
</body>
</html>