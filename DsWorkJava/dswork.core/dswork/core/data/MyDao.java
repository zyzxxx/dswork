package dswork.core.data;

import org.springframework.stereotype.Repository;

/**
 * MyBatis基础Dao类
 * @author sille
 * @version 1.0
 */
@Repository
public class MyDao extends MyDataDao implements MyEntityDao
{
	/**
	 * 获取需要操作sql的id，当getEntityClass().getName()无法满足时，可以重载此方法
	 * @param statementName SQL的ID(不包含namespace)
	 * @return String
	 */
	protected String getStatementName(String statementName, Object obj)
	{
		return obj.getClass().getCanonicalName() + "." + statementName;
	}

	public int save(Object obj)
	{
		return this.executeInsert(getStatementName("insert", obj), obj);
	}

	public int delete(Object obj)
	{
		return this.executeDelete(getStatementName("delete", obj), obj);
	}

	public int update(Object obj)
	{
		return this.executeUpdate(getStatementName("update", obj), obj);
	}

	public Object get(Object obj)
	{
		return this.executeSelect(getStatementName("select", obj), obj);
	}
}
