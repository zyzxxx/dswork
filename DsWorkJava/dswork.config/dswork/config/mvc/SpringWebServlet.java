package dswork.config.mvc;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
@WebServlet(
	name="SpringWebServlet", 
	loadOnStartup=1, 
	urlPatterns={"*.htm"}, 
	initParams={@WebInitParam(name="contextConfigLocation",value="classpath*:/dswork/config/mvc/SpringWebServlet.xml,classpath*:/config/springmvc*.xml")}
)
public class SpringWebServlet extends org.springframework.web.servlet.DispatcherServlet
{
}
