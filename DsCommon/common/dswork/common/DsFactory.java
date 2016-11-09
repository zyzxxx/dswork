package dswork.common;

import dswork.common.service.DsCommonIFlowService;
import dswork.spring.BeanFactory;

/**
 * 相当于dao，本身没有事务支持。
 */
public class DsFactory
{
	private static DsCommonFactoryDict d = new DsCommonFactoryDict();
	public static DsCommonFactoryDict getDict()
	{
		return d;
	}

	private static DsCommonFactoryOrg o = new DsCommonFactoryOrg();
	public static DsCommonFactoryOrg getOrg()
	{
		return o;
	}
	
	private static DsCommonIFlowService service = null;
	/**
	 * 相当于service，本身提供事务支持。
	 */
	public static DsCommonIFlowService getFlow()
	{
		if(service == null)
		{
			service = (DsCommonIFlowService) BeanFactory.getBean("dsCommonIFlowService");
		}
		return service;
	}
}
