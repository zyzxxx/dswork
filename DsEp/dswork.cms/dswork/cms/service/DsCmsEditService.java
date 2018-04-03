/**
 * 采编审核Service
 */
package dswork.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.cms.dao.DsCmsCategoryEditDao;
import dswork.cms.dao.DsCmsPageEditDao;
import dswork.cms.dao.DsCmsCategoryDao;
import dswork.cms.dao.DsCmsLogDao;
import dswork.cms.dao.DsCmsPageDao;
import dswork.cms.dao.DsCmsSiteDao;
import dswork.cms.model.DsCmsCategoryEdit;
import dswork.cms.model.DsCmsPageEdit;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsLog;
import dswork.cms.model.DsCmsPage;
import dswork.cms.model.DsCmsSite;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;
import dswork.core.util.UniqueId;

@Service
public class DsCmsEditService
{
	@Autowired
	private DsCmsPageEditDao pageEditDao;
	@Autowired
	private DsCmsCategoryEditDao categoryEditDao;
	@Autowired
	private DsCmsLogDao logDao;
	@Autowired
	private DsCmsCategoryDao categoryDao;
	@Autowired
	private DsCmsSiteDao siteDao;
	@Autowired
	private DsCmsPageDao pageDao;

	public int savePageEdit(DsCmsPageEdit po, boolean writePage, boolean isEnablelog, String editid, String editname)
	{
		pageEditDao.save(po);
		if(po.getScope() != 2) // 不为外链
		{
			po.setUrl("/a/" + po.getCategoryid() + "/" + po.getId() + ".html");
			if(writePage)
			{
				DsCmsPage page = new DsCmsPage();
				page.setId(po.getId());
				page.setSiteid(po.getSiteid());
				page.setCategoryid(po.getCategoryid());
				page.setStatus(po.getStatus());
				page.setTitle(po.getTitle());
				page.setMetakeywords(po.getMetakeywords());
				page.setMetadescription(po.getMetadescription());
				page.setSummary(po.getSummary());
				page.setContent(po.getContent());
				page.setReleasetime(po.getReleasetime());
				page.setReleasesource(po.getReleasesource());
				page.setReleaseuser(po.getReleaseuser());
				page.setImg(po.getImg());
				page.setImgtop(po.getImgtop());
				page.setPagetop(po.getPagetop());
				page.setScope(po.getScope());
				page.setStatus(0);// page设置为新建未发布状态
				pageDao.save(page);
				pageDao.updateURL(po.getId(), po.getUrl());
				po.setAuditstatus(0);// pageEdit设置为草稿状态
			}
			pageEditDao.update(po);
		}
		if(po.isAudit() && isEnablelog)
		{
			writeLogPage(po, editid, editname);
		}
		return 1;
	}

	public int deletePageEdit(Long id, boolean deletePage)
	{
		pageEditDao.delete(id);
		if(deletePage)
		{
			pageDao.delete(id);
		}
		return 1;
	}

	public int updateCategoryEdit(DsCmsCategoryEdit po, boolean writeCategory, boolean isEnablelog, String editid, String editname)
	{
		if(po.isAudit() && isEnablelog)
		{
			writeLogCategory(po, editid, editname);
		}
		if(writeCategory)
		{
			DsCmsCategory c = (DsCmsCategory) categoryDao.get(po.getId());
			if(c.getScope() == 0 || c.getScope() == 1)
			{
				c.setSummary(po.getSummary());
				c.setMetakeywords(po.getMetakeywords());
				c.setMetadescription(po.getMetadescription());
				c.setReleasesource(po.getReleasesource());
				c.setReleaseuser(po.getReleaseuser());
				c.setImg(po.getImg());
				c.setContent(po.getContent());
				c.setReleasetime(po.getReleasetime());
				if(c.getStatus() != 0)
				{
					c.setStatus(1);
				}
				categoryDao.updateContent(c);
			}
			else if(c.getScope() == 2)
			{
				c.setUrl(po.getUrl());
				c.setStatus(8);// Category设置为已发布状态（因为外链不需要发布操作）
				categoryDao.update(c);
			}
			po.setAuditstatus(0);// CategoryEdit设置为草稿状态
		}
		return categoryEditDao.update(po);
	}

	public int updateRevokeCategoryEdit(DsCmsCategoryEdit po, boolean isEnablelog, String editid, String editname)
	{
		if(po.getStatus() == -1)
		{
			po.setStatus(1); // 修改
		}
		po.setAuditstatus(0); // 草稿
		categoryEditDao.update(po);
		po.setStatus(4); // 撤销
		if(isEnablelog)
		{
			writeLogCategory(po, editid, editname);
		}
		return 1;
	}

	public int updatePageEdit(DsCmsPageEdit po, boolean writePage, boolean isEnablelog, String editid, String editname)
	{
		if(po.isAudit() && isEnablelog)
		{
			writeLogPage(po, editid, editname);
		}
		if(writePage)
		{
			DsCmsPage page = (DsCmsPage) pageDao.get(po.getId());
			boolean isSave = false;
			if(page == null)
			{
				page = new DsCmsPage();
				page.setId(po.getId());
				isSave = true;
			}
			page.setSiteid(po.getSiteid());
			page.setCategoryid(po.getCategoryid());
			page.setStatus(po.getStatus());
			page.setTitle(po.getTitle());
			page.setMetakeywords(po.getMetakeywords());
			page.setMetadescription(po.getMetadescription());
			page.setSummary(po.getSummary());
			page.setContent(po.getContent());
			page.setReleasetime(po.getReleasetime());
			page.setReleasesource(po.getReleasesource());
			page.setReleaseuser(po.getReleaseuser());
			page.setImg(po.getImg());
			page.setImgtop(po.getImgtop());
			page.setPagetop(po.getPagetop());
			page.setScope(po.getScope());
			if(isSave)
			{
				page.setStatus(0);// page设置为新建未发布状态
				pageDao.save(page);
				pageDao.updateURL(po.getId(), po.getUrl());
			}
			else
			{
				if(page.getStatus() != 0)
				{
					page.setStatus(1);// page设置为更新未发布状态
				}
				pageDao.update(page);
			}
			po.setAuditstatus(0);// pageEdit设置为草稿状态
		}
		if(po.getScope() != 2)
		{
			po.setUrl("/a/" + po.getCategoryid() + "/" + po.getId() + ".html");
		}
		return pageEditDao.update(po);
	}

