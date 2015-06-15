package dswork.json;

public class JSONUtil
{
	static com.alibaba.fastjson.serializer.SerializeConfig mapping = new com.alibaba.fastjson.serializer.SerializeConfig();
	static
	{
		mapping.put(java.util.Date.class, new com.alibaba.fastjson.serializer.SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}
	
	public static String toJson(Object object)
	{
		return com.alibaba.fastjson.JSON.toJSONString(object, mapping);
	}
	
	public static <T> T toBean(String json, Class<T> classOfT)
	{
		return com.alibaba.fastjson.JSON.parseObject(json, classOfT);
	}
}
