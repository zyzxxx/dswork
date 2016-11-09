package dswork.common;

import dswork.common.service.DsCommonIFlowService;
import dswork.spring.BeanFactory;

public class DsFactoryService
{
	private static DsCommonIFlowService service = null;

	private static void init()
	{
		if(service == null)
		{
			service = (DsCommonIFlowService) BeanFactory.getBean("dsCommonIFlowService");
		}
	}
	/**
	 * 相当于service，本身提供事务支持。
	 */
	public static DsCommonIFlowService getFlow()
	{
		init();
		return service;
	}
}
