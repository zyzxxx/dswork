<%@page language="java" pageEncoding="UTF-8"  import="java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
</head>
<body>
<%
Connection conn = null;
PreparedStatement stmt = null;
ResultSet rs = null;
try{
	DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
	out.println("<br />oracle.jdbc.OracleDriver...OK");
}
catch(Exception s){
	out.println(s.getMessage());
}
try{
	DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	out.println("<br />oracle.jdbc.driver.OracleDriver...OK");
}
catch(Exception s){
	out.println(s.getMessage());
}
try{
	String url="jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	conn = DriverManager.getConnection(url, "test", "test");
	stmt = conn.prepareStatement("select 'SB' from dual");
	rs = stmt.executeQuery();
	out.print("<br />starting...");
	while(rs.next()){
		out.print("<br />"+rs.getString(1));
	}
	out.print("<br />end...");
	rs.close();
	stmt.close();
	conn.close();
}
catch(Exception e){
	out.println(e.getMessage());
}
%>
</body>
</html>