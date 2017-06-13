package dswork.flow;

import dswork.core.util.FileUtil;

public class MyFlowTest
{
	public static void main(String[] args)
	{
		try
		{
			MyFlow flow = new MyFlow(FileUtil.readFile(MyFlowTest.class.getClass().getResource("/").getPath() + "dswork/flow/c.txt", "UTF-8"));
			flow.prettyPrint(true);
			System.out.println(flow.toSvg());
			System.out.println(flow.toXml());
		}
		catch(Exception e)
		{
			// TODO: handle exception
		}
	}
}
