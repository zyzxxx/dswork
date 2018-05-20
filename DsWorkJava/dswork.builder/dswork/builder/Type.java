package dswork.builder;

import java.sql.SQLException;

public abstract class Type
{
	public abstract void initConnect(String url) throws SQLException;
	public abstract Table queryTable(String tableName);
	public abstract void close();
}
