package dswork;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponseWrapper;

@SuppressWarnings("all")
public class CharacterEncodingFilter implements Filter
{
	private String encoding = "UTF-8";

	private boolean forceEncoding = false;
	
	public void init(FilterConfig config) throws ServletException
	{
		encoding = String.valueOf(config.getInitParameter("encoding")).trim();
		String v = String.valueOf(config.getInitParameter("forceEncoding")).trim();
		if(v.equals("true"))
		{
			forceEncoding = true;
		}
	}
	
	public void destroy()
	{
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		req.setCharacterEncoding(this.encoding);
		if (this.forceEncoding) {
			res.setCharacterEncoding(this.encoding);
		}
		chain.doFilter(req, res);
	}
}
