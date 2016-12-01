/**
 * 内容Service
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
import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.core.page.PageRequest;

@Service
@SuppressWarnings("all")
public class DsCmsPageService extends BaseService<DsCmsPage, Long>
{
	@Autowired
	private DsCmsCategoryDao catDao;
	@Autowired
	private DsCmsSiteDao siteDao;
	@Autowired
	private DsCmsPageDao dao;

	@Override
	protected EntityDao getEntityDao()
	{
		return dao;
	}
	
	public int save(DsCmsPage m)
	{
		dao.save(m);
		dao.updateURL(m.getId(), m.getUrl() + "/" + m.getId() + ".html");
		return 1;
	}
	public int updateStatus(Long id, Integer status)
	{
		dao.updateStatus(id, status);
		return 1;
	}

	public DsCmsSite getSite(Long siteid)
	{
		return (DsCmsSite) siteDao.get(siteid);
	}

	public DsCmsCategory getCategory(Long categoryid)
	{
		return (DsCmsCategory) catDao.get(categoryid);
	}

	public List<DsCmsSite> queryListSite(PageRequest rq)
	{
		return siteDao.queryList(rq);
	}

	public List<DsCmsCategory> queryListCategory(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return catDao.queryList(new PageRequest(map));
	}
	
	public void updateCategory(DsCmsCategory po)
	{
		catDao.updateContent(po);
	}
}
