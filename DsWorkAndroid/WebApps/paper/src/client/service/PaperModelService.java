package client.service;

import gwen.devwork.BaseDao;
import gwen.devwork.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import client.dao.PaperModelDao;
import client.entity.PaperModel;

@Service("clientPaperModelService")
public class PaperModelService extends BaseService<PaperModel,Long>
{
	@Autowired
	private PaperModelDao paperModelDao;

	@Override
	public BaseDao<PaperModel,Long> getEntityDao() {
		return paperModelDao;
	}
}
