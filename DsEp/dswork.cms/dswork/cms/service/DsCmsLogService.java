/**
 * 日志Service
 */
package dswork.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dswork.core.db.EntityDao;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;
import dswork.core.db.BaseService;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsLog;
import dswork.cms.model.DsCmsSite;
import dswork.cms.dao.DsCmsCategoryDao;
import dswork.cms.dao.DsCmsLogDao;
import dswork.cms.dao.DsCmsSiteDao;

@Service
@SuppressWarnings("all")
public class DsCmsLogService
{
	@Autowired
	private DsCmsLogDao dao;
	@Autowired
	private DsCmsCategoryDao categoryDao;
	@Autowired
	private DsCmsSiteDao siteDao;

	public List<DsCmsSite> queryListSite(Map<String, Object> map)
	{
		return siteDao.queryList(map);
	}

	public List<DsCmsCategory> queryListCategory(long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return categoryDao.queryList(map);
	}

	public Page<DsCmsLog> queryPage(PageRequest pr)
	{
		return dao.queryPage(pr);
	}
}
