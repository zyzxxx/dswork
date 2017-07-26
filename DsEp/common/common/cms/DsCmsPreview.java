/**
 * CMSService
 */
package common.cms;

import javax.servlet.http.HttpServletRequest;
import dswork.cms.dao.DsCmsDao;

public class DsCmsPreview extends CmsFactory
{
	private static DsCmsDao dao = null;

	@Override
	protected DsCmsDao getDao()
	{
		return dao;
	}

	@Override
	protected void init()
	{
		dao = (DsCmsDao) dswork.spring.BeanFactory.getBean("dsCmsPreviewDao");
	}

	public DsCmsPreview(HttpServletRequest request)
	{
		super(request);
	}
}
