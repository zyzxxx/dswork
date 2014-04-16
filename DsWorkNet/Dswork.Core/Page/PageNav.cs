using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Text;
using System.Web;

namespace Dswork.Core.Page
{
	/// <summary>
	/// 页面显示翻页的对象
	/// </summary>
	/// <typeparam name="T">详见Tecamo.Core.Page.Page类的T</typeparam>
	public class PageNav<T>
	{
		private static long i = 0L;
		private Page<T> page;
		private Boolean isOutForm = false;
		private static String PAGEFORMID = "jskeyPageForm";
		private String formId = PAGEFORMID;
		private String formString = "";
		private String Path = "";

		/// <summary>
		/// 初始化formString
		/// </summary>
		/// <param name="req"></param>
		private void PageNavInit(NameValueCollection req)
		{
			formId = formId + DateTime.Now.Ticks;
			String pageName = page.PageName;
			try
			{
				StringBuilder sb = new StringBuilder("<script language=\"javascript\">if(typeof($jskey)!=\"object\"){$jskey={};}$jskey.page={go:function(formID,page){page=parseInt(page)||1;page=(page<1)?1:page;document.getElementById(formID+\"_page\").value=page;document.getElementById(formID).submit();}};</script>\n");
				sb.Append("<form id=\"").Append(formId).Append("\" method=\"post\" style=\"display:none;\" action=\"").Append(Path).Append("\">");
				sb.Append("<input id=\"").Append(formId).Append("_page\" name=\"").Append(pageName).Append("\" type=\"hidden\" value=\"1\"/>\n");
				sb.Append("<input id=\"").Append(formId).Append("_pageSize\" name=\"").Append(page.PageSizeName).Append("\" type=\"hidden\" value=\"").Append(page.PageSize).Append("\"/>\n");

				String key;
				String[] values;
				for(int i = 0; i < req.Count; i++)
				{
					key = req.Keys[i];
					if(key != pageName)
					{
						values = req.GetValues(key);
						if(values == null || values.Length == 0)
						{
							sb.Append("<input name=\"").Append(key).Append("\" type=\"hidden\" value=\"\"/>\n");
						}
						else
						{
							for(int j = 0; j < values.Length; j++)
							{
								if(values[j] == null)
								{
									values[j] = "";
								}
								sb.Append("<input name=\"").Append(key).Append("\" type=\"hidden\" value=\"").Append(values[j].Replace("\"", "&quot;")).Append("\"/>\n");
							}
						}
					}
				}
				sb.Append("</form>");
				formString = sb.ToString();
			}
			catch
			{
			}
		}

		/// <summary>
		/// 构造函数
		/// </summary>
		/// <param name="request">HttpRequest</param>
		/// <param name="page">Page&lt;T&gt;</param>
		public PageNav(HttpRequest request, Page<T> page)
		{
			NameValueCollection req = new NameValueCollection();// 用于合并get和post
			req.Add(request.QueryString);
			req.Add(request.Form);
			this.page = page;
			this.Path = request.Path;
			PageNavInit(req);
		}

		/// <summary>
		/// 构造函数
		/// </summary>
		/// <param name="request">HttpRequestBase</param>
		/// <param name="page">Page&lt;T&gt;</param>
		public PageNav(HttpRequestBase request, Page<T> page)
		{
			NameValueCollection req = new NameValueCollection();// 用于合并get和post
			req.Add(request.QueryString);
			req.Add(request.Form);
			this.page = page;
			this.Path = request.Path;
			PageNavInit(req);
		}

		/// <summary>
		/// 构造函数
		/// </summary>
        /// <param name="request">HttpRequestWrapper</param>
		/// <param name="page">Page&lt;T&gt;</param>
        public PageNav(HttpRequestWrapper request, Page<T> page)
		{
			NameValueCollection req = new NameValueCollection();// 用于合并get和post
			req.Add(request.QueryString);
			req.Add(request.Form);
			this.page = page;
			this.Path = request.Path;
			PageNavInit(req);
		}

		/// <summary>
		/// 输出表单
		/// </summary>
		/// <returns>String</returns>
		public String ShowForm()
		{
			if(!isOutForm)
			{
				isOutForm = true;
				return formString;
			}
			return "";
		}

		/// <summary>
		/// 显示默认的翻页效果
		/// </summary>
		/// <returns>String</returns>
		public String ShowPage()
		{
			return ShowPage(true, true, true, true);
		}

		/// <summary>
		/// 显示分页控件
		/// </summary>
		/// <param name="isViewTotal">是否显示所有记录数</param>
		/// <param name="isViewPageInfo">是否显示页面信息</param>
		/// <param name="isShowLink">是否翻页</param>
		/// <param name="isShowJump">是否支持跳转</param>
		/// <returns>String</returns>
		public String ShowPage(Boolean isViewTotal, Boolean isViewPageInfo, Boolean isShowLink, Boolean isShowJump)
		{
			StringBuilder sb = new StringBuilder();
			sb.Append(ShowForm());
			sb.Append("<div class='pageview'>");
			if(isViewTotal)
			{
				sb.Append(" 共").Append(page.TotalCount).Append("条记录 ");
			}
			if(isViewPageInfo)
			{
				sb.Append(" 第").Append(page.CurrentPage).Append("/").Append(page.TotalPage).Append("页 ");
			}
			if(isShowLink)
			{
				sb.Append("<a class=\"first\"" + ((page.TotalPage > 1 && page.CurrentPage > 1) ? " onclick=\"$jskey.page.go('" + formId + "', '1');return false;\" href=\"#\"" : "") + ">首页</a>&nbsp;");
				sb.Append("<a class=\"prev\"" + ((page.IsHasPreviousPage) ? " onclick=\"$jskey.page.go('" + formId + "', '" + page.PreviousPage + "');return false;\" href=\"#\"" : "") + ">上页</a>&nbsp;");
				sb.Append("<a class=\"next\"" + ((page.IsHasNextPage) ? " onclick=\"$jskey.page.go('" + formId + "', '" + page.NextPage + "');return false;\" href=\"#\"" : "") + ">下页</a>&nbsp;");
				sb.Append("<a class=\"last\"" + ((page.TotalPage > 1 && page.CurrentPage < page.TotalPage) ? " onclick=\"$jskey.page.go('" + formId + "', '" + page.TotalPage + "');return false;\" href=\"#\"" : "") + ">尾页</a>&nbsp;");
			}
			if(isShowJump)
			{
				i = (i > 888L) ? (0L) : (i + 1);
				String pid = formId + "_go" + i;
				sb.Append("转到第 <input type=\"text\" class=\"input\" id=\"").Append(pid).Append("\" /> 页 ").Append("<input type=\"button\" class=\"go\" value=\"GO\" onclick=\"$jskey.page.go('").Append(formId).Append("', document.getElementById('").Append(pid).Append("').value);\" />");
			}
			sb.Append("</div>");
			return sb.ToString();
		}

		/// <summary>
		/// 显示翻页效果
		/// </summary>
		/// <returns>String</returns>
		[Obsolete("Recommended to use ShowPage()", false)]
		public String GetPage()
		{
			return ShowPage(true, true, true, true);
		}
	}
}
