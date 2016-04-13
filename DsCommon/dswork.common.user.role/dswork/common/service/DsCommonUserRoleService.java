/**
 * 用户角色Service
 */
package dswork.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.common.dao.DsCommonRoleDao;
import dswork.common.dao.DsCommonUserDao;
import dswork.common.dao.DsCommonUserRoleDao;
import dswork.common.model.DsCommonRole;
import dswork.common.model.DsCommonUser;
import dswork.common.model.DsCommonUserRole;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;
import dswork.core.util.UniqueId;

@Service
@SuppressWarnings("all")
public class DsCommonUserRoleService
{
	@Autowired
	private DsCommonUserRoleDao userroleDao;
	@Autowired
	private DsCommonRoleDao roleDao;
	@Autowired
	private DsCommonUserDao userDao;

	/**
	 * 新增对象
	 * @param orgid 角色ID
	 * @param useridList 用户ID集合
	 */
	public void saveByRole(Long roleid, List<Long> useridList)
	{
		userroleDao.deleteByRoleid(roleid);
		DsCommonUserRole o = new DsCommonUserRole();
		o.setRoleid(roleid);
		for(Long id : useridList)
		{
			o.setId(UniqueId.genUniqueId());
			o.setUserid(id);
			userroleDao.save(o);
		}
	}

	/**
	 * 新增对象
	 * @param userid 用户ID
	 * @param orgidList 角色ID集合
	 */
	public void saveByUser(Long userid, List<Long> roleidList)
	{
		userroleDao.deleteByUserid(userid);
		DsCommonUserRole o = new DsCommonUserRole();
		o.setUserid(userid);
		for(Long id : roleidList)
		{
			if(id > 0)
			{
				o.setId(UniqueId.genUniqueId());
				o.setRoleid(id);
				userroleDao.save(o);
			}
		}
	}

	/**
	 * 根据角色获得授权用户
	 * @param roleid 角色主键
	 * @return List&lt;DsCommonUserRole&gt;
	 */
	public List<DsCommonUserRole> queryListByRoleid(Long roleid)
	{
		return userroleDao.queryListByRoleid(roleid);
	}

	/**
	 * 根据用户获得授权角色
	 * @param userid 用户主键
	 * @return List&lt;DsCommonUserRole&gt;
	 */
	public List<DsCommonUserRole> queryListByUserid(Long userid)
	{
		return userroleDao.queryListByUserid(userid);
	}

	public Page queryUserPage(PageRequest pageRequest)
	{
		return userDao.queryPage(pageRequest);
	}
	public DsCommonUser getUser(Long userid)
	{
		return (DsCommonUser)userDao.get(userid);
	}
	public List<DsCommonRole> queryRoleList(Long systemid, Long pid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		PageRequest request = new PageRequest();
		map.put("systemid", systemid);
		map.put("pid", pid);
		request.setFilters(map);
		return roleDao.queryList(request);
	}
}
