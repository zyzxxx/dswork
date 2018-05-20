package dswork.builder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeMySql extends Type
{
	private static final String SQL_TABLE_COMMENT = "select TABLE_COMMENT from information_schema.TABLES where TABLE_SCHEMA='%s' and TABLE_NAME='%s'";
	private static final String SQL_TABLE_COLUMN = "select COLUMN_NAME,COLUMN_DEFAULT,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_COMMENT,COLUMN_KEY,EXTRA from information_schema.COLUMNS where TABLE_SCHEMA='%s' and TABLE_NAME='%s'";
//	private static final String SQL_ALL_TABLES = "select TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA='%s'";

	private Connection conn;
	private String dbName;

	@Override
	public void initConnect(String url) throws SQLException
	{
		conn = DriverManager.getConnection(url);
		dbName = conn.getCatalog();
	}

	private void querySetTableComment(Table table)
	{
		String comment = "";
		String sql = String.format(SQL_TABLE_COMMENT, dbName, table.getName());
		try(PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();)
		{
			rs.next();
			comment = rs.getString("TABLE_COMMENT");
		}
		catch(Exception e)
		{
		}
		table.setComment(comment);
	}

	private void querySetTableColumn(Table table)
	{
		String sql = String.format(SQL_TABLE_COLUMN, dbName, table.getName());
		try(PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();)
		{
			while(rs.next())
			{
				Table.Column col = table.addColumn();
				col.setName(rs.getString("COLUMN_NAME"));
				col.setDefaults(rs.getString("COLUMN_DEFAULT"));
				col.setType(rs.getString("DATA_TYPE"));
				col.setLength(rs.getInt("CHARACTER_MAXIMUM_LENGTH"));
				col.setComment(rs.getString("COLUMN_COMMENT"));
				col.setKey("PRI".equals(rs.getString("COLUMN_KEY")));
				col.setPrecision(rs.getInt("NUMERIC_PRECISION"));
				col.setDigit(rs.getInt("NUMERIC_SCALE"));
				col.setAuto("auto_increment".equals(rs.getString("EXTRA")));
				switch(col.getType())
				{
					case "int":
					case "tinyint":
					case "mediumint":
						col.setType("int"); break;
					case "bigint":
						if("id".equalsIgnoreCase(col.getName()))
						{
							col.setType("Long");
						}
						else
						{
							col.setType("long");
						}
						break;
					case "float":
					case "double":
						col.setType("float"); break;
					case "decimal":
					{
						if(col.getDigit() == 0)
						{
							if(col.getPrecision() < 11)
							{
								col.setType("int"); break;
							}
							col.setType("long"); break;
						}
						else
						{
							col.setType("float"); break;
						}
					}
					case "char":
					case "datetime":
					case "json":
					case "longtext":
					case "mediumtext":
					case "text":
					case "timestamp":
					case "time":
					case "tinytext":
					case "varchar":
					case "xml":
						col.setType("String"); break;
					default:
						break;
				}
			}
		}
		catch(Exception e)
		{
		}
	}

	@Override
	public Table queryTable(String tableName)
	{
		Table table = new Table();
		table.setName(tableName);
		querySetTableComment(table);
		querySetTableColumn(table);
		return table;
	}

	@Override
	public void close()
	{
		try
		{
			if(conn != null)
			{
				conn.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
