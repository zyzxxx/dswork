/**
 * 调用webservice的服务类
 */
package dswork.cas.service;

import java.net.MalformedURLException;
import java.net.URL;
import dswork.core.util.EnvironmentUtil;

public class CasClientWebService
{
	private static DsworkCasWebService dsworkCasWebService;
	
	static
	{
		URL url = null;
		try
		{
			url = new URL(DsworkCasWebService.class.getResource("."), EnvironmentUtil.getToString("dswork.cas.webservice", "") + "?wsdl");
		}
		catch(MalformedURLException e)
		{
			System.out.println(e.toString());
		}
		javax.xml.ws.Service service = javax.xml.ws.Service.create(url, new javax.xml.namespace.QName("http://cas.service.frame.dswork/", "DsworkCasWebServiceService"));
		dsworkCasWebService = service.getPort(new javax.xml.namespace.QName("http://cas.service.frame.dswork/", "DsworkCasWebServicePort"), DsworkCasWebService.class);
	}

	public void setTecamoCasWebService(DsworkCasWebService service)
	{
		dsworkCasWebService = service;
	}

	public static DsworkCasWebService getDsworkCasWebService()
	{
		return dsworkCasWebService;
	}
}
