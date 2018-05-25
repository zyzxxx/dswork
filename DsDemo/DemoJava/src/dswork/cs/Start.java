package dswork.cs;

/**
 * 本地方式启动应用
 */
public class Start extends MyStart
{
	public static void main(String[] args)
	{
		MyStart.myLog4jLoad();
		MyStart.mySpringLoad();
		// 执行需要的应用程序
		ExecuteProject.execute(args);
		
		// 以下代码用于防止main进程执行后结束，可用于捕获监听输入，输入quit或exit可退出
		MyStart.waitForQuit();
	}
}
