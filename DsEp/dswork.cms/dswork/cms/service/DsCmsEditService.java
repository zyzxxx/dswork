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

@SuppressWarnings("unchecked")
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

	public void savePageEdit(DsCmsPageEdit po, boolean writePage, boolean enablelog, String editid, String editname)
	{
		if(po.getScope() != 2) // 不为外链
		{
			po.setUrl("/a/" + po.getCategoryid() + "/" + po.getId() + ".html");
		}
		if(writePage)
		{
			DsCmsPage p = new DsCmsPage();
			p.setId(po.getId());
			p.setSiteid(po.getSiteid());
			p.setCategoryid(po.getCategoryid());
			p.setStatus(po.getStatus());
			p.setTitle(po.getTitle());
			p.setMetakeywords(po.getMetakeywords());
			p.setMetadescription(po.getMetadescription());
			p.setSummary(po.getSummary());
			p.setContent(po.getContent());
			p.setReleasetime(po.getReleasetime());
			p.setReleasesource(po.getReleasesource());
			p.setReleaseuser(po.getReleaseuser());
			p.setImg(po.getImg());
			p.setImgtop(po.getImgtop());
			p.setPagetop(po.getPagetop());
			p.setScope(po.getScope());
			p.setStatus(0);// page设置为新建未发布状态
			pageDao.save(p);
			pageDao.updateURL(po.getId(), po.getUrl());
			po.setAuditstatus(4);// pageEdit设置为已通过状态
		}
		pageEditDao.save(po);
		if((po.isAudit() || po.isPass()) && enablelog)// 只记录提交时的日志
		{
			writeLogPage(po, 0, editid, editname);
		}
	}

	public int deletePageEdit(Long id)
	{
		pageEditDao.delete(id);
		pageDao.updateStatus(id, -1);
		return 1;
	}

	public void updateCategoryEdit(DsCmsCategoryEdit po, boolean writeCategory, boolean enablelog, String editid, String editname)
	{
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
			po.setAuditstatus(8);// CategoryEdit设置为已通过状态
		}
		categoryEditDao.update(po);
		if((po.isAudit() || po.isPass()) && enablelog)// 只记录提交时的日志
		{
			writeLogCategory(po, 1, editid, editname);
		}
	}

	public void updateRevokeCategoryEdit(DsCmsCategoryEdit po, boolean enablelog, String editid, String editname)
	{
		if(po.getStatus() == -1)
		{
			po.setStatus(1); // 修改
		}
		po.setAuditstatus(0); // 草稿
		categoryEditDao.update(po);
		if(enablelog)
		{
			po.setStatus(4); // 撤销
			writeLogCategory(po, 3, editid, editname);
		}
	}

	public void updatePageEdit(DsCmsPageEdit po, boolean writePage, boolean enablelog, String editid, String editname)
	{
		if(po.getScope() != 2)
		{
			po.setUrl("/a/" + po.getCategoryid() + "/" + po.getId() + ".html");
		}
		if(writePage)
		{
			DsCmsPage p = (DsCmsPage) pageDao.get(po.getId());
			boolean isSave = false;
			if(p == null)
			{
				p = new DsCmsPage();
				p.setId(po.getId());
				isSave = true;
			}
			p.setSiteid(po.getSiteid());
			p.setCategoryid(po.getCategoryid());
			p.setStatus(po.getStatus());
			p.setTitle(po.getTitle());
			p.setMetakeywords(po.getMetakeywords());
			p.setMetadescription(po.getMetadescription());
			p.setSummary(po.getSummary());
			p.setContent(po.getContent());
			p.setReleasetime(po.getReleasetime());
			p.setReleasesource(po.getReleasesource());
			p.setReleaseuser(po.getReleaseuser());
			p.setImg(po.getImg());
			p.setImgtop(po.getImgtop());
			p.setPagetop(po.getPagetop());
			p.setScope(po.getScope());
			if(isSave)
			{
				p.setStatus(0);// page设置为新建未发布状态
				pageDao.save(p);
				pageDao.updateURL(po.getId(), po.getUrl());
			}
			else
			{
				if(p.getStatus() != 0)
				{
					p.setStatus(1);// page设置为更新未发布状态
				}
				pageDao.update(p);
			}
			po.setAuditstatus(4);// pageEdit设置为已通过状态
		}
		pageEditDao.update(po);
		if((po.isAudit() || po.isPass()) && enablelog)// 只记录提交时的日志
		{
			writeLogPage(po, po.getStatus() == -1 ? 2 : 1, editid, editname);
		}
	}

	public void updateRevokePageEdit(DsCmsPageEdit po, boolean enablelog, String editid, String editname)
	{
		if(po.getStatus() == -1)
		{
			po.setStatus(1); // 修改
		}
		po.setAuditstatus(0); // 草稿
		pageEditDao.update(po);
		if(enablelog)
		{
			po.setStatus(4); // 撤销
			writeLogPage(po, 3, editid, editname);
		}
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
		DsCmsCategory c = (DsCmsCategory) categoryDao.get(id);
		po.setId(c.getId());
		po.setSiteid(c.getSiteid());
		po.setSummary(c.getSummary());
		po.setMetakeywords(c.getMetakeywords());
		po.setMetadescription(c.getMetadescription());
		po.setReleasesource(c.getReleasesource());
		po.setReleaseuser(c.getReleaseuser());
		po.setImg(c.getImg());
		po.setContent(c.getContent());
		po.setReleasetime(c.getReleasetime());
		po.setUrl(c.getUrl());
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

	public List<DsCmsSite> queryListSite(String own)
	{
		return siteDao.queryList(own);
	}

	public Page<DsCmsPageEdit> queryPagePageEdit(PageRequest pr)
	{
		return pageEditDao.queryPage(pr);
	}

	public List<DsCmsCategory> queryListCategory(long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		map.put("publishstatus", "true");
		return categoryDao.queryList(map);
	}

	private void writeLogPage(DsCmsPageEdit po, int action, String editid, String editname)
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
			log.setAuditstatus(action);
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

	private void writeLogCategory(DsCmsCategoryEdit po, int action, String editid, String editname)
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
			log.setAuditstatus(action);
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
