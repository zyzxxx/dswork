/**
 * 栏目Dao
 */
package dswork.cms.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.cms.model.DsCmsCategory;
import dswork.core.db.BaseDao;

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
	 * @param siteid 站点ID
	 */
	public void updateSeq(long id, int seq, long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("seq", seq);
		map.put("siteid", siteid);
		executeUpdate("updateSeq", map);
	}

	/**
	 * 更新单页栏目内容
	 * @param id 主键
	 * @param keywords 关键词
	 * @param content 内容
	 */
	public void updateContent(long id, String metakeywords, String metadescription, String content)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("metakeywords", metakeywords);
		map.put("metadescription", metadescription);
		map.put("content", content);
		executeUpdate("updateContent", map);
	}
}
