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
}
