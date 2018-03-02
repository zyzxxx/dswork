/**
 * CMSService
 */
package common.cms;

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

	public DsCmsPreview(Long siteid)
	{
		super(siteid);
	}
}
