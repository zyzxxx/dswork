package dswork.builder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoMySql extends Dao
{
	private static final String SQL_TABLE_COMMENT = "select TABLE_COMMENT as CCOMMENT from information_schema.TABLES where TABLE_SCHEMA='%s' and TABLE_NAME='%s'";
	private static final String SQL_TABLE_COLUMN = "select "
			+ "COLUMN_NAME as CNAME, "
			+ "DATA_TYPE as CDATATYPE, "
			+ "CHARACTER_MAXIMUM_LENGTH as CLENGTH, "
			+ "IS_NULLABLE as CNULLABLE, "
			+ "NUMERIC_PRECISION as CPRECISION, "
			+ "NUMERIC_SCALE as CSCALE, "
			+ "COLUMN_COMMENT as CCOMMENT, "
			+ "EXTRA as CAUTO, "
			+ "COLUMN_DEFAULT as CDEFAULT,"
			+ "COLUMN_KEY as CKEY "
			+ "from information_schema.COLUMNS "
			+ "where TABLE_SCHEMA='%s' and TABLE_NAME='%s'";
//	private static final String SQL_ALL_TABLES = "select TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA='%s'";

	private Connection conn;
	private String dbName;

	@Override
	public void connect(String url) throws SQLException
	{
		conn = DriverManager.getConnection(url);
		dbName = conn.getCatalog();
	}

	private void querySetTableComment(Table table)
	{
		String comment = "";
		String sql = String.format(SQL_TABLE_COMMENT, dbName, table.getName());
		try
		{
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			comment = rs.getString("CCOMMENT");
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		table.setComment(comment);
	}

	private void querySetTableColumn(Table table)
	{
		String sql = String.format(SQL_TABLE_COLUMN, dbName, table.getName());
		try
		{
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				Table.Column col = table.addColumn();
				col.setName(rs.getString("CNAME"));
				col.setDatatype(rs.getString("CDATATYPE"));
				col.setLength(rs.getInt("CLENGTH"));
				col.setNullable("TRUE".equalsIgnoreCase(rs.getString("CNULLABLE")));
				col.setPrecision(rs.getInt("CPRECISION"));
				col.setDigit(rs.getInt("CSCALE"));
				col.setComment(rs.getString("CCOMMENT"));
				col.setAuto("auto_increment".equals(rs.getString("CAUTO")));
				col.setDefaultvalue(rs.getString("CDEFAULT"));
				col.setKey("PRI".equals(rs.getString("CKEY")));
				switch(col.getDatatype())
				{
					case "int":
					case "tinyint":
					case "mediumint":
						col.setDatatype("int"); break;
					case "bigint":
						if("id".equalsIgnoreCase(col.getName()))
						{
							col.setDatatype("Long");
						}
						else
						{
							col.setDatatype("long");
						}
						break;
					case "float":
					case "double":
						col.setDatatype("float"); break;
					case "decimal":
					{
						if(col.getDigit() == 0)
						{
							if(col.getPrecision() < 11)
							{
								col.setDatatype("int"); break;
							}
							col.setDatatype("long"); break;
						}
						else
						{
							col.setDatatype("float"); break;
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
						col.setDatatype("String"); break;
					default:
						break;
				}
			}
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Table query(String tableName)
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
