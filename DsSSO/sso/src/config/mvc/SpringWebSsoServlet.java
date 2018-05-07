package config.mvc;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
@WebServlet(
	name="SpringWebSsoServlet", 
	loadOnStartup=1, 
	urlPatterns={"/login","/loginAction","/logout","/password","/passwordAction","/api/*"}, 
	initParams={@WebInitParam(name="contextConfigLocation",value="classpath*:/config/mvc/SpringWebSsoServlet.xml")}
)
public class SpringWebSsoServlet extends org.springframework.web.servlet.DispatcherServlet
{
}
