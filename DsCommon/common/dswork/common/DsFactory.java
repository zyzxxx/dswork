package dswork.common;

public class DsFactory
{
	static DsCommonFlowFactory f = null;
	public static DsCommonFlowFactory getFlow()
	{
		if(f == null)
		{
			f = new DsCommonFlowFactory();
		}
		return f;
	}
	
	static DsCommonFactory d = null;
	public static DsCommonFactory getDict()
	{
		if(d == null)
		{
			d = new DsCommonFactory();
		}
		return d;
	}
}
