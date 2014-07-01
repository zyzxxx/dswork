package dswork.android.lib.util.webutil;

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
		System.out.println("postObj.getUrl():"+postObj.getUrl());
		HttpPost req = new HttpPost(postObj.getUrl());
		try{
			//发送HTTP request
			req.setEntity(new UrlEncodedFormEntity(postObj.getParams(), HTTP.UTF_8));
			//取得HTTP response
			HttpResponse resp = new DefaultHttpClient().execute(req);
			//若状态码为200 ok
			if(resp.getStatusLine().getStatusCode() == 200){
				result = EntityUtils.toString(resp.getEntity(),"UTF-8");
			}else{
				result = "error";
			}
		}catch(Exception e){
			result = "error";
			System.out.println(e.getMessage());
			Log.i("Exception",e.getMessage());
			e.printStackTrace();
		}
		Log.i("result", result);
		return result;
	}
	
	/**
	 * Long类型id数组转成String类型（如"1,2,3,4"）
	 * @param ids
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
