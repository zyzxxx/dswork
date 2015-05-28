package gwen.devwork;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;


public abstract class BaseDao<T,PK> extends SqlSessionDaoSupport
{
	/**
	 * 用于返回命名空间的全路径Class.getName()
	 * @return Class
	 */
	protected abstract Class getEntityClass();
	private String _statement = null;
	
	/**
	 * 返回命名空间的值
	 * @return String
	 */
	private String getSqlNamespace()
	{
		return getEntityClass().getName();
	}

	/**
	 * 获取需要操作sql的id，当getEntityClass().getName()无法满足时，可以重载此方法
	 * @param statementName SQL的ID(不包含namespace)
	 * @return String
	 */
	protected String getStatementName(String statementName)
	{
		if(_statement == null)
		{
			_statement = getSqlNamespace() + ".";
		}
		return _statement + statementName;
	}
	
	public void add(T po){
		this.getSqlSession().insert(getStatementName("add"), po);
	}
	
	public void upd(T po){
		this.getSqlSession().update(getStatementName("upd"), po);
	}

	public void del(PK id){
		this.getSqlSession().delete(getStatementName("del"), id);
	}

	public T getById(PK id){
		return (T) this.getSqlSession().selectOne(getStatementName("getById"), id);
	}
	
	public List get(Map m){
		return this.getSqlSession().selectList(getStatementName("get"), m);
	}
	
	public int getCount(Map m){
		return (Integer) this.getSqlSession().selectOne(getStatementName("getCount"), m);
	}

	/**
	 * 分页查询
	 * @param countPageEach 可点击页码个数 
	 * @param m 表单查询参数
	 * @return Page对象
	 */
	public Page<T> getPage(int countPageEach, Map m)
	{
		int pageSize = Integer.parseInt(m.get("pageSize").toString());
		int curPage = Integer.parseInt(m.get("page").toString());
		
		int totalCount = getCount(m);//总记录数
		int countPage = totalCount%pageSize>0?totalCount/pageSize+1:totalCount/pageSize;//总页码
		int rownum = curPage*pageSize-(pageSize-1);
		rownum--;//mysql index 从0开始，所以要减一；oracle index 从1开始
		System.out.println("rownum:"+rownum);
		m.put("rownum", rownum);
		m.put("pagesize", pageSize);
		List result = get(m);
		return new Page<T>(curPage, countPage, countPageEach, pageSize, result);
	}
}
