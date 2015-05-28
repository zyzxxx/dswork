package client.service;

import gwen.devwork.BaseDao;
import gwen.devwork.BaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import client.dao.PaperImageDao;
import client.entity.PaperImage;


@Service("clientPaperImageService")
public class PaperImageService extends BaseService<PaperImage,Long>
{
	@Autowired
	private PaperImageDao dao;

	@Override
	public BaseDao<PaperImage,Long> getEntityDao() {
		return dao;
	}
}
