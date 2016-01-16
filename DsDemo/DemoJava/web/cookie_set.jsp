<%@page contentType="text/html; charset=UTF-8" import="javax.servlet.http.Cookie"%><%
Cookie cookie = new Cookie("ooo", "oooooo");
cookie.setMaxAge(-1);
cookie.setPath("/");
//cookie.setDomain("sso");
cookie.setSecure(false);
response.addCookie(cookie);

cookie = new Cookie("ooo2", "oooooo2");
cookie.setMaxAge(-1);
cookie.setPath("/");
//cookie.setDomain("127.0.0.1");
cookie.setSecure(false);
response.addCookie(cookie);

cookie = new Cookie("ooo3", "oooooo3");
cookie.setMaxAge(-1);
cookie.setPath("/");
//cookie.setDomain("127.0.0.1");
cookie.setSecure(false);
response.addCookie(cookie);
%>2