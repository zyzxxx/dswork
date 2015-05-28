package admin.dao;

import gwen.devwork.BaseDao;

import org.springframework.stereotype.Repository;

import admin.entity.PaperImage;

@Repository("paperImageDao")
public class PaperImageDao extends BaseDao<PaperImage,Long>
{
	@Override
	public Class getEntityClass()
	{
		return PaperImage.class;
	}
	
	public void updSortById(PaperImage po)
	{
		this.getSqlSession().update(getStatementName("updSortById"), po);
	}
}
