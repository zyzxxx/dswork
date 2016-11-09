package dswork.common;

import dswork.common.util.DsCommonIFlowUtil;

public class DsFactoryUtil
{
	private static DsCommonIFlowUtil d = new DsCommonIFlowUtil();
	public static DsCommonIFlowUtil getFlow()
	{
		return d;
	}
}
