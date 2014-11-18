package dswork.android.lib.core.util.webutil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpUtil 
{
	public static String sendHttpPost(HttpPostObj postObj)
	{
		String result = "";
		try{
			//发送HTTP request
			HttpPost req = new HttpPost(postObj.getUrl());
			System.out.println("【action url】:"+postObj.getUrl());
			req.setEntity(new UrlEncodedFormEntity(postObj.getParams(), HTTP.UTF_8));
			//设置网络超时
			HttpClient client =  new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(client.getParams(), 6000);//连接建立的超时时间
			HttpConnectionParams.setSoTimeout(client.getParams(), 15000);//连接建立后，没有收到response的超时时间
			//取得HTTP response
			HttpResponse resp = client.execute(req);
			//若状态码为200 ok
			if(resp.getStatusLine().getStatusCode() == 200){
				result = EntityUtils.toString(resp.getEntity(),"UTF-8");
			}else {
				result = "HttpResponse状态码："+resp.getStatusLine().getStatusCode();
				Log.i("HttpResponse状态码",result);
			}
		}catch(Exception e){
			result = e.getMessage();
			e.printStackTrace();
			Log.i("Http请求异常", result);
		}
		Log.i("Http result", result);
		return result;
	}
	
	/**
	 * Long类型id数组转成String类型（如new Long[]{1，2，3，4}转"1,2,3,4"）
	 * @param ids Long[]
	 * @return String
	 */
	public static String idsConvertToStr(Long[] ids)
	{
		String _ids = "";
		for(int i=0;i<ids.length;i++)
		{
			_ids += ids[i];
			if(i+1<ids.length) _ids += ",";
		}
		Log.i("列名串",_ids);
		return _ids;
	}
}
