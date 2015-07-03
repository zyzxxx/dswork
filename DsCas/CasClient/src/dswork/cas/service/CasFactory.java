/**
 * 调用webservice的服务类
 */
package dswork.cas.service;

import java.net.MalformedURLException;
import java.net.URL;
import dswork.core.util.EnvironmentUtil;

public class CasFactory
{
	private static DsworkCasService dsworkCasService;
	
	private static void init()
	{
		URL url = null;
		try
		{
			url = new URL(DsworkCasService.class.getResource("."), EnvironmentUtil.getToString("dswork.cas.webservice", "") + "?wsdl");
			javax.xml.ws.Service service = javax.xml.ws.Service.create(url, new javax.xml.namespace.QName("http://service.cas.dswork/", "DsworkCasServiceService"));
			dsworkCasService = service.getPort(new javax.xml.namespace.QName("http://service.cas.dswork/", "DsworkCasServicePort"), DsworkCasService.class);
		}
		catch(MalformedURLException e)
		{
			System.out.println(e.toString());
		}
	}

	public void setDsworkCasService(DsworkCasService service)
	{
		dsworkCasService = service;
	}

	public static DsworkCasService getDsworkCasService()
	{
		if(dsworkCasService == null)
		{
			init();
		}
		return dsworkCasService;
	}
}
