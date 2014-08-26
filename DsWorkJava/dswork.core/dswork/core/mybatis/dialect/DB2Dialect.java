package dswork.core.mybatis.dialect;

/**
 * Dialect for DB2
 * @author skey
 */
public class DB2Dialect extends Dialect
{
	/**
	 * 是否支持分页，limit和offset
	 * @return boolean
	 */
	public boolean supportsLimitOffset()
	{
		return true;
	}

	/**
	 * 将sql变成分页sql
	 * @param sql 原始sql语句
	 * @param offset 跳过行数
	 * @param limit 返回行数
	 * @return String
	 */
	public String getLimitString(String sql, int offset, int limit)
	{
		StringBuilder sb = new StringBuilder(sql.length() + 100);
		sb.append("select * from ( select ROW_NUMBER() over() as rn, t.* from (")
		.append(sql)
		.append(" ) t ")
		.append(" ) where rn > ").append(offset).append(" and rn <=").append(offset + limit);
		return sb.toString();
	}
}
