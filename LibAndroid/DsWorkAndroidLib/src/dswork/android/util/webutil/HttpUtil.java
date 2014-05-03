package dswork.android.util.webutil;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpUtil 
{
	public static String sendHttpPost(HttpPostObj postObj)
	{
		String result = "";
		HttpPost req = new HttpPost(postObj.getUrl());
		try
		{
			//发送HTTP request
			req.setEntity(new UrlEncodedFormEntity(postObj.getParams(), HTTP.UTF_8));
			//取得HTTP response
			HttpResponse resp = new DefaultHttpClient().execute(req);
			
			//若状态码为200 ok
			if(resp.getStatusLine().getStatusCode() == 200)
			{
				result = EntityUtils.toString(resp.getEntity(),"UTF-8");
			}
			else
			{
				result = "error";
			}
		}
		catch(Exception e)
		{
			result = "error";
			Log.i("Exception",e.getMessage());
			e.printStackTrace();
		}
		Log.i("result", result);
		return result;
		
	}
}
