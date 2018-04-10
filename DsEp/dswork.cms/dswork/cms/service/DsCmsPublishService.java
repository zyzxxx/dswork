/**
 * 采编审核Service
 */
package dswork.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.cms.dao.DsCmsCategoryDao;
import dswork.cms.dao.DsCmsPageDao;
import dswork.cms.dao.DsCmsSiteDao;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsPage;
import dswork.cms.model.DsCmsSite;
import dswork.core.db.BaseService;
import dswork.core.db.EntityDao;

@Service
public class DsCmsPublishService extends BaseService<DsCmsPage, Long>
{
	@Autowired
	private DsCmsPageDao pageDao;
	@Autowired
	private DsCmsCategoryDao categoryDao;
	@Autowired
	private DsCmsSiteDao siteDao;
	@Autowired

	@Override
	protected EntityDao<DsCmsPage, Long> getEntityDao()
	{
		return pageDao;
	}

	public DsCmsSite getSite(Long siteid)
	{
		return (DsCmsSite) siteDao.get(siteid);
	}

	public DsCmsCategory getCategory(Long categoryid)
	{
		return (DsCmsCategory) categoryDao.get(categoryid);
	}

	public List<DsCmsSite> queryListSite(String own)
	{
		return siteDao.queryList(own);
	}

	@SuppressWarnings("unchecked")
	public List<DsCmsCategory> queryListCategory(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		map.put("publishstatus", "true");
		return categoryDao.queryList(map);
	}

	public void updatePageStatus(Long id, int status)
	{
		pageDao.updateStatus(id, status);
	}

	public void updateCategoryStatus(Long id, int status)
	{
		categoryDao.updateStatus(id, status);
	}
}
