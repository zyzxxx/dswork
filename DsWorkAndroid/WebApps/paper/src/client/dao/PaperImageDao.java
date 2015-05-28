package client.dao;

import gwen.devwork.BaseDao;

import org.springframework.stereotype.Repository;

import client.entity.PaperImage;


@Repository("clientPaperImageDao")
public class PaperImageDao extends BaseDao<PaperImage,Long>
{
	@Override
	public Class getEntityClass()
	{
		return PaperImage.class;
	}
}
