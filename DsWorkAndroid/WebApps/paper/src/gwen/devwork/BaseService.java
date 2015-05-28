package gwen.devwork;

import java.util.List;
import java.util.Map;

public abstract class BaseService<T,PK> 
{
	public abstract BaseDao<T, PK> getEntityDao();
	
	public void add(T po){
		getEntityDao().add(po);
	}

	public void upd(T po) {
		getEntityDao().upd(po);
	}

	public void del(PK[] ids) {
		for(PK id:ids){
			getEntityDao().del(id);
		}
	}

	public T getById(PK id) {
		return (T) getEntityDao().getById(id);
	}

	public List get(Map m) {
		return getEntityDao().get(m);
	}
	
	public int getCount(Map m) {
		return getEntityDao().getCount(m);
	}

	/**
	 * 分页查询
	 * @param countPageEach 可点击页码个数 
	 * @param m 表单查询参数
	 * @return Page对象
	 */
	public Page<T> getPage(int countPageEach, Map m){
		return getEntityDao().getPage(countPageEach, m);
	}
}
