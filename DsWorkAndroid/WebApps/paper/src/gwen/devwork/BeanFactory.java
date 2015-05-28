package gwen.devwork;

/**
 * 从ApplicationContext取得spring管理的类
 */
@SuppressWarnings("all")
public class BeanFactory
{
	/**
	 * 取得Spring托管的类
	 * @param name 托管类的Class
	 * @return Object
	 */
	public static Object getBean(Class name)
	{
		return org.springframework.web.context.ContextLoaderListener.getCurrentWebApplicationContext().getBean(name);
	}
	
	/**
	 * 取得Spring托管的类
	 * @param name 托管类的ID
	 * @return Object
	 */
	public static Object getBean(String name)
	{
		return org.springframework.web.context.ContextLoaderListener.getCurrentWebApplicationContext().getBean(name);
	}
}
