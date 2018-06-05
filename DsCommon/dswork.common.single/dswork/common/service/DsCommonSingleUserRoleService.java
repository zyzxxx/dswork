/**
 * 用户岗位Service
 */
package dswork.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.common.dao.DsCommonOrgDao;
import dswork.common.dao.DsCommonOrgRoleDao;
import dswork.common.dao.DsCommonRoleDao;
import dswork.common.dao.DsCommonUserDao;
import dswork.common.dao.DsCommonUserOrgDao;
import dswork.common.model.DsCommonOrg;
import dswork.common.model.DsCommonOrgRole;
import dswork.common.model.DsCommonRole;
import dswork.common.model.DsCommonUser;
import dswork.common.model.DsCommonUserOrg;
import dswork.core.util.UniqueId;

@Service
@SuppressWarnings("all")
public class DsCommonSingleUserRoleService
{
	@Autowired
	private DsCommonUserOrgDao userorgDao;
	@Autowired
	private DsCommonRoleDao roleDao;
	@Autowired
	private DsCommonUserDao userDao;
	@Autowired
	private DsCommonOrgDao orgDao;
	@Autowired
	private DsCommonOrgRoleDao orgroleDao;

	public DsCommonUser getUserByAccount(String account)
	{
		return userDao.getByAccount(account);
	}

	public DsCommonOrg getOrg(Long id)
	{
		return (DsCommonOrg) orgDao.get(id);
	}

	public DsCommonUser getUser(Long id)
	{
		return (DsCommonUser) userDao.get(id);
	}

	public List<DsCommonUser> queryUserList(Long pid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgid", pid);
		return userDao.queryList(map);
	}

	public List<DsCommonOrgRole> queryOrgRoleList(Long orgid)
	{
		return orgroleDao.queryList(orgid);
	}

	public void saveOrgRole(Long orgid, List<Long> roleidList)
	{
		orgroleDao.deleteByOrgid(orgid);
		DsCommonOrgRole o = new DsCommonOrgRole();
		o.setOrgid(orgid);
		for(Long roleid : roleidList)
		{
			if(roleid > 0)
			{
				o.setId(UniqueId.genUniqueId());
				o.setRoleid(roleid);
				orgroleDao.save(o);
			}
		}
	}
}
