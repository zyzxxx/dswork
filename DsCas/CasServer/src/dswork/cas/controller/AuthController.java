/**
 * 获取认证信息
 */
package dswork.cas.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.cas.service.CasFactoryService;
import dswork.cas.model.IFunc;
import dswork.cas.model.IOrg;
import dswork.cas.model.ISystem;
import dswork.cas.model.IUser;
import dswork.web.MyRequest;

@Controller
@RequestMapping("/api")
public class AuthController
{
	private static com.google.gson.GsonBuilder builder = new com.google.gson.GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
	private static com.google.gson.Gson gson = builder.create();
	//static com.alibaba.fastjson.serializer.SerializeConfig mapping = new com.alibaba.fastjson.serializer.SerializeConfig();
	//static{mapping.put(java.util.Date.class, new com.alibaba.fastjson.serializer.SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));}
	private static String toJson(Object object)
	{
		System.out.println(gson.toJson(object));
		return gson.toJson(object);
		//return com.alibaba.fastjson.JSON.toJSONString(object, mapping);
	}
	//private static <T> T toBean(String json, Class<T> classOfT)
	//{
	//	return gson.fromJson(json, classOfT);
	//	//return com.alibaba.fastjson.JSON.parseObject(json, classOfT);
	//}
	
	@Autowired
	private CasFactoryService service;

	//////////////////////////////////////////////////////////////////////////////
	// 权限相关的方法
	//////////////////////////////////////////////////////////////////////////////
	/**
	 * 获取子系统信息
	 * @param systemAlias 系统标识
	 * @param systemPassword 系统访问密码
	 * @return ISystem
	 */
	@RequestMapping("/getSystem")
	public void getSystem(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		String systemAlias = req.getString("systemAlias");
		String systemPassword = req.getString("systemPassword");
		try
		{
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			if(service.isSystemExist(systemAlias, systemPassword))
			{
				ISystem m = service.getSystem(systemAlias);
				out.print(toJson(m));
			}
			out.print("");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		request.getSession().invalidate();
	}

	/**
	 * 获取用户有权限访问的子系统
	 * @param userAccount 用户帐号
	 * @return ISystem[]
	 */
	@RequestMapping("/getSystemByUser")
	public void getSystemByUser(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		String userAccount = req.getString("userAccount");
		try
		{
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			ISystem[] m = service.getSystemByUser(userAccount);
			out.print(toJson(m));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		request.getSession().invalidate();
	}

	/**
	 * 获取系统的功能结构
	 * @param systemAlias 系统标识
	 * @param systemPassword 系统访问密码
	 * @return IFunc[]
	 */
	@RequestMapping("/getFunctionBySystem")
	public void getFunctionBySystem(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		String systemAlias = req.getString("systemAlias");
		String systemPassword = req.getString("systemPassword");
		try
		{
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			if(service.isSystemExist(systemAlias, systemPassword))
			{
				IFunc[] m = service.getFuncBySystemAlias(systemAlias);
				out.print(toJson(m));
			}
			out.print("");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		request.getSession().invalidate();
	}

	/**
	 * 获取用户权限范围内的系统功能结构
	 * @param systemAlias 系统标识
	 * @param systemPassword 系统访问密码
	 * @param userAccount 用户帐号
	 * @return IFunc[]
	 */
	@RequestMapping("/getFunctionByUser")
	public void getFunctionByUser(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		String systemAlias = req.getString("systemAlias");
		String systemPassword = req.getString("systemPassword");
		String userAccount = req.getString("userAccount");
		try
		{
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			if(service.isSystemExist(systemAlias, systemPassword))
			{
				IFunc[] m = service.getFuncBySystemAliasAndAccount(systemAlias, userAccount);
				out.print(toJson(m));
			}
			out.print("");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		request.getSession().invalidate();
	}

	/**
	 * 获取岗位权限范围内的系统功能结构
	 * @param systemAlias 系统标识
	 * @param systemPassword 系统访问密码
	 * @param postId 岗位ID
	 * @return IFunc[]
	 */
	@RequestMapping("/getFunctionByPost")
	public void getFunctionByPost(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		String systemAlias = req.getString("systemAlias");
		String systemPassword = req.getString("systemPassword");
		String postId = req.getString("postId");
		try
		{
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			if(service.isSystemExist(systemAlias, systemPassword))
			{
				IFunc[] m = service.getFuncBySystemAliasAndPostid(systemAlias, postId);
				out.print(toJson(m));
			}
			out.print("");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		request.getSession().invalidate();
	}

	//////////////////////////////////////////////////////////////////////////////
	// 用户相关的方法
	//////////////////////////////////////////////////////////////////////////////
	/**
	 * @note 获取组织机构
	 * @param orgId 组织机构ID
	 * @return IOrg
	 */
	@RequestMapping("/getOrg")
	public void getOrg(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		String orgId = req.getString("orgId");
		try
		{
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			IOrg m = service.getOrgByOrgId(orgId);
			out.print(toJson(m));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		request.getSession().invalidate();
	}

	/**
	 * @note 获取下级组织机构(status:2单位,1部门,0岗位)
	 * @param orgPid 组织机构ID，为0则取顶级
	 * @return IOrg[]
	 */
	@RequestMapping("/queryOrgByOrgParent")
	public void queryOrgByOrgParent(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		String orgPid = req.getString("orgPid");
		try
		{
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			IOrg[] m = service.queryOrgByOrgPid(orgPid);
			out.print(toJson(m));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		request.getSession().invalidate();
	}

	/**
	 * @note 获取组织机构下的岗位
	 * @param orgId 组织机构ID
	 * @return IOrg[]
	 */
	@RequestMapping("/queryPostByOrg")
	public void queryPostByOrg(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		String orgId = req.getString("orgId");
		try
		{
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			IOrg[] arr = service.queryOrgByOrgPid(orgId);
			if (arr != null)
			{
				List<IOrg> newList = new ArrayList<IOrg>();
				for(IOrg m : arr)
				{
					if(m.getStatus() == 0)//岗位
					{
						newList.add(m);
					}
				}
				IOrg[] m = newList.toArray(new IOrg[newList.size()]);
				out.print(toJson(m));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		request.getSession().invalidate();
	}

	/**
	 * @note 获取指定用户的基本信息
	 * @param userAccount 用户帐号
	 * @return IUser
	 */
	@RequestMapping("/getUser")
	public void getUser(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		String userAccount = req.getString("userAccount");
		try
		{
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			IUser m = service.getUserByAccount(userAccount);
			out.print(toJson(m));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		request.getSession().invalidate();
	}

	/**
	 * @note 获取岗位下的所有用户
	 * @param postId 岗位ID
	 * @return IUser[]
	 */
	@RequestMapping("/queryUserByPost")
	public void queryUserByPost(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		String postId = req.getString("postId");
		try
		{
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			IUser[] m = service.queryUserByPostid(postId);
			out.print(toJson(m));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		request.getSession().invalidate();
	}

	/**
	 * @note 获取指定用户拥有的岗位
	 * @param userAccount 用户帐号
	 * @return IOrg[]
	 */
	@RequestMapping("/queryPostByUser")
	public void queryPostByUser(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		String userAccount = req.getString("userAccount");
		try
		{
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			IUser user = service.getUserByAccount(userAccount);
			if(user != null)
			{
				IOrg[] m = service.getPostByUserId(user.getId());
				out.print(toJson(m));
			}
			out.print("");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		request.getSession().invalidate();
	}
}
