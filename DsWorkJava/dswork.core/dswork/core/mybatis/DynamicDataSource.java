package dswork.core.mybatis;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@SuppressWarnings("all")
public class DynamicDataSource extends AbstractRoutingDataSource
{
	private static final ThreadLocal contextHolder = new ThreadLocal();

	public static void setDbType(String dbType)
	{
		System.out.println("DynamicDataSource.setDbType is running.dbType=" + String.valueOf(dbType));
		contextHolder.set(dbType);
	}

	/**
	 * 取得dbtype类型
	 * @return
	 */
	public static String getDbType()
	{
		String str = (String) contextHolder.get();
		if (null == str || "".equals(str))
			str = "1";
		System.out.println("DynamicDataSource.getDbType is running.str=" + str);
		return str;
	}

	public static void clearDbType()
	{
		System.out.println("DynamicDataSource.clearDbType is running.");
		contextHolder.remove();
	}

	// 实现determineCurrentLookupKey接口
	@Override
	protected Object determineCurrentLookupKey()
	{
		System.out.println("DynamicDataSource.determineCurrentLookupKey is running." + "DynamicDataSource.getDbType()=" + DynamicDataSource.getDbType());
		return DynamicDataSource.getDbType();
	}
}
