package client.dao;

import gwen.devwork.BaseDao;

import org.springframework.stereotype.Repository;

import client.entity.PaperModel;

@Repository("clientPaperModelDao")
public class PaperModelDao extends BaseDao<PaperModel,Long>
{
	@Override
	public Class getEntityClass()
	{
		return PaperModel.class;
	}
}
