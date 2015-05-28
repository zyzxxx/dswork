package admin.dao;

import gwen.devwork.BaseDao;

import org.springframework.stereotype.Repository;

import admin.entity.PaperModel;

@Repository("paperModelDao")
public class PaperModelDao extends BaseDao<PaperModel,Long>
{
	@Override
	public Class getEntityClass()
	{
		return PaperModel.class;
	}
}
