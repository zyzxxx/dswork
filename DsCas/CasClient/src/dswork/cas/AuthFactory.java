/**
 * 调用webservice的服务类
 */
package dswork.cas;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;

import dswork.cas.http.HttpUtil;
import dswork.cas.model.IFunc;
import dswork.cas.model.IOrg;
import dswork.cas.model.ISystem;
import dswork.cas.model.IUser;

public class AuthFactory
{
	static com.google.gson.GsonBuilder builder = new com.google.gson.GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
	static com.google.gson.Gson gson = builder.create();
	static String url = "";
	static Logger log = LoggerFactory.getLogger(CasFilter.class.getName());
	
	//////////////////////////////////////////////////////////////////////////////
	// 权限相关的方法
	//////////////////////////////////////////////////////////////////////////////
	/**
	 * 获取子系统信息
	 * @param systemAlias 系统标识
	 * @param systemPassword 系统访问密码
	 * @return ISystem
	 */
	public static ISystem getSystem(String systemAlias, String systemPassword)
	{
		String u = url + "/getSystem.htm?systemAlias=" + systemAlias + "&systemPassword=" + systemPassword;
		String v = new HttpUtil().create(u, u.startsWith("https:")).connect().trim();
		if(log.isDebugEnabled())
		{
			log.debug("AuthFactory:url=" + u + ", json:" + v);
		}
		ISystem m = gson.fromJson(v, ISystem.class);
		return m;
	}
	/**
	 * 获取用户有权限访问的子系统
	 * @param userAccount 用户帐号
	 * @return ISystem[]
	 */
	public static ISystem[] getSystemByUser(String userAccount)
	{
		String u = url + "/getSystemByUser.htm?userAccount=" + userAccount;
		String v = new HttpUtil().create(u, u.startsWith("https:")).connect().trim();
		if(log.isDebugEnabled())
		{
			log.debug("AuthFactory:url=" + u + ", json:" + v);
		}
		List<ISystem> list = gson.fromJson(v, new TypeToken<List<ISystem>>(){}.getType());
		return list.toArray(new ISystem[list.size()]);
	}
	
	/**
	 * 获取系统的功能结构
	 * @param systemAlias 系统标识
	 * @param systemPassword 系统访问密码
	 * @return IFunc[]
	 */
	public static IFunc[] getFunctionBySystem(String systemAlias, String systemPassword)
	{
		String u = url + "/getFunctionBySystem.htm?systemAlias=" + systemAlias + "&systemPassword=" + systemPassword;
		String v = new HttpUtil().create(u, u.startsWith("https:")).connect().trim();
		if(log.isDebugEnabled())
		{
			log.debug("AuthFactory:url=" + u + ", json:" + v);
		}
		List<IFunc> list = gson.fromJson(v, new TypeToken<List<IFunc>>(){}.getType());
		return list.toArray(new IFunc[list.size()]);
	}
	
	/**
	 * 获取用户权限范围内的系统功能结构
	 * @param systemAlias 系统标识
	 * @param systemPassword 系统访问密码
	 * @param userAccount 用户帐号
	 * @return IFunc[]
	 */
	public static IFunc[] getFunctionByUser(String systemAlias, String systemPassword, String userAccount)
	{
		String u = url + "/getFunctionByUser.htm?systemAlias=" + systemAlias + "&systemPassword=" + systemPassword + "&userAccount=" + userAccount;
		String v = new HttpUtil().create(u, u.startsWith("https:")).connect().trim();
		if(log.isDebugEnabled())
		{
			log.debug("AuthFactory:url=" + u + ", json:" + v);
		}
		List<IFunc> list = gson.fromJson(v, new TypeToken<List<IFunc>>(){}.getType());
		return list.toArray(new IFunc[list.size()]);
	}
	
	/**
	 * 获取岗位权限范围内的系统功能结构
	 * @param systemAlias 系统标识
	 * @param systemPassword 系统访问密码
	 * @param postId 岗位ID
	 * @return IFunc[]
	 */
	public static IFunc[] getFunctionByPost(String systemAlias, String systemPassword, String postId)
	{
		String u = url + "/getFunctionByPost.htm?systemAlias=" + systemAlias + "&systemPassword=" + systemPassword + "&postId=" + postId;
		String v = new HttpUtil().create(u, u.startsWith("https:")).connect().trim();
		if(log.isDebugEnabled())
		{
			log.debug("AuthFactory:url=" + u + ", json:" + v);
		}
		List<IFunc> list = gson.fromJson(v, new TypeToken<List<IFunc>>(){}.getType());
		return list.toArray(new IFunc[list.size()]);
	}

	//////////////////////////////////////////////////////////////////////////////
	// 用户相关的方法
	//////////////////////////////////////////////////////////////////////////////
	/**
	 * @note 获取组织机构
	 * @param orgId 组织机构ID
	 * @return IOrg
	 */
	public static IOrg getOrg(String orgId)
	{
		String u = url + "/getOrg.htm?orgId=" + orgId;
		String v = new HttpUtil().create(u, u.startsWith("https:")).connect().trim();
		if(log.isDebugEnabled())
		{
			log.debug("AuthFactory:url=" + u + ", json:" + v);
		}
		IOrg m = gson.fromJson(v, IOrg.class);
		return m;
	}
	
