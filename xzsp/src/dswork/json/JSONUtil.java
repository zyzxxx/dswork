package dswork.json;

public class JSONUtil
{
	static com.google.gson.GsonBuilder builder = new com.google.gson.GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
	//static com.alibaba.fastjson.serializer.SerializeConfig mapping = new com.alibaba.fastjson.serializer.SerializeConfig();
	//static{mapping.put(java.util.Date.class, new com.alibaba.fastjson.serializer.SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));}
	static com.google.gson.Gson gson = builder.create();
	public static String toJson(Object object)
	{
		return gson.toJson(object);
		//return com.alibaba.fastjson.JSON.toJSONString(object, mapping);
	}
	
	public static <T> T toBean(String json, Class<T> classOfT)
	{
		return gson.fromJson(json, classOfT);
		//return com.alibaba.fastjson.JSON.parseObject(json, classOfT);
	}
}
