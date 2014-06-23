/**
 * 公共Dao
 */
package dswork.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.common.model.IDict;
import dswork.common.model.IOrg;
import dswork.core.db.MyBatisDao;

@Repository
@SuppressWarnings("all")
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
	public List<IDict> queryListDict(String name, String parentAlias)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", String.valueOf(name));
		map.put("alias", parentAlias);
		List<IDict> list = executeSelectList("queryDict", map);
		if(parentAlias != null && parentAlias.length() > 0)
		{
			for(IDict po : list)
			{
				po.setPid(parentAlias);
			}
		}
		return list;
	}
	
	/**
	 * 根据上级组织机构主键取得列表数据
	 * @param pid 上级组织机构主键
	 * @param status 0-2为指定分类（2单位，1部门，0岗位），超出0-2范围则不过滤
	 * @return List&lt;DsCommonOrg&gt;
	 */
	public List<IOrg> queryListOrg(Long pid, Integer status)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		if(status != null && status.intValue() > -1 && status.intValue() < 3)
		{
			map.put("status", status);
		}
		List<IOrg> list = executeSelectList("queryOrg", map);
		if(pid == 0)
		{
			for(IOrg po : list)
			{
				po.setPid("0");
			}
		}
		return list;
	}
}