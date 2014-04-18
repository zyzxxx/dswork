package dswork.android.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dswork.android.model.Demo;
import dswork.android.util.FileUtil;

import android.util.Log;


public class DemoService 
{
	public static List<Demo> getJSONDemo() throws Exception
	{
		String path="http://192.168.40.56:8088/adserverdemo/manage/demo/getJSONDemo.htm";
		URL url=new URL(path);
		HttpURLConnection conn=(HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		Log.i("【getJSONDemo】", String.valueOf(conn.getResponseCode()));
		if(conn.getResponseCode()==200)
		{
			InputStream is=conn.getInputStream();
			return parseJSON(is);
		}
		return null;
	}
	
	/**
	 * 解析JSON数据
	 * @param is
	 * @return
	 * @throws IOException 
	 * @throws JSONException 
	 */
	private static List<Demo> parseJSON(InputStream is) throws IOException, JSONException 
	{
		List<Demo> Demoes = new ArrayList<Demo>();
		byte[] data = FileUtil.getToByte(is);
		String json = new String(data);
		JSONArray array = new JSONArray(json);
		for(int i=0;i<array.length();i++)
		{
			JSONObject jsonObject = array.getJSONObject(i);
			Demo Demo = new Demo(jsonObject.getLong("id"),jsonObject.getString("title"),jsonObject.getString("content"),jsonObject.getString("foundtime"));
			Demoes.add(Demo);
		}
		return Demoes;
	}

	
	public int add(int a,int b)
	{
		return a+b;
	}
}
