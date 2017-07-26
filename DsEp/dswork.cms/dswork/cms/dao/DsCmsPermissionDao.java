/**
 * 采编审核权限Dao
 */
package dswork.cms.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import dswork.core.db.BaseDao;
import dswork.cms.model.DsCmsPermission;

@Repository
@SuppressWarnings("all")
public class DsCmsPermissionDao extends BaseDao<DsCmsPermission, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsCmsPermission.class;
	}

	public DsCmsPermission getByOwnAccount(String own, String account)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("own", own);
		map.put("account", account);
		return (DsCmsPermission) executeSelect("selectByOwnAccount", map);
	}
}