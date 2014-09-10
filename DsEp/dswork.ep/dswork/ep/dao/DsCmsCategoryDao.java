/**
 * 栏目Dao
 */
package dswork.ep.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.core.db.BaseDao;
import dswork.ep.model.DsCmsCategory;

@Repository
@SuppressWarnings("all")
public class DsCmsCategoryDao extends BaseDao<DsCmsCategory, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsCmsCategory.class;
	}

	/**
	 * 排序节点
	 * @param id 主键
	 * @param seq 排序位置
	 */
	public void updateSeq(long id, int seq)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("seq", seq);
		executeUpdate("updateSeq", map);
	}

	/**
	 * 更新单页栏目内容
	 * @param id 主键
	 * @param keywords 关键词
	 * @param content 内容
	 */
	public void updateContent(long id, String keywords, String content)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("keywords", keywords);
		map.put("content", content);
		executeUpdate("updateContent", map);
	}
}
