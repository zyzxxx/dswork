package common.cms;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
@WebServlet(
	name="DsCmsServlet", 
	loadOnStartup=1, 
	urlPatterns={"*.chtml"}, 
	initParams={@WebInitParam(name="contextConfigLocation",value="classpath*:/common/cms/DsCmsServlet.xml")}
)
public class DsCmsServlet extends org.springframework.web.servlet.DispatcherServlet
{
}
