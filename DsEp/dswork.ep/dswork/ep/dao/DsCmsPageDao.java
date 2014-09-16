/**
 * 内容Dao
 */
package dswork.ep.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.core.db.BaseDao;
import dswork.ep.model.DsCmsPage;

@Repository
@SuppressWarnings("all")
public class DsCmsPageDao extends BaseDao<DsCmsPage, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsCmsPage.class;
	}

	/**
	 * 更新链接
	 * @param id 主键
	 * @param url 链接
	 */
	public void updateURL(long id, String url)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("url", url);
		executeUpdate("updateURL", map);
	}
}
