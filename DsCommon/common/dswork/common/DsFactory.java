package dswork.common;

/**
 * 相当于dao，本身没有事务支持。
 */
public class DsFactory
{
	private static DsCommonFactoryFlow u = new DsCommonFactoryFlow();
	/**
	 * 相当于dao，本身没有事务支持。
	 */
	public static DsCommonFactoryFlow getFlow()
	{
		return u;
	}

	
	private static DsCommonFactoryDict d = new DsCommonFactoryDict();
	/**
	 * 相当于dao，本身没有事务支持。
	 */
	public static DsCommonFactoryDict getDict()
	{
		return d;
	}

	private static DsCommonFactoryOrg o = new DsCommonFactoryOrg();
	/**
	 * 相当于dao，本身没有事务支持。
	 */
	public static DsCommonFactoryOrg getOrg()
	{
		return o;
	}
}
