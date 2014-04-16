package dswork.cas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dswork.cas.model.IFunc;
import dswork.cas.model.IRes;
import dswork.cas.service.CasClientWebService;
import dswork.core.util.EnvironmentUtil;

@SuppressWarnings("unchecked")
public class CasCheckFilter implements Filter
{
	private final static String KEY_SESSIONUSER = "cas.client.user";//存放在session中的用户账号的key
	
	private final static String KEY_SESSIONFUNC = "dswork.cas.func";//存放在session中用户岗位信息的key
	
	private final static String KEY_SYSTEM_REFRESH = "dswork.cas.system.refresh";// 当前系统的标识
	private final static String KEY_SYSTEM_ALIAS = "dswork.cas.system.alias";// 当前系统的标识
	private final static String KEY_SYSTEM_PASSWORD = "dswork.cas.system.password";// 当前系统的标识
	private final static String KEY_PAGE_NOACCESS = "dswork.cas.page.noAccess";// 不能访问时的提示页面
	private final static String KEY_PAGE_IGNORE = "dswork.cas.page.ignore";// 规则范围内不需要验证的页面
	private final static String KEY_CHECK_FIX = "dswork.cas.check.fix";// 以逗号隔开需要过滤的后缀名,*指全部页面,空指不过滤
	private final static String KEY_CHECK_PARAM = "dswork.cas.check.param";// 是否需要验证参数true验证，false不验证

	private static Long SYSTEM_REFRESH;//当前系统的全部权限缓存更新间隔
	private static String SYSTEM_ALIAS;//当前系统的标识
	private static String SYSTEM_PASSWORD;//当前系统的标识
	private static String PAGE_NOACCESS;//跳转到无权限提示页面
	private static Set<String> PAGE_IGNORE = new HashSet<String>();// 无需验证页面
	private static String CHECK_FIX;//需要过滤的后缀名
	private static boolean isCheckAllFix;//是否需要过滤后缀名
	private static boolean isCheckParam;//是否需要验证参数

	private static Map<String, String> resUrlMap = new HashMap<String, String>();// 只存放url作为key值
	private static Map<String, IRes> resMap = new HashMap<String, IRes>();// 系统中所有配置的res信息
	private static long refreshTime = 0L;

	private static void splitToSet(String str, Set<String> set)
	{
		if (str != null)
		{
			String[] values = str.trim().split(",");
			for (String value : values)
			{
				if(value.length() > 0)
				{
					set.add(value.trim());
				}
			}
		}
	}
	
	private static void refreshSystemFunc()
	{
		if(System.currentTimeMillis() > refreshTime)// 初始时refreshPermissionTime为0，肯定会执行
		{
			boolean isFailure = true;
			try
			{
				resUrlMap.clear();
				resMap.clear();
				IFunc[] funcArray = CasClientWebService.getDsworkCasWebService().getFunctionBySystem(SYSTEM_ALIAS, SYSTEM_PASSWORD);// 初始化该系统已配置的全部功能
				if(funcArray != null)// 读得到数据，结果集也可以为0
				{
					if(isCheckParam)// 需要权限参数时，才把res存放起来
					{
						for(IFunc o : funcArray)// 将系统里的所有资源初始化到map中
						{
							if(o == null || o.getRes() == null || o.getRes().size() == 0)
							{
								continue;
							}
							for(IRes r : o.getRes())
							{
								resMap.put(String.valueOf(r.getId()), r);// 把res存放起来
								if(resUrlMap.get(r.getUrl()) == null)
								{
									resUrlMap.put(r.getUrl(), "");// 放入需要验证的url
								}
							}
						}
					}
					else
					{
						for(IFunc o : funcArray)// 将系统里的所有资源初始化到map中
						{
							if(o == null || o.getRes() == null || o.getRes().size() == 0)
							{
								continue;
							}
							for(IRes r : o.getRes())
							{
								if(resUrlMap.get(r.getUrl()) == null)
								{
									resUrlMap.put(r.getUrl(), "");// 放入需要验证的url
								}
							}
						}
					}
					refreshTime += System.currentTimeMillis() + SYSTEM_REFRESH.longValue();
					isFailure = false;
				}
			}
			catch(Exception ex)
			{
			}
			if(isFailure)//funcArray为null或异常（cxf为null时抛出异常）
			{
				resUrlMap.clear();
				resMap.clear();
				resUrlMap.put("nullkey", "");// 读取出错，随便放一个nullkey，让它不为空，避免读不到信息时权限验证被越
			}
		}
	}
	
