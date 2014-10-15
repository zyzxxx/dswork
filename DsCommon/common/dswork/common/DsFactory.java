package dswork.common;

public class DsFactory
{
	static DsCommonFlowFactory f = null;
	private static DsCommonFlowFactory init()
	{
		if(f == null)
		{
			f = new DsCommonFlowFactory();
		}
		return f;
	}
	public static DsCommonFlowFactory getFlow()
	{
		return init();
	}
	
	

	static DsCommonFactory d = null;
	private static DsCommonFactory initd()
	{
		if(d == null)
		{
			d = new DsCommonFactory();
		}
		return d;
	}
	public static DsCommonFactory getDict()
	{
		return initd();
	}
}
