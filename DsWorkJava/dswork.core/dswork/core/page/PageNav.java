package dswork.core.page;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面显示翻页的对象
 */
public class PageNav<T>
{
	private static long i = 0L;
	private Page<T> page;
	private boolean isOutForm = false;
	private static String PAGEFORMID = "jskeyPageForm";
	private String formId = PAGEFORMID;
	private String formString = "";
	private static int[] sizeArray = {5, 10, 15, 20, 25, 30, 50};

	/**
	 * 构造函数
	 * @param request HttpServletRequest
	 * @param page Page<T>
	 */
	public PageNav(HttpServletRequest request, Page<T> page)
	{
		this.page = page;
		formId = formId + System.currentTimeMillis();
		String pageName = page.getPageName();
		String pageSizeName = page.getPageSizeName();
		try
		{
			StringBuilder sb = new StringBuilder("<script language=\"javascript\">if(typeof($jskey)!=\"object\"){$jskey={};}$jskey.page={go:function(formID,page){page=parseInt(page)||1;page=(page<1)?1:page;document.getElementById(formID+\"_page\").value=page;document.getElementById(formID).submit();}};</script>\n");
			sb.append("<form id=\"").append(formId).append("\" method=\"post\" style=\"display:none;\" action=\"").append(request.getRequestURL().toString()).append("\">");
			sb.append("<input id=\"").append(formId).append("_page\" name=\"").append(pageName).append("\" type=\"hidden\" value=\"1\"/>\n");
			sb.append("<input id=\"").append(formId).append("_pageSize\" name=\"").append(page.getPageSizeName()).append("\" type=\"hidden\" value=\"").append(page.getPageSize()).append("\"/>\n");

			@SuppressWarnings("all")
			Enumeration e = request.getParameterNames();
			String key = "";
			String value[];
			while(e.hasMoreElements())
			{
				key = (String) e.nextElement();
				if(!key.equals(pageName) && !key.equals(pageSizeName))
				{
					value = request.getParameterValues(key);
					if(value == null || value.length == 0)
					{
						sb.append("<input name=\"").append(key).append("\" type=\"hidden\" value=\"\"/>\n");
					}
					else
					{
						for(int i = 0; i < value.length; i++)
						{
							if(value[i] == null)
							{
								value[i] = "";
							}
							sb.append("<input name=\"").append(key).append("\" type=\"hidden\" value=\"").append(value[i].replace("\"", "&quot;")).append("\"/>\n");
						}
					}
				}
			}
			sb.append("</form>");
			formString = sb.toString();
		}
		catch(Exception e)
		{
		}
	}

	/**
	 * 输出表单
	 */
	public String getForm()
	{
		if(!isOutForm)
		{
			isOutForm = true;
			return formString;
		}
		return "";
	}

	/**
	 * 显示默认的翻页效果
	 */
	public String getPage()
	{
		return getPage(true, true, true, true, true);
	}
	
	/**
	 * 显示分页控件
	 * @param isViewTotal 是否显示所有记录数
	 * @param isViewPageInfo 是否显示页面信息
	 * @param isShowLink 是否翻页
	 * @param isShowJump 是否支持跳转
	 * @param isShowJumpSize 是否支持定制记录数
	 */
	public String getPage(boolean isViewTotal, boolean isViewPageInfo, boolean isShowLink, boolean isShowJump, boolean isShowJumpSize)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(getForm());
		sb.append("<div class='pageview'>");
		if(isViewTotal)
		{
			sb.append(" 共").append(page.getTotalCount()).append("条记录 ");
		}
		if(isViewPageInfo)
		{
			sb.append(" 第").append(page.getCurrentPage()).append("/").append(page.getTotalPage()).append("页 ");
		}
		if(isShowLink)
		{
			sb.append("<a class=\"first\"" + ((page.getTotalPage() > 1 && page.getCurrentPage() > 1) ? " onclick=\"$jskey.page.go('" + formId+ "', '1');return false;\" href=\"#\"" : "") + ">首页</a>&nbsp;");
			sb.append("<a class=\"prev\"" + ((page.isHasPreviousPage()) ? " onclick=\"$jskey.page.go('" + formId+ "', '" + page.getPreviousPage() + "');return false;\" href=\"#\"" : "") + ">上页</a>&nbsp;");
			sb.append("<a class=\"next\"" + ((page.isHasNextPage()) ? " onclick=\"$jskey.page.go('" + formId+ "', '" + page.getNextPage() + "');return false;\" href=\"#\"" : "") + ">下页</a>&nbsp;");
			sb.append("<a class=\"last\"" + ((page.getTotalPage() > 1 && page.getCurrentPage() < page.getTotalPage()) ? " onclick=\"$jskey.page.go('" + formId+ "', '" + page.getTotalPage() + "');return false;\" href=\"#\"" : "") + ">尾页</a>&nbsp;");
		}
		if(isShowJump || isShowJumpSize)
		{
			i = (i > 888L)?(0L):(i+1);
			String pid = formId + "_go" + i;
			if(isShowJump)
			{
				sb.append("转到第 <input type=\"text\" class=\"input\" id=\"").append(pid).append("\" /> 页 ").append("<input type=\"button\" class=\"go\" value=\"GO\" onclick=\"$jskey.page.go('").append(formId).append("', document.getElementById('").append(pid).append("').value);\" />");
			}
			if(isShowJumpSize)
			{
				sb.append(" <select id=\"").append(pid).append("_s").append("\" onchange=\"document.getElementById(\'").append(formId).append("_pageSize\').value=this.value;$jskey.page.go('" + formId+ "',1);\">");
				for(int j : sizeArray)
				{
					sb.append("<option value=\"").append(j).append((page.getPageSize() == j)?"\" selected=\"selected\">":"\">").append(j).append("</option>");
				}
				sb.append("</select>");
			}
		}
		sb.append("</div>");
		return sb.toString();
	}
}