	public void init(FilterConfig config) throws ServletException
	{
		SYSTEM_REFRESH = EnvironmentUtil.getToLong(KEY_SYSTEM_REFRESH, 60000);// 刷新间隔1000(1秒)|60000(1分钟)|3600000(1小时)|86400000(1天)
		SYSTEM_ALIAS = EnvironmentUtil.getToString(KEY_SYSTEM_ALIAS, "");
		SYSTEM_PASSWORD = EnvironmentUtil.getToString(KEY_SYSTEM_PASSWORD, "");
		PAGE_NOACCESS = EnvironmentUtil.getToString(KEY_PAGE_NOACCESS, "");
		splitToSet(EnvironmentUtil.getToString(KEY_PAGE_IGNORE, ""), PAGE_IGNORE);
		CHECK_FIX = EnvironmentUtil.getToString(KEY_CHECK_FIX, "htm,jsp").trim();
		isCheckParam = EnvironmentUtil.getToBoolean(KEY_CHECK_PARAM, false);
		if(CHECK_FIX.length() > 0)
		{
			CHECK_FIX = "," + CHECK_FIX + ",";
			isCheckAllFix = false;
		}
		else
		{
			isCheckAllFix = true;
		}
	}
	
	public void destroy()
	{
	}
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		String relativeURI = request.getRequestURI().trim();// 相对地址
		if(request.getContextPath().length() > 0)
		{
			relativeURI = relativeURI.replaceFirst(request.getContextPath(), "");
		}
		if(isIgnoreURI(relativeURI))// 是否不用判断
		{
			chain.doFilter(request, response);
			return;
		}
		// 取得当前用户账号
		String userAccount = String.valueOf(request.getSession().getAttribute(KEY_SESSIONUSER)).trim();
		if(userAccount.equals("null"))
		{
			userAccount = "";
		}
		if(isAccess(request, userAccount, relativeURI))// 判断是否能访问该页面
		{
			chain.doFilter(request, response);// 有权限访问
			return;
		}
		// 没权限
		response.sendRedirect(request.getContextPath() + PAGE_NOACCESS); //无权限访问，跳转页面
		return;
	}

	/**
	 * 判断是否为不处理的页面
	 * @param uri 本地相对地址
	 * @return true 不用进行权限判断，false 要进行权限判断
	 */
	private static boolean isIgnoreURI(String uri)
	{
		//System.out.println("当前访问的URI是：" + uri);
		if(PAGE_IGNORE.contains(uri))// 判断是否为不处理页面
		{
			return true;// 是不处理的页面，不用判断
		}
		if(!isCheckAllFix)// 判断是否要判断后缀名
		{
			int i = uri.lastIndexOf(".");
			if (i == -1)
			{
				return true;//找不到后缀名，不用判断
			}
			String fix = "," + uri.substring(uri.lastIndexOf(".") + 1, uri.length()) + ",";
			if (CHECK_FIX.indexOf(fix) == -1)// 判断后缀名是否是要处理
			{
				return true;// 不在配置的后缀名里，不用判断
			}
		}
		return false;
	}
	
	private static boolean isAccess(HttpServletRequest request, String userAccount, String uri)
	{
		if (userAccount.length() == 0)
		{
			return false;
		}
		refreshSystemFunc();// 刷新数据
		if(resUrlMap.get("nullkey") != null)
		{
			return false;// 读不到权限数据
		}
		if(resUrlMap.get(uri) == null)// 不需要验证
		{
			return true;// 可以访问
		}
		//调用webservice获取用户有权限访问的功能结构
		Map<String, List<String>> resUrlIdMap = getUserFunc(userAccount, request.getSession());
		if(resUrlIdMap.get(uri) == null)// 没权限
		{
			return false;// 不可以访问
		}
		if(isCheckParam)// 是否需要验证参数
		{
			try
			{
				Map<String, String[]> reqParamMap= request.getParameterMap();// 锁定的只读map
				int mapLen = reqParamMap.size();
				List<String> resIdList = resUrlIdMap.get(uri);
				boolean flag = true;
				IRes r;
				for (String id : resIdList)
				{
					r = resMap.get(id);
					if(r == null)
					{
						return false;// 应该是出错的数据，正常情况下不会出现
					}
					if (r.getParamMap().size() == 0)// 没参数，不用判断了
					{
						return true;// 没参数，直接就算过了，相当于不用判断参数
					}
					if (mapLen == 0)// 有参数，但request没参数
					{
						continue;// 没法匹配，看其余的res有没有无参数的
					}
					flag = true;// 重新初始化
					for (Entry<String, String> entry : r.getParamMap().entrySet())
					{
						// 此处没有处理这种情况：有多个同名参数
						String key = entry.getKey();
						String vaule = entry.getValue();
						// reqParamMap.get(key)是String[]类型，转为list再跟value判断
						if (reqParamMap.containsKey(key) && Arrays.asList(reqParamMap.get(key)).contains(vaule))
						{
						}
						else
						// 任意一个找不到则跳出内层for循环
						{
							flag = false;
							break;
						}
					}
					if (flag)
					{
						return true;// 有权限
					}
				}
			}
			catch (Exception e)
			{
			}
			return false;// 出错或没有匹配到参数，当没权限
		}
		return true;
	}
	
	/**
	 * 获取用户有权限访问的功能结构
	 * @param userAccount
	 * @param 
	 * @return
	 */
	private static Map<String, List<String>> getUserFunc(String userAccount, HttpSession session)
	{
		Map<String, List<String>> userResMap = new HashMap<String, List<String>>();
		// 用户未改变，则session有信息
		if (session.getAttribute(KEY_SESSIONFUNC) != null)
		{
			userResMap = (Map<String, List<String>>)session.getAttribute(KEY_SESSIONFUNC);
		}
		else
		{
			IFunc[] myFuncArray = CasClientWebService.getDsworkCasWebService().getFunctionByUser(SYSTEM_ALIAS, SYSTEM_PASSWORD, userAccount);
			if(myFuncArray != null)// 读得到数据，结果集也可以为0
			{
				for(IFunc o : myFuncArray)// 将系统里的所有资源初始化到map中
				{
					if(o == null || o.getRes() == null || o.getRes().size() == 0)
					{
						continue;
					}
					for(IRes r : o.getRes())
					{
						if(userResMap.get(r.getUrl()) == null)
						{
							userResMap.put(r.getUrl(), new ArrayList<String>());
						}
						if(isCheckParam)// 需要参数判断时才放入参数信息，否则只需要有url的key值即可
						{
							userResMap.get(r.getUrl()).add(String.valueOf(r.getId()));// 只放入参数值
						}
					}
				}
			}
			session.setAttribute(KEY_SESSIONFUNC, userResMap);
		}
		return userResMap;
	}
	
	/**
	 * 获取用户菜单功能，过滤掉不显示在菜单上的功能项
	 * @param account
	 * @return
	 */
	public static IFunc[] getFuncByUser(String account)
	{
		List<IFunc> list = new ArrayList<IFunc>(); 
		if(account != null && !account.equals(""))
		{
			IFunc[] arr = CasClientWebService.getDsworkCasWebService().getFunctionByUser(SYSTEM_ALIAS, SYSTEM_PASSWORD, account);
			if(arr != null && arr.length > 0)
			{
				for(int i = arr.length - 1; i >= 0; i--)
				{
					if(arr[i].getStatus() == 1)
					{
						list.add(arr[i]);
					}
				}
			}
			return list.toArray(new IFunc[list.size()]);
		}
		return null;
	}
}
