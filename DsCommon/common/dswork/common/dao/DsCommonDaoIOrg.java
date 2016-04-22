/**
 * 公共Dao
 */
package dswork.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.common.model.IOrg;
import dswork.core.db.MyBatisDao;

@Repository
@SuppressWarnings("all")
public class DsCommonDaoIOrg extends MyBatisDao
{
	@Override
	protected Class getEntityClass()
	{
		return DsCommonDaoIOrg.class;
	}
	
	// /////////////////////////////////////////////////////////////////////////
	// 组织机构
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * 根据上级组织机构主键取得列表数据
	 * @param pid 上级组织机构主键
	 * @param status 0-2为指定分类（2单位，1部门，0岗位），超出0-2范围则不过滤
	 * @return List&lt;IOrg&gt;
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
