package dswork.android.lib.core.util.webutil;

import java.io.InputStream;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class HttpUtil
{
	private static RequestQueue mQueue;
	
	public static <T> HttpResultObj<T> sendHttpAction(HttpActionObj actionObj, Class<T> clazz)
	{
		return sendHttpAction(actionObj, clazz, 6000, 15000);
	}
	@SuppressWarnings("unchecked")
	public static <T> HttpResultObj<T> sendHttpAction(HttpActionObj actionObj, Class<T> clazz, int connectionTimeout, int soTimeout)
	{
		System.out.println("action url ------> "+actionObj.getUrl());
		HttpResultObj<T> o = new HttpResultObj<T>();
		try
		{
			//初始化HttpPost
			HttpPost req = new HttpPost(actionObj.getUrl());
			req.setEntity(new UrlEncodedFormEntity(actionObj.getParams(), HTTP.UTF_8));
			//设置网络超时
			HttpClient client =  new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(client.getParams(), connectionTimeout);//连接建立的超时时间
			HttpConnectionParams.setSoTimeout(client.getParams(), soTimeout);//连接建立后，没有收到response的超时时间
			//执行请求
			HttpResponse resp = client.execute(req);
			if(resp.getStatusLine().getStatusCode() == 200)
			{
				if(clazz.isAssignableFrom(String.class))
				{
					o.setData((T) EntityUtils.toString(resp.getEntity(),"UTF-8"));
				}
				else if(clazz.isAssignableFrom(Bitmap.class))
				{
					InputStream is = resp.getEntity().getContent();
					o.setData((T) BitmapFactory.decodeStream(is));
				}
				else if(clazz.isAssignableFrom(InputStream.class))
				{
					o.setData((T) resp.getEntity().getContent());
				}
				o.setSuc(true);
			}
			else 
			{
				o.setSuc(false);
				o.setErrMsg("请求失败："+resp.getStatusLine().getStatusCode());
			}
		}
		catch(Exception e)
		{
			o.setSuc(false);
			o.setErrMsg("请求失败："+e.getMessage());
			e.printStackTrace();
		}
		return o;
	}
	
	public static void sendVollyHttpActionBitmap(Context ctx, final HttpActionObj actionObj, final ImageView v)
	{
		mQueue = Volley.newRequestQueue(ctx);
		Response.Listener<Bitmap> sucListener = new Response.Listener<Bitmap>()
		{  
            @Override  
            public void onResponse(Bitmap response)
            {
            	v.setImageBitmap(response);
            }  
        };
        Response.ErrorListener errListener = new Response.ErrorListener()
        {  
            @Override  
            public void onErrorResponse(VolleyError error)
            {  
                Log.e("TAG", error.getMessage(), error);
//                v.setImageResource(R.drawable.默认图片);
            }  
        };
        ImageRequest imageRequest = new ImageRequest(actionObj.getUrl(), sucListener, 0, 0, Config.RGB_565, errListener)
        {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError
			{
				return actionObj.getParamsMap();
			}
		};
		mQueue.add(imageRequest);
	}
	
	public static String sendVollyHttpAction(Context ctx, final HttpActionObj actionObj)
	{
		final String[] result = null;
		mQueue = Volley.newRequestQueue(ctx);
		Response.Listener<String> sucListener = new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);
				result[0] = response;
			}  
		};
		Response.ErrorListener errListener = new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error);  
			}  
		};
		StringRequest stringRequest = new StringRequest(actionObj.getUrl(), sucListener, errListener){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError
			{
				return actionObj.getParamsMap();
			}
		}; 
		mQueue.add(stringRequest);
		return result[0];
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
