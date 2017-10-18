/**
 * 用户Service
 */
package dswork.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.cms.dao.DsCmsCategoryDao;
import dswork.cms.dao.DsCmsPermissionDao;
import dswork.cms.dao.DsCmsSiteDao;
import dswork.cms.dao.DsCmsUserDao;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsPermission;
import dswork.cms.model.DsCmsSite;
import dswork.core.db.BaseService;
import dswork.core.db.EntityDao;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;

@Service
@SuppressWarnings("all")
public class DsCmsPermissionService
{
	@Autowired
	private DsCmsPermissionDao dao;
	@Autowired
	private DsCmsSiteDao siteDao;
	@Autowired
	private DsCmsCategoryDao categoryDao;
	@Autowired
	private DsCmsUserDao userDao;

	public int save(DsCmsPermission po)
	{
		return dao.save(po);
	}

	public int delete(long siteid, String account)
	{
		return dao.delete(siteid, account);
	}

	public int update(DsCmsPermission po)
	{
		return dao.update(po);
	}

	public DsCmsPermission get(Long siteid, String account)
	{
		return dao.get(siteid, account);
	}

	public List<DsCmsSite> queryListSite(Map<String, Object> map)
	{
		return siteDao.queryList(map);
	}

	public List<DsCmsCategory> queryListCategory(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return categoryDao.queryList(map);
	}

	public Page<Map<String, Object>> queryPageCommonUser(PageRequest pr)
	{
		return userDao.queryPageCommonUser(pr);
	}

	public Page<Map<String, Object>> queryPageEpUser(PageRequest pr)
	{
		return userDao.queryPageEpUser(pr);
	}

	public Page<Map<String, Object>> queryPagePersonUser(PageRequest pr)
	{
		return userDao.queryPagePersonUser(pr);
	}
}
