/**
 * 字典分类Dao
 */
package dswork.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.common.imodel.IDsDict;
import dswork.core.db.MyBatisDao;

@Repository
@SuppressWarnings("unchecked")
public class DsCommonDao extends MyBatisDao
{
	@Override
	protected Class getEntityClass()
	{
		return DsCommonDao.class;
	}
	
	/**
	 * 获取指定节点的列表数据
	 * @param name 字典分类名
	 * @param alias 上级标识，当alias为null时获取全部节点数据，当alias为""时获取根节点数据
	 * @return List&lt;IDsDict&gt;
	 */
	public List<IDsDict> queryList(String name, String parentAlias)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", String.valueOf(name));
		map.put("alias", parentAlias);
		List<IDsDict> list = executeSelectList("queryDict", map);
		if(parentAlias != null && parentAlias.length() > 0)
		{
			for(IDsDict po : list)
			{
				po.setPid(parentAlias);
			}
		}
		return list;
	}
}