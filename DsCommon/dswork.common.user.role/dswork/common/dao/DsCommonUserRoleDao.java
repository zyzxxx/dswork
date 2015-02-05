/**
 * 用户角色Dao
 */
package dswork.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.common.model.DsCommonUserRole;
import dswork.core.db.MyBatisDao;

@Repository
@SuppressWarnings("all")
public class DsCommonUserRoleDao extends MyBatisDao
{
	@Override
	public Class getEntityClass()
	{
		return DsCommonUserRole.class;
	}

	/**
	 * 新增对象
	 * @param entity 需要新增的对象模型
	 * @return int
	 */
	public int save(DsCommonUserRole entity)
	{
		return executeInsert("insert", entity);
	}

	/**
	 * 删除该角色下授权用户
	 * @param roleid 角色主键
	 * @return int
	 */
	public int deleteByRoleid(Long roleid)
	{
		return executeDelete("deleteByRoleid", roleid);
	}

	/**
	 * 删除该用户下授权角色
	 * @param userid 用户主键
	 * @return int
	 */
	public int deleteByUserid(Long userid)
	{
		return executeDelete("deleteByUserid", userid);
	}

	/**
	 * 根据角色获得授权用户
	 * @param orgid 角色主键
	 * @return List&lt;DsCommonUserOrg&gt;
	 */
	public List<DsCommonUserRole> queryListByRoleid(Long roleid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleid", roleid);
		return executeSelectList("query", map);
	}

	/**
	 * 根据用户获得授权角色
	 * @param userid 用户主键
	 * @return List&lt;DsCommonUserRole&gt;
	 */
	public List<DsCommonUserRole> queryListByUserid(Long userid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		return executeSelectList("query", map);
	}
}
