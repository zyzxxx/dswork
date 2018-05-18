package dswork.builder;

public abstract class Type
{
	public abstract void initConnect(String url);
	public abstract Table queryTable(String tableName);
	public abstract void close();
}
