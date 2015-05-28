package admin;

import gwen.devwork.BeanFactory;
import admin.service.PaperImageService;


public class MyFactory {
	public static PaperImageService getPaperImageService(){return (PaperImageService) BeanFactory.getBean("paperImageService");}
}
