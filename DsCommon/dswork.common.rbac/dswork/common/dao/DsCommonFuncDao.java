/**
 * 功能Dao
 */
package dswork.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import dswork.common.model.DsCommonFunc;
import dswork.common.model.DsCommonRes;
import dswork.core.db.BaseDao;

@Repository
@SuppressWarnings("unchecked")
public class DsCommonFuncDao extends BaseDao<DsCommonFunc, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsCommonFunc.class;
	}

	/**
	 * 移动节点
	 * @param id 功能主键
	 * @param pid 小于等于0则是根节点
	 * @param map 临时map对象，传递进来后会被clear，并放入id和seq
	 */
	public void updatePid(Long id, long pid, Map<String, Object> map)
	{
		map.clear();
		map.put("id", id);
		map.put("pid", pid);
		executeUpdate("updatePid", map);
	}

	/**
	 * 排序节点
	 * @param id 功能主键
	 * @param seq 排序位置
	 */
	public void updateSeq(Long id, Long seq)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("seq", seq);
		executeUpdate("updateSeq", map);
	}

	/**
	 * 判断标识是否存在
	 * @param alias 标识
	 * @param systemid 所属系统主键
	 * @return boolean 存在true，不存在false
	 */
	public boolean isExistByAlias(String alias, long systemid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alias", alias);
		map.put("systemid", systemid);
		DsCommonFunc m = (DsCommonFunc) executeSelect("query", map);
		if(m != null && m.getId().longValue() != 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * 新增对象
	 * @param entity 功能资源对象
	 * @return int
	 */
	public int saveRes(DsCommonRes entity)
	{
		return executeInsert("insertRes", entity);
	}

	/**
	 * 删除功能下所有功能资源
	 * @param funcid 功能主键
	 */
	public void deleteResByFuncid(Long funcid)
	{
		executeDelete("deleteResByFuncid", funcid);
	}

	/**
	 * 根据功能获得功能资源
	 * @param funcid 功能主键
	 * @return List&lt;DsCommonRes&gt;
	 */
	public List<DsCommonRes> queryResByFuncid(Long funcid)
	{
		return executeSelectList("queryResByFuncid", funcid);
	}
}
