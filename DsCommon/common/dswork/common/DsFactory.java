package dswork.common;

public class DsFactory
{
	private static DsCommonFactoryFlow f = new DsCommonFactoryFlow();
	public static DsCommonFactoryFlow getFlow()
	{
		return f;
	}
	
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
}
