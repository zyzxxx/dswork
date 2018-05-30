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
import dswork.common.dao.DsCommonUserDao;
import dswork.common.dao.DsCommonUserOrgDao;
import dswork.common.model.DsCommonOrg;
import dswork.common.model.DsCommonUser;
import dswork.common.model.DsCommonUserOrg;
import dswork.core.util.UniqueId;

@Service
@SuppressWarnings("all")
public class DsCommonExAuthorizeService
{
	@Autowired
	private DsCommonUserOrgDao userorgDao;
	@Autowired
	private DsCommonOrgDao orgDao;
	@Autowired
	private DsCommonUserDao userDao;

	public DsCommonUser getUserByAccount(String account)
	{
		return userDao.getByAccount(account);
	}

	public void saveByOrg(Long orgid, List<Long> useridList)
	{
		userorgDao.deleteByOrgid(orgid);
		DsCommonUserOrg o = new DsCommonUserOrg();
		o.setOrgid(orgid);
		for(Long id : useridList)
		{
			if(id != 0)
			{
				o.setId(UniqueId.genUniqueId());
				o.setUserid(id);
				userorgDao.save(o);
			}
		}
	}

	public void saveByUser(Long userid, List<Long> orgidList)
	{
		userorgDao.deleteByUserid(userid);
		DsCommonUserOrg o = new DsCommonUserOrg();
		o.setUserid(userid);
		for(Long id : orgidList)
		{
			if(id > 0)
			{
				o.setId(UniqueId.genUniqueId());
				o.setOrgid(id);
				userorgDao.save(o);
			}
		}
	}

	public List<DsCommonUserOrg> queryListByOrgid(Long orgid)
	{
		return userorgDao.queryListByOrgid(orgid);
	}

	public List<DsCommonUserOrg> queryListByUserid(Long userid)
	{
		return userorgDao.queryListByUserid(userid);
	}

	public DsCommonOrg get(Long primaryKey)
	{
		return (DsCommonOrg) orgDao.get(primaryKey);
	}
	
	public List<DsCommonOrg> queryOrgList(Long pid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		map.put("status", 0);
		return orgDao.queryList(map);
	}
	
	public List<DsCommonUser> queryUserList(Long pid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgid", pid);
		return userDao.queryList(map);
	}
	
	public DsCommonUser getUserById(Long userid)
	{
		return (DsCommonUser) userDao.get(userid);
	}
	
	public List<DsCommonOrg> queryOrgListByUserid(Long userid)
	{
		List<DsCommonUserOrg> ls = userorgDao.queryListByUserid(userid);
		List<DsCommonOrg> list = new ArrayList<DsCommonOrg>();
		for(DsCommonUserOrg u : ls)
		{
			DsCommonOrg org = (DsCommonOrg) orgDao.get(u.getOrgid());
			list.add(org);
		}
		return list;
	}
}
