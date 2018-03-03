/**
 * 采编审核Service
 */
package dswork.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.cms.dao.DsCmsAuditCategoryDao;
import dswork.cms.dao.DsCmsAuditPageDao;
import dswork.cms.dao.DsCmsCategoryDao;
import dswork.cms.dao.DsCmsLogDao;
import dswork.cms.dao.DsCmsPageDao;
import dswork.cms.dao.DsCmsPermissionDao;
import dswork.cms.dao.DsCmsSiteDao;
import dswork.cms.model.DsCmsAuditCategory;
import dswork.cms.model.DsCmsAuditPage;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsLog;
import dswork.cms.model.DsCmsPage;
import dswork.cms.model.DsCmsPermission;
import dswork.cms.model.DsCmsSite;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;
import dswork.core.util.UniqueId;

@Service
public class DsCmsEditService
{
	@Autowired
	private DsCmsAuditPageDao auditPageDao;
	@Autowired
	private DsCmsAuditCategoryDao auditCategoryDao;
	@Autowired
	private DsCmsLogDao logDao;
	@Autowired
	private DsCmsPermissionDao permissionDao;
	@Autowired
	private DsCmsCategoryDao categoryDao;
	@Autowired
	private DsCmsSiteDao siteDao;
	@Autowired
	private DsCmsPageDao pageDao;

	public int saveAuditPage(DsCmsAuditPage po, boolean isEnablelog, String editid, String editname)
	{
		auditPageDao.save(po);
		if(po.getScope() != 2) // 不为外链
		{
			po.setUrl("/a/" + po.getCategoryid() + "/" + po.getId() + ".html");
			auditPageDao.update(po);
		}
		if(po.isAudit() && isEnablelog)
		{
			writeLogPage(po, editid, editname);
		}
		return 1;
	}

	public int deleteAuditPage(Long id)
	{
		return auditPageDao.delete(id);
	}

	public int updateAuditCategory(DsCmsAuditCategory po, boolean isEnablelog, String editid, String editname)
	{
		if(po.isAudit() && isEnablelog)
		{
			writeLogCategory(po, editid, editname);
		}
		return auditCategoryDao.update(po);
	}

	public int updateRevokeAuditCategory(DsCmsAuditCategory po, boolean isEnablelog, String editid, String editname)
	{
		if(po.getStatus() == -1)
		{
			po.setStatus(1); // 修改
		}
		po.setAuditstatus(0); // 草稿
		auditCategoryDao.update(po);
		po.setStatus(4); // 撤销
		if(isEnablelog)
		{
			writeLogCategory(po, editid, editname);
		}
		return 1;
	}

	public int updateAuditPage(DsCmsAuditPage po, boolean isEnablelog, String editid, String editname)
	{
		if(po.isAudit() && isEnablelog)
		{
			writeLogPage(po, editid, editname);
		}
		if(po.getScope() != 2)
		{
			po.setUrl("/a/" + po.getCategoryid() + "/" + po.getId() + ".html");
		}
		return auditPageDao.update(po);
	}

	public int updateRevokeAuditPage(DsCmsAuditPage po, boolean isEnablelog, String editid, String editname)
	{
		if(po.getStatus() == -1)
		{
			po.setStatus(1); // 修改
		}
		po.setAuditstatus(0); // 草稿
		auditPageDao.update(po);
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

	public DsCmsPermission getPermission(Long siteid, String account)
	{
		return permissionDao.get(siteid, account);
	}

	public DsCmsAuditCategory saveAuditCategory(Long id)
	{
		DsCmsAuditCategory po = new DsCmsAuditCategory();
		DsCmsCategory _po = (DsCmsCategory) categoryDao.get(id);
		if(_po.getScope() != 0) // 非列表
		{
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
			po.setAuditstatus(DsCmsAuditCategory.EDIT);// 编辑状态
			po.setStatus(0);// 初始设置为新增状态
			auditCategoryDao.save(po);
		}
		return po;
	}

	public DsCmsAuditCategory getAuditCategory(Long id)
	{
		return (DsCmsAuditCategory) auditCategoryDao.get(id);
	}

	public DsCmsAuditPage getAuditPage(Long id)
	{
		return (DsCmsAuditPage) auditPageDao.get(id);
	}

	public DsCmsPage getPage(Long id)
	{
		return (DsCmsPage) pageDao.get(id);
	}

	public List<DsCmsSite> queryListSite(Map<String, Object> map)
	{
		return permissionDao.queryListSite(map);
	}

	@SuppressWarnings("unchecked")
	public Page<DsCmsAuditPage> queryPageAuditPage(PageRequest pr)
	{
		return auditPageDao.queryPage(pr);
	}

	@SuppressWarnings("unchecked")
	public List<DsCmsCategory> queryListCategory(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return categoryDao.queryList(map);
	}

	private void writeLogPage(DsCmsAuditPage po, String editid, String editname)
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

	private void writeLogCategory(DsCmsAuditCategory po, String editid, String editname)
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
