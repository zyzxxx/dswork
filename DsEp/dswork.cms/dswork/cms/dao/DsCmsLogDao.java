/**
 * 日志Dao
 */
package dswork.cms.dao;

import org.springframework.stereotype.Repository;
import dswork.core.db.BaseDao;
import dswork.core.util.UniqueId;
import dswork.cms.model.DsCmsCategoryEdit;
import dswork.cms.model.DsCmsLog;
import dswork.cms.model.DsCmsPageEdit;

@Repository
@SuppressWarnings("all")
public class DsCmsLogDao extends BaseDao<DsCmsLog, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsCmsLogDao.class;
	}

	@Override
	@Deprecated
	public int delete(Long id)
	{
		return 0;
	}

	@Override
	@Deprecated
	public int update(DsCmsLog id)
	{
		return 0;
	}

	public void saveForEditPage(DsCmsPageEdit po, int action, String editid, String editname)
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
		save(log);
	}

	public void saveForEditCategory(DsCmsCategoryEdit po, int action, String editid, String editname)
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
		save(log);
	}

	public void saveForAuditPage(DsCmsPageEdit po, int action)
	{
		DsCmsLog log = new DsCmsLog();
		log.setId(UniqueId.genId());
		log.setSiteid(po.getSiteid());
		log.setCategoryid(po.getCategoryid());
		log.setPageid(po.getId());
		log.setAuditid(po.getAuditid());
		log.setAuditmsg(po.getMsg());
		log.setAuditname(po.getAuditname());
		log.setAudittime(po.getAudittime());
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
		save(log);
	}

	public void saveForAuditCategory(DsCmsCategoryEdit po, int action)
	{
		DsCmsLog log = new DsCmsLog();
		log.setId(UniqueId.genId());
		log.setSiteid(po.getSiteid());
		log.setCategoryid(po.getId());
		log.setAuditid(po.getAuditid());
		log.setAuditmsg(po.getMsg());
		log.setAuditname(po.getAuditname());
		log.setAudittime(po.getAudittime());
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
		save(log);
	}
}