<%@page language="java" pageEncoding="UTF-8"  import="java.sql.*,java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
</head>
<body>
<%
Set<String> subDrivers = new TreeSet<String>();
subDrivers.add("oracle.jdbc.driver.OracleDriver");
subDrivers.add("oracle.jdbc.OracleDriver");
subDrivers.add("com.mysql.jc.jdbc.Driver");// mysql j 6.x for jdk8
subDrivers.add("com.mysql.jdbc.Driver");// mysql j 5.x及之前
subDrivers.add("com.microsoft.jdbc.sqlserver.SQLServerDriver");// MS driver for Sql Server 2000
subDrivers.add("com.microsoft.sqlserver.jdbc.SQLServerDriver");// MS driver for Sql Server 2005
subDrivers.add("com.ibm.db2.jcc.DB2Driver");// 增加了DB2支持
subDrivers.add("org.sqlite.JDBC");// 增加了sqlite支持
subDrivers.add("org.postgresql.Driver");
subDrivers.add("com.sybase.jdbc2.jdbc.SybDriver");
subDrivers.add("net.sourceforge.jtds.jdbc.Driver");// sqlServer
subDrivers.add("weblogic.jdbc.sqlserver.SQLServerDriver");// sqlServer
subDrivers.add("com.informix.jdbc.IfxDriver");
subDrivers.add("org.apache.derby.jdbc.ClientDriver");
subDrivers.add("org.apache.derby.jdbc.EmbeddedDriver");
subDrivers.add("org.hsqldb.jdbcDriver");
subDrivers.add("org.h2.Driver");
subDrivers.add("dm.jdbc.driver.DmDriver");//达梦
subDrivers.add("com.gbase.jdbc.Driver");//gbase
String driverClass;
for(Iterator<String> i = subDrivers.iterator(); i.hasNext();)
{
	driverClass = (String) i.next();
	try
	{
		//DriverManager.registerDriver(new oracle.jdbc.OracleDriver());//显式注册
		Class.forName(driverClass);//隐式注册
		out.print("<br />找到驱动："+driverClass);
	}
	catch(Throwable c)
	{
		i.remove();
	}
}

	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	try
	{
		String url = "";
		//url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		url = "jdbc:sqlite:../webapps/DemoJava/WEB-INF/classes/db";
		//url = "jdbc:mysql://127.0.0.1:3306/db?characterEncoding=UTF-8";
		//url = "jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=demo";
		//url = "jdbc:sqlserver://localhost:1433; DatabaseName=demo";
		conn = DriverManager.getConnection(url, "demo", "demo");
		String dd = conn.getMetaData().getDriverName();
		String sql = " select 'sb' ";
		if(dd.startsWith("oracle"))
		{
			sql += " from dual ";
		}
		
		stmt = conn.prepareStatement(sql);
		rs = stmt.executeQuery();

		out.print("<br />开始执行...");
		out.print("<br />数据读取结果如下:");
		while(rs.next()){
			out.print("<br />"+rs.getString(1));
		}
		out.print("<br />结束...");
		rs.close();
		stmt.close();
		conn.close();
		
	}
	catch(Exception e)
	{
		out.println(e.getMessage());
	}
%>
</body>
</html>