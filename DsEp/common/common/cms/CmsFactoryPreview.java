/**
 * CMSService
 */
package common.cms;

public class CmsFactoryPreview extends CmsFactory
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

	public CmsFactoryPreview(Long siteid)
	{
		super(siteid);
	}
}
