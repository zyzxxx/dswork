<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="
java.util.HashMap,
java.util.List,
dswork.spring.BeanFactory,
testwork.model.Demo,
testwork.service.ManageDemoService
"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
ManageDemoService service = (ManageDemoService)BeanFactory.getBean("manageDemoService");
Demo demo = new Demo();
demo.setContent("Content");
demo.setTitle("Title");
demo.setFoundtime("2010-01-01");
service.save(demo);
List<Demo> csList = service.queryList(new HashMap<Object, Object>());
out.println("Total:"+csList.size());
%>
</body>
</html>