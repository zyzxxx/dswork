/**
 * 用户岗位Service
 */
package dswork.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.common.dao.DsCommonOrgDao;
import dswork.common.dao.DsCommonUserOrgDao;
import dswork.common.model.DsCommonOrg;
import dswork.common.model.DsCommonUserOrg;

@Service
public class DsCommonUserOrgService
{
	@Autowired
	private DsCommonUserOrgDao userorgDao;
	@Autowired
	private DsCommonOrgDao orgDao;

	/**
	 * 新增对象
	 * @param orgid 岗位ID
	 * @param useridList 用户ID集合
	 */
	public void saveByOrg(Long orgid, List<Long> useridList)
	{
		userorgDao.deleteByOrgid(orgid);
		DsCommonUserOrg o = new DsCommonUserOrg();
		o.setOrgid(orgid);
		for(Long id : useridList)
		{
			if(id > 0)
			{
				o.setUserid(id);
				userorgDao.save(o);
			}
		}
	}

	/**
	 * 新增对象
	 * @param userid 用户ID
	 * @param orgidList 岗位ID集合
	 */
	public void saveByUser(Long userid, List<Long> orgidList)
	{
		userorgDao.deleteByUserid(userid);
		DsCommonUserOrg o = new DsCommonUserOrg();
		o.setUserid(userid);
		for(Long id : orgidList)
		{
			if(id > 0)
			{
				o.setOrgid(id);
				userorgDao.save(o);
			}
		}
	}

	/**
	 * 根据岗位获得授权用户
	 * @param orgid 岗位主键
	 * @return List&lt;DsCommonUserOrg&gt;
	 */
	public List<DsCommonUserOrg> queryListByOrgid(Long orgid)
	{
		return userorgDao.queryListByOrgid(orgid);
	}

	/**
	 * 根据用户获得授权岗位
	 * @param userid 用户主键
	 * @return List&lt;DsCommonUserOrg&gt;
	 */
	public List<DsCommonUserOrg> queryListByUserid(Long userid)
	{
		return userorgDao.queryListByUserid(userid);
	}

	/**
	 * 查询单个组织机构对象
	 * @param primaryKey 组织机构主键
	 * @return DsCommonOrg
	 */
	public DsCommonOrg get(Long primaryKey)
	{
		return (DsCommonOrg) orgDao.get(primaryKey);
	}
}
