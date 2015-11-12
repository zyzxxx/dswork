/**
 * 调用webservice的服务类
 */
package dswork.cas;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
	static com.google.gson.Gson gson = CasFilter.getGson();
	static String url = "";
	static Logger log = LoggerFactory.getLogger(CasFilter.class.getName());
	
	private static String getAPI()
	{
		return CasFilter.getSsoURL();
	}
	private static String getName()
	{
		return CasFilter.getSsoName();
	}
	private static String getPwd()
	{
		String v = CasFilter.getSsoPassword();
		return pwdMd5(v);
	}
	private static String pwdMd5(String str)
	{
		if(str != null)
		{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
			String v = sdf.format(cal.getTime()) + str;
			StringBuilder sb = new StringBuilder();
			try
			{
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] digest = md.digest(v.getBytes("UTF-8"));
				String stmp = "";
				for(int n = 0; n < digest.length; n++)
				{
					stmp = (Integer.toHexString(digest[n] & 0XFF));
					sb.append((stmp.length() == 1) ? "0" : "").append(stmp);
				}
				return sb.toString().toUpperCase(Locale.ENGLISH);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				sb = null;
			}
		}
		return "";
	}
	
	
	
	//////////////////////////////////////////////////////////////////////////////
	// 权限相关的方法
	//////////////////////////////////////////////////////////////////////////////
	/**
	 * 获取子系统信息
	 * @param systemPassword 系统访问密码
	 * @return ISystem
	 */
	public static ISystem getSystem()
	{
		String u = getAPI() + "/getSystem?name=" + getName() + "&pwd=" + getPwd();
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
		String u = getAPI() + "/getSystemByUser?name=" + getName() + "&pwd=" + getPwd() + "&userAccount=" + userAccount;
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
	public static IFunc[] getFunctionBySystem()
	{
		String u = getAPI() + "/getFunctionBySystem?name=" + getName() + "&pwd=" + getPwd();
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
	public static IFunc[] getFunctionByUser(String userAccount)
	{
		String u = getAPI() + "/getFunctionByUser?name=" + getName() + "&pwd=" + getPwd() + "&userAccount=" + userAccount;
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
	public static IFunc[] getFunctionByPost(String postId)
	{
		String u = getAPI() + "/getFunctionByPost?name=" + getName() + "&pwd=" + getPwd() + "&postId=" + postId;
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
		String u = getAPI() + "/getOrg?name=" + getName() + "&pwd=" + getPwd() + "&orgId=" + orgId;
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
		String u = getAPI() + "/queryOrgByOrgParent?name=" + getName() + "&pwd=" + getPwd() + "&orgPid=" + orgPid;
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
		String u = getAPI() + "/queryPostByOrg?name=" + getName() + "&pwd=" + getPwd() + "&orgId=" + orgId;
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
		String u = getAPI() + "/getUser?name=" + getName() + "&pwd=" + getPwd() + "&userAccount=" + userAccount;
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
		String u = getAPI() + "/queryUserByPost?name=" + getName() + "&pwd=" + getPwd() + "&postId=" + postId;
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
		String u = getAPI() + "/queryPostByUser?name=" + getName() + "&pwd=" + getPwd() + "&userAccount=" + userAccount;
		String v = new HttpUtil().create(u, u.startsWith("https:")).connect().trim();
		if(log.isDebugEnabled())
		{
			log.debug("AuthFactory:url=" + u + ", json:" + v);
		}
		List<IOrg> list = gson.fromJson(v, new TypeToken<List<IOrg>>(){}.getType());
		return list.toArray(new IOrg[list.size()]);
	}
}
