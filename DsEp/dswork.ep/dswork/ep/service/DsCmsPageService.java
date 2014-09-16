/**
 * 内容Service
 */
package dswork.ep.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.core.page.PageRequest;
import dswork.ep.model.DsCmsCategory;
import dswork.ep.model.DsCmsPage;
import dswork.ep.model.DsCmsSite;
import dswork.ep.dao.DsCmsCategoryDao;
import dswork.ep.dao.DsCmsPageDao;
import dswork.ep.dao.DsCmsSiteDao;

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

	/**
	 * 更新单页栏目内容
	 * @param id 主键
	 * @param keywords 关键词
	 * @param content 内容
	 */
	public void updateCategory(long id, String keywords, String content)
	{
		catDao.updateContent(id, keywords, content);
	}
}
