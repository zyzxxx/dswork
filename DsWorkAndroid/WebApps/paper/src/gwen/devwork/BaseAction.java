package gwen.devwork;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;


public abstract class BaseAction<T> extends ActionSupport 
{
	protected PrintWriter out;
	
	public HttpServletRequest getBaseReq(){
		return ServletActionContext.getRequest();
	}
	public HttpServletResponse getBaseResp(){
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		return ServletActionContext.getResponse();
	}
	public HttpSession getSession(){
		return getBaseReq().getSession();
	}
	public MyReq getReq(){
		return new MyReq(getBaseReq());
	}
	
	//结果集
	protected List<T> resultList;
	public List<T> getResultList() {
		return resultList;
	}
	//Page对象
	protected Page<T> page;
	public Page<T> getPage() {
		return page;
	}
	//PageNav对象
	protected PageNav<T> pageNav;
	public PageNav<T> getPageNav() {
		return pageNav;
	}
	
	public void put(String key, Object value){
		getReq().setAttribute(key, value);
	}
	
	public void print(String s){
		try{
			if(out == null){
				out = getBaseResp().getWriter();
			}
			out.print(s);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
