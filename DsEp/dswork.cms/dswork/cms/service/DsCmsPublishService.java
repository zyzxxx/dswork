/**
 * 采编审核Service
 */
package dswork.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.cms.dao.DsCmsCategoryDao;
import dswork.cms.dao.DsCmsCountDao;
import dswork.cms.dao.DsCmsPageDao;
import dswork.cms.dao.DsCmsPageEditDao;
import dswork.cms.dao.DsCmsSiteDao;
import dswork.cms.dao.DsCmsSpecialDao;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsCount;
import dswork.cms.model.DsCmsPage;
import dswork.cms.model.DsCmsSite;
import dswork.cms.model.DsCmsSpecial;
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
	private DsCmsPageEditDao pageEditDao;
	@Autowired
	private DsCmsCountDao countDao;
	@Autowired
	private DsCmsSpecialDao specialDao;

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

	public List<DsCmsCount> queryCountForPublish(long siteid, List<Long> idsForPageList, List<Long> idsForCategoryList)
	{
		String idsForPage = "", idsForCategory = "" ;
		for(int i = 0; i < idsForPageList.size(); i++)
		{
			idsForPage += idsForPageList.get(i);
			if(i < idsForPageList.size() - 1)
			{
				idsForPage += ",";
			}
		}
		for(int i = 0; i < idsForCategoryList.size(); i++)
		{
			idsForCategory += idsForCategoryList.get(i);
			if(i < idsForCategoryList.size() - 1)
			{
				idsForCategory += ",";
			}
		}
		List<DsCmsCount> resultList;
		if(idsForPage.length() > 0 || idsForCategory.length() > 0)
		{
			resultList = countDao.queryCountForPublish(siteid, idsForPage, idsForCategory);
		}
		else
		{
			resultList = new ArrayList<DsCmsCount>();
		}
		return resultList;
	}

	public void deletePage(long siteid, long categoryid)
	{
		pageEditDao.delete(siteid, categoryid);
		pageDao.delete(siteid, categoryid);
	}

	public List<DsCmsSpecial> querySpecialList(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return specialDao.queryList(map);
	}

	public DsCmsSpecial getSpecial(long id)
	{
		return specialDao.get(id);
	}
}