	public int updateRevokePageEdit(DsCmsPageEdit po, boolean isEnablelog, String editid, String editname)
	{
		if(po.getStatus() == -1)
		{
			po.setStatus(1); // 修改
		}
		po.setAuditstatus(0); // 草稿
		pageEditDao.update(po);
		po.setStatus(4); // 撤销
		if(isEnablelog)
		{
			writeLogPage(po, editid, editname);
		}
		return 1;
	}

	public DsCmsSite getSite(Long siteid)
	{
		return (DsCmsSite) siteDao.get(siteid);
	}

	public DsCmsCategory getCategory(Long categoryid)
	{
		return (DsCmsCategory) categoryDao.get(categoryid);
	}

	public DsCmsCategoryEdit saveCategoryEdit(Long id)
	{
		DsCmsCategoryEdit po = new DsCmsCategoryEdit();
		DsCmsCategory _po = (DsCmsCategory) categoryDao.get(id);
		po.setId(_po.getId());
		po.setSiteid(_po.getSiteid());
		po.setSummary(_po.getSummary());
		po.setMetakeywords(_po.getMetakeywords());
		po.setMetadescription(_po.getMetadescription());
		po.setReleasesource(_po.getReleasesource());
		po.setReleaseuser(_po.getReleaseuser());
		po.setImg(_po.getImg());
		po.setContent(_po.getContent());
		po.setReleasetime(_po.getReleasetime());
		po.setUrl(_po.getUrl());
		po.setAuditstatus(DsCmsCategoryEdit.EDIT);// 编辑状态
		po.setStatus(0);// 初始设置为新增状态
		categoryEditDao.save(po);
		return po;
	}

	public DsCmsCategoryEdit getCategoryEdit(Long id)
	{
		return (DsCmsCategoryEdit) categoryEditDao.get(id);
	}

	public DsCmsPageEdit getPageEdit(Long id)
	{
		return (DsCmsPageEdit) pageEditDao.get(id);
	}

	public DsCmsPage getPage(Long id)
	{
		return (DsCmsPage) pageDao.get(id);
	}

	public List<DsCmsSite> queryListSite(String own, String accout)
	{
		return siteDao.queryList(own, accout);
	}

	@SuppressWarnings("unchecked")
	public Page<DsCmsPageEdit> queryPagePageEdit(PageRequest pr)
	{
		return pageEditDao.queryPage(pr);
	}

	@SuppressWarnings("unchecked")
	public List<DsCmsCategory> queryListCategory(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return categoryDao.queryList(map);
	}

	private void writeLogPage(DsCmsPageEdit po, String editid, String editname)
	{
		try
		{
			DsCmsLog log = new DsCmsLog();
			log.setId(UniqueId.genId());
			log.setSiteid(po.getSiteid());
			log.setCategoryid(po.getCategoryid());
			log.setPageid(po.getId());
			log.setEditid(editid);
			log.setEditname(editname);
			log.setEdittime(po.getEdittime());
			log.setStatus(po.getStatus());
			log.setAuditstatus(po.getAuditstatus());
			log.setTitle(po.getTitle());
			log.setScope(po.getScope());
			log.setUrl(po.getUrl());
			log.setMetakeywords(po.getMetakeywords());
			log.setMetadescription(po.getMetadescription());
			log.setSummary(po.getSummary());
			log.setReleasetime(po.getReleasetime());
			log.setReleasesource(po.getReleasesource());
			log.setReleaseuser(po.getReleaseuser());
			log.setImg(po.getImg());
			log.setContent(po.getContent());
			log.setImgtop(po.getImgtop());
			log.setPagetop(po.getPagetop());
			logDao.save(log);
		}
		catch(Exception e)
		{
		}
	}

	private void writeLogCategory(DsCmsCategoryEdit po, String editid, String editname)
	{
		try
		{
			DsCmsLog log = new DsCmsLog();
			log.setId(UniqueId.genId());
			log.setSiteid(po.getSiteid());
			log.setCategoryid(po.getId());
			log.setEditid(editid);
			log.setEditname(editname);
			log.setEdittime(po.getEdittime());
			log.setStatus(po.getStatus());
			log.setAuditstatus(po.getAuditstatus());
			log.setTitle(po.getName());
			log.setScope(po.getScope());
			log.setUrl(po.getUrl());
			log.setMetakeywords(po.getMetakeywords());
			log.setMetadescription(po.getMetadescription());
			log.setSummary(po.getSummary());
			log.setReleasetime(po.getReleasetime());
			log.setReleasesource(po.getReleasesource());
			log.setReleaseuser(po.getReleaseuser());
			log.setImg(po.getImg());
			log.setContent(po.getContent());
			logDao.save(log);
		}
		catch(Exception e)
		{
		}
	}
}
