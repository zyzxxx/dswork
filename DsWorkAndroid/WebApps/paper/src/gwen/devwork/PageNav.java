package gwen.devwork;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class PageNav<T>
{
	private Page page;
//	private String pageName;
	private String pageSizeListStr;
	private boolean isOutForm = false;
	private static String PAGEFORMID = "gwenPageForm";
	private String formId = PAGEFORMID;
	private String formString = "";
	
	public PageNav(HttpServletRequest request, Page page)
	{
		this(request, page, "10,20,50,100");
	}
	public PageNav(HttpServletRequest request, Page page, String pageSizeListStr)
	{
		super();
		this.page = page;
//		this.pageName=pageName;
		this.page = page;
		this.pageSizeListStr = pageSizeListStr;
		formId = formId + System.currentTimeMillis();
//		String pageName = page.getPageName();
//		String pageSizeName = page.getPageSizeName();
		try
		{
			StringBuilder sb = new StringBuilder("<script language=\"javascript\">if(typeof($jsgwen)!=\"object\"){$jsgwen={};}$jsgwen.page={go:function(formID,page){page=parseInt(page)||1;page=(page<1)?1:page;document.getElementById(formID+\"_page\").value=page;document.getElementById(formID+\"_pageSize\").value=document.getElementById(\"pageSize\").value;document.getElementById(formID).submit();}};</script>\n");
			sb.append("<form id=\"").append(formId).append("\" method=\"post\" style=\"display:none;\" action=\"").append(request.getRequestURI().toString()).append("\">");
			sb.append("<input id=\"").append(formId).append("_page\" name=\"").append("page").append("\" type=\"hidden\" value=\"1\"/>\n");
			sb.append("<input id=\"").append(formId).append("_pageSize\" name=\"").append("pageSize").append("\" type=\"hidden\" value=\"").append(page.getPageSize()).append("\"/>\n");
			
			@SuppressWarnings("all")
			Enumeration e = request.getParameterNames();
			String key = "";
			String value[];
			while(e.hasMoreElements())
			{
				key = (String) e.nextElement();
//				if(!key.equals(pageName) && !key.equals(pageSizeName))
				if(!key.equals("page") && !key.equals("pageSize"))
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
	
	//获取翻页导航html（jsp通过jstl调用${pageNav.pageNav}生成翻页html）
	public String getPageHtml()
	{
		return createPageHtml();
	}
	
	//获取结果集
	public List<T> getResult()
	{
		return page.getResult();
	}
	
	//创建翻页导航html
	private String createPageHtml()
	{
		int curPage=this.page.getCurPage();
		int countPage=this.page.getCountPage();
		int countPageEach=this.page.getCountPageEach();
		
		StringBuffer strHtml=new StringBuffer();
		
		int startPage = 0,endPage = 0;
		strHtml.append(getForm());
		strHtml.append("<table border='0' ><tr>");
		
		if(countPageEach % 2 == 0)
		{ //每次显示页数为偶数			
			even(curPage,countPage,countPageEach,strHtml,startPage,endPage);
		}
		else
		{ //每次显示页数为奇数
			odd(curPage,countPage,countPageEach,strHtml,startPage,endPage);
		}
		
		strHtml.append("<td><select id=\"pageSize\" onchange=\"$jsgwen.page.go('" + formId+ "', '1');return false;\">");
		String[] pageSizeArr = pageSizeListStr.split(",");
	    for(int i=0; i<pageSizeArr.length; i++)
	    {
	    	int pageSize = Integer.valueOf(pageSizeArr[i]);
			if(pageSize == page.getPageSize())
				strHtml.append("<option selected>"+pageSize+"</option>");
			else
				strHtml.append("<option>"+pageSize+"</option>");
		}
		strHtml.append("</select></td>");
		strHtml.append("<td>转到第<input type=\"text\" id=\"pageTo\" name=\"pageTo\" style=\"width:30px\"/>页<input type=\"button\" value=\"GO\" onclick=\"$jsgwen.page.go('" + formId+ "', document.getElementById('pageTo').value);return false;\"/></td>");
		strHtml.append("</tr></table>");
		return strHtml.toString();
		
	}
	
	/*
	 * 奇数页码样式
	 */
	private void odd(int curPage,int countPage,int countPageEach,StringBuffer strHtml,int startPage,int endPage)
	{
		/*样式① 【1】 2 3 4 5 下一页*/
		if(countPage<=countPageEach)
		{
			if(curPage>1)
			{
				strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">上一页</a></td>");
			}
			for(int i=1;i<=countPage;i++){
				if(curPage==i)
				{
					strHtml.append("<td><b>["+i+"]</b></td>");
					continue;
				}
				strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></td>");
			}
			if(curPage<countPage)
			{
				strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">下一页</a></td>");
			}
		}
		/*样式②*/
		else if(curPage<=(countPageEach/2)*2){

			if(curPage==1)
			{//【1】 2 3 4 5 ... 16下一页
				strHtml.append("<td><b>[1]</b></td>");
				startPage=2;
				endPage=countPageEach;
				for(int i=startPage;i<=endPage;i++)
				{
					strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></td>");
				}
			}
			else if(curPage>1 && curPage<countPageEach)
			{//上一页 1 2 3 【4】 5  ... 16 下一页
				strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">上一页</a></td>");
				startPage=1;
				endPage=countPageEach;
				for(int i=startPage;i<=endPage;i++)
				{
					if(curPage==i)
					{
						strHtml.append("<td><b>["+i+"]</b></td>");
						continue;
					}
					strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></td>");
				}
			}
			strHtml.append("<td>...</td>");
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+countPage+"');return false;\" href=\"#\">"+countPage+"</a></td>");
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">下一页</a></td>");
		}
		/*样式③ 上一页 1 ... 3 4 【5】 6 7  ... 16 下一页*/
		else if(curPage>(countPageEach/2)+2 && curPage <= (countPage - countPageEach+1))
		{
			startPage = curPage - countPageEach / 2;
			endPage = curPage + countPageEach / 2;
			if(endPage >= countPage)
			{
				endPage=countPage;
			}

			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">上一页</a></td>");
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+1+"');return false;\" href=\"#\">1</a></td>");
			strHtml.append("<td>...</td>");
			for(int i=startPage;i<=endPage;i++)
			{
				if(curPage==i)
				{
					strHtml.append("<td><b>["+i+"]</b></td>");
					continue;
				}
				strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></td>");
			}
			strHtml.append("<td>...</td>");
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+countPage+"');return false;\" href=\"#\">"+countPage+"</a></td>");
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">下一页</a></td>");
			
		}
		/*样式④ 上一页 1 ... 12 13 14 【15】 16 下一页*/
		else if(curPage > (countPage - countPageEach)+1 && curPage < countPage)
		{
			startPage=countPage-countPageEach+1;
			endPage=countPage;
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">上一页</a></td>");
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+1+"');return false;\" href=\"#\">1</a></td>");
			strHtml.append("<td>...</td>");
			for(int i=startPage;i<=endPage;i++)
			{
				if(curPage==i)
				{
					strHtml.append("<td><b>["+i+"]</b></td>");
					continue;
				}
				strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></td>");
			}
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">下一页</a></td>");
		}
		/*样式⑤ 上一页 1 ...12 13 14 15 【16】 */
		else if(curPage==countPage)
		{
			startPage=countPage-countPageEach+1;
			endPage=countPage-1;
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">上一页</a></td>");
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+1+"');return false;\" href=\"#\">1</a></td>");
			strHtml.append("<td>...</td>");
			for(int i=startPage;i<=endPage;i++)
			{
				strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></td>");
			}
			strHtml.append("<td><b>["+countPage+"]</b></td>");
		}
	}
	
	/*
	 * 偶数页码样式
	 */
	private void even(int curPage,int countPage,int countPageEach,StringBuffer strHtml,int startPage,int endPage)
	{
		/*样式① 【1】 2 3 4 5 下一页*/
		if(countPage<=countPageEach)
		{
			if(curPage>1)
			{
				strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">上一页</a></td>");
			}
			for(int i=1;i<=countPage;i++)
			{
				if(curPage==i)
				{
					strHtml.append("<td><b>["+i+"]</b></td>");
					continue;
				}
				strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></td>");
			}
			if(curPage<countPage)
			{
				strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">下一页</a></td>");
			}
		}
		/*样式②*/
		else if(curPage<(countPageEach/2)*2){
			if(curPage==1)
			{//【1】 2 3 4 5 ... 16下一页
				strHtml.append("<td><b>[1]</b></td>");
				startPage=2;
				endPage=countPageEach;
				for(int i=startPage;i<=endPage;i++)
				{
					strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></td>");
				}
			}
			else if(curPage>1 && curPage<countPageEach)
			{//上一页 1 2 3 【4】 5  ... 16 下一页
				strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">上一页</a></td>");
				startPage=1;
				endPage=countPageEach;
				for(int i=startPage;i<=endPage;i++)
				{
					if(curPage==i)
					{
						strHtml.append("<td><b>["+i+"]</b></td>");
						continue;
					}
					strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></td>");
				}
			}
			strHtml.append("<td>...</td>");
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+countPage+"');return false;\" href=\"#\">"+countPage+"</a></td>");
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">下一页</a></td>");
		}
		/*样式③ 上一页 1 ... 3 4 【5】 6 7  ... 16 下一页*/
		else if(curPage>(countPageEach/2)+1 && curPage <= (countPage - countPageEach+1))
		{
			startPage = (curPage - countPageEach / 2) + 1;
			endPage = curPage + countPageEach / 2 ;

			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">上一页</a></td>");
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+1+"');return false;\" href=\"#\">1</a></td>");
			strHtml.append("<td>...</td>");
			for(int i=startPage;i<=endPage;i++)
			{
				if(curPage==i)
				{
					strHtml.append("<td><b>["+i+"]</b></td>");
					continue;
				}
				strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></td>");
			}
			strHtml.append("<td>...</td>");
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+countPage+"');return false;\" href=\"#\">"+countPage+"</a></td>");
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">下一页</a></td>");
			
		}
		/*样式④ 上一页 1 ... 12 13 14 【15】 16 下一页*/
		else if(curPage > (countPage - countPageEach)+1 && curPage < countPage)
		{
			startPage=countPage-countPageEach+1;
			endPage=countPage;
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">上一页</a></td>");
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+1+"');return false;\" href=\"#\">1</a></td>");
			strHtml.append("<td>...</td>");
			for(int i=startPage;i<=endPage;i++)
			{
				if(curPage==i)
				{
					strHtml.append("<td><b>["+i+"]</b></td>");
					continue;
				}
				strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></td>");
			}
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">下一页</a></td>");
		}
		/*样式⑤ 上一页 1 ...12 13 14 15 【16】 */
		else if(curPage==countPage)
		{
			startPage=countPage-countPageEach+1;
			endPage=countPage-1;
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">上一页</a></td>");
			strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+1+"');return false;\" href=\"#\">1</a></td>");
			strHtml.append("<td>...</td>");
			for(int i=startPage;i<=endPage;i++)
			{
				strHtml.append("<td><a onclick=\"$jsgwen.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></td>");
			}
			strHtml.append("<td><b>["+countPage+"]</b></td>");
		}		
	}
}
