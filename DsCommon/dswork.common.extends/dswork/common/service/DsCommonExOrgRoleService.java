/**
 * 岗位角色Service
 */
package dswork.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.common.dao.DsCommonOrgDao;
import dswork.common.dao.DsCommonOrgRoleDao;
import dswork.common.dao.DsCommonUserDao;
import dswork.common.model.DsCommonOrg;
import dswork.common.model.DsCommonOrgRole;
import dswork.common.model.DsCommonUser;
import dswork.core.util.UniqueId;

@Service
public class DsCommonExOrgRoleService
{
	@Autowired
	private DsCommonUserDao userDao;
	@Autowired
	private DsCommonOrgRoleDao orgroleDao;
	@Autowired
	private DsCommonOrgDao orgDao;

	public DsCommonUser getUserByAccount(String account)
	{
		return userDao.getByAccount(account);
	}

	public void save(Long orgid, List<Long> roleidList)
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

	public List<DsCommonOrgRole> queryList(Long orgid)
	{
		return orgroleDao.queryList(orgid);
	}

	public DsCommonOrg get(Long primaryKey)
	{
		return (DsCommonOrg) orgDao.get(primaryKey);
	}
}