	/**
	 * @note 获取下级组织机构(status:2单位,1部门,0岗位)
	 * @param orgPid 组织机构ID，为0则取顶级
	 * @return IOrg[]
	 */
	public static IOrg[] queryOrgByOrgParent(String orgPid)
	{
		String u = url + "/queryOrgByOrgParent.htm?orgPid=" + orgPid;
		String v = new HttpUtil().create(u, u.startsWith("https:")).connect().trim();
		if(log.isDebugEnabled())
		{
			log.debug("AuthFactory:url=" + u + ", json:" + v);
		}
		List<IOrg> list = gson.fromJson(v, new TypeToken<List<IOrg>>(){}.getType());
		return list.toArray(new IOrg[list.size()]);
	}

	/**
	 * @note 获取组织机构下的岗位
	 * @param orgId 组织机构ID
	 * @return IOrg[]
	 */
	public static IOrg[] queryPostByOrg(String orgId)
	{
		String u = url + "/queryPostByOrg.htm?orgId=" + orgId;
		String v = new HttpUtil().create(u, u.startsWith("https:")).connect().trim();
		if(log.isDebugEnabled())
		{
			log.debug("AuthFactory:url=" + u + ", json:" + v);
		}
		List<IOrg> list = gson.fromJson(v, new TypeToken<List<IOrg>>(){}.getType());
		return list.toArray(new IOrg[list.size()]);
	}
	
	/**
	 * @note 获取指定用户的基本信息
	 * @param userAccount 用户帐号
	 * @return IUser
	 */
	public static IUser getUser(String userAccount)
	{
		String u = url + "/getUser.htm?userAccount=" + userAccount;
		String v = new HttpUtil().create(u, u.startsWith("https:")).connect().trim();
		if(log.isDebugEnabled())
		{
			log.debug("AuthFactory:url=" + u + ", json:" + v);
		}
		IUser m = gson.fromJson(v, IUser.class);
		return m;
	}
	
	/**
	 * @note 获取岗位下的所有用户
	 * @param postId 岗位ID
	 * @return IUser[]
	 */
	public static IUser[] queryUserByPost(String postId)
	{
		String u = url + "/queryUserByPost.htm?postId=" + postId;
		String v = new HttpUtil().create(u, u.startsWith("https:")).connect().trim();
		if(log.isDebugEnabled())
		{
			log.debug("AuthFactory:url=" + u + ", json:" + v);
		}
		List<IUser> list = gson.fromJson(v, new TypeToken<List<IUser>>(){}.getType());
		return list.toArray(new IUser[list.size()]);
	}
	
	/**
	 * @note 获取指定用户拥有的岗位
	 * @param userAccount 用户帐号
	 * @return IOrg[]
	 */
	public static IOrg[] queryPostByUser(String userAccount)
	{
		String u = url + "/queryPostByUser.htm?userAccount=" + userAccount;
		String v = new HttpUtil().create(u, u.startsWith("https:")).connect().trim();
		if(log.isDebugEnabled())
		{
			log.debug("AuthFactory:url=" + u + ", json:" + v);
		}
		List<IOrg> list = gson.fromJson(v, new TypeToken<List<IOrg>>(){}.getType());
		return list.toArray(new IOrg[list.size()]);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private static final String SYSTEM_PROPERTIES_PATH = "/config/config.properties";
	private static Properties SYSTEM_PROPERTIES;
	static
	{
		setSystemProperties(SYSTEM_PROPERTIES_PATH);
		url = getToString("dswork.cas.api", "");
	}

	private static void setSystemProperties(String path)
	{
		SYSTEM_PROPERTIES = new Properties();
		InputStream stream = AuthFactory.class.getResourceAsStream(path);
		if (stream != null)
		{
			try
			{
				SYSTEM_PROPERTIES.load(stream);
			}
			catch (Exception e)
			{
				System.out.println();
			}
			finally
			{
				try
				{
					stream.close();
				}
				catch (IOException ioe)
				{
					System.out.println(SYSTEM_PROPERTIES_PATH + "关闭流失败");
				}
			}
		}
		else
		{
			System.out.println(SYSTEM_PROPERTIES_PATH + "无法加载");
		}
		System.getProperties();
	}

	/**
	 * 获得系统属性配置信息，如果没有则返回null
	 * @param name 属性名
	 * @return String
	 */
	private static final String getStringProperty(String name)
	{
		if (SYSTEM_PROPERTIES != null)
		{
			String str = SYSTEM_PROPERTIES.getProperty(name);
			if(str != null)
			{
				return String.valueOf(str).trim();
			}
		}
		return null;
	}
	
	/**
	 * 获得系统属性配置信息，如果没有则返回默认值(长整型)
	 * @param name 属性名
	 * @param defaultValue 默认值
	 * @return long
	 */
	public static final long getToLong(String name, long defaultValue)
	{
		try
		{
			return Long.parseLong(getStringProperty(name));
		}
		catch (Exception e)
		{
			return defaultValue;
		}
	}

	/**
	 * 获得系统属性配置信息，如果没有则返回默认值(字符串类型)
	 * @param name 属性名
	 * @param defaultValue 默认值
	 * @return String
	 */
	public static final String getToString(String name, String defaultValue)
	{
		try
		{
			String str = getStringProperty(name);
			if (str != null)
			{
				return str;
			}
		}
		catch (Exception e)
		{
		}
		return defaultValue;
	}
	
	/**
	 * 获得系统属性配置信息，如果没有则返回默认值("true"、"false")
	 * @param name 属性名
	 * @param defaultValue 默认值
	 * @return boolean
	 */
	public static final boolean getToBoolean(String name, boolean defaultValue)
	{
		try
		{
			String str = String.valueOf(getStringProperty(name));
			if(str.equals("true"))
			{
				return Boolean.TRUE;
			}
			else if(str.equals("false"))
			{
				return Boolean.FALSE;
			}
		}
		catch (Exception e)
		{
		}
		return defaultValue;
	}
}
