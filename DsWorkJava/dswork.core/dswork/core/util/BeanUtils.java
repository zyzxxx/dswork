package dswork.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * apache BeanUtils的等价类，只是将check exception改为uncheck exception
 */
@SuppressWarnings("unchecked")
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils
{
	protected static final Log logger = LogFactory.getLog(BeanUtils.class);

	private BeanUtils()
	{
	}

	private static void handleReflectionException(Exception e)
	{
		ReflectionUtils.handleReflectionException(e);
	}

	public static Object cloneBean(Object bean)
	{
		try
		{
			return org.apache.commons.beanutils.BeanUtils.cloneBean(bean);
		}
		catch (Exception e)
		{
			handleReflectionException(e);
			return null;
		}
	}

	public static <T> T copyProperties(Class<T> destClass, Object orig)
	{
		try
		{
			Object target = destClass.newInstance();
			copyProperties((Object) target, orig);
			return (T) target;
		}
		catch (Exception e)
		{
			handleReflectionException(e);
			return null;
		}
	}

	public static void copyProperties(Object dest, Object orig)
	{
		try
		{
			org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
		}
		catch (Exception e)
		{
			handleReflectionException(e);
		}
	}

	public static void copyProperty(Object bean, String name, Object value)
	{
		try
		{
			org.apache.commons.beanutils.BeanUtils.copyProperty(bean, name, value);
		}
		catch (Exception e)
		{
			handleReflectionException(e);
		}
	}

	public static Map describe(Object bean)
	{
		try
		{
			return org.apache.commons.beanutils.BeanUtils.describe(bean);
		}
		catch (Exception e)
		{
			handleReflectionException(e);
			return null;
		}
	}

	public static String[] getArrayProperty(Object bean, String name)
	{
		try
		{
			return org.apache.commons.beanutils.BeanUtils.getArrayProperty(bean, name);
		}
		catch (Exception e)
		{
			handleReflectionException(e);
			return null;
		}
	}

	public static String getIndexedProperty(Object bean, String name, int index)
	{
		try
		{
			return org.apache.commons.beanutils.BeanUtils.getIndexedProperty(bean, name, index);
		}
		catch (Exception e)
		{
			handleReflectionException(e);
			return null;
		}
	}

	public static String getIndexedProperty(Object bean, String name)
	{
		try
		{
			return org.apache.commons.beanutils.BeanUtils.getIndexedProperty(bean, name);
		}
		catch (Exception e)
		{
			handleReflectionException(e);
			return null;
		}
	}

	public static String getMappedProperty(Object bean, String name, String key)
	{
		try
		{
			return org.apache.commons.beanutils.BeanUtils.getMappedProperty(bean, name, key);
		}
		catch (Exception e)
		{
			handleReflectionException(e);
			return null;
		}
	}

	public static String getMappedProperty(Object bean, String name)
	{
		try
		{
			return org.apache.commons.beanutils.BeanUtils.getMappedProperty(bean, name);
		}
		catch (Exception e)
		{
			handleReflectionException(e);
			return null;
		}
	}

	public static String getNestedProperty(Object bean, String name)
	{
		try
		{
			return org.apache.commons.beanutils.BeanUtils.getNestedProperty(bean, name);
		}
		catch (Exception e)
		{
			handleReflectionException(e);
			return null;
		}
	}

	public static String getProperty(Object bean, String name)
	{
		try
		{
			return org.apache.commons.beanutils.BeanUtils.getProperty(bean, name);
		}
		catch (Exception e)
		{
			handleReflectionException(e);
			return null;
		}
	}

	public static String getSimpleProperty(Object bean, String name)
	{
		try
		{
			return org.apache.commons.beanutils.BeanUtils.getSimpleProperty(bean, name);
		}
		catch (Exception e)
		{
			handleReflectionException(e);
			return null;
		}
	}

	public static void populate(Object bean, Map properties)
	{
		try
		{
			org.apache.commons.beanutils.BeanUtils.populate(bean, properties);
		}
		catch (Exception e)
		{
			handleReflectionException(e);
		}
	}

	public static void setProperty(Object bean, String name, Object value)
	{
		try
		{
			org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
		}
		catch (Exception e)
		{
			handleReflectionException(e);
		}
	}

	// ************************************************************************************
	// ************************************************************************************
	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException
	{
		Assert.notNull(object);
		Assert.hasText(propertyName);
		return getDeclaredField(object.getClass(), propertyName);
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException
	{
		Assert.notNull(clazz);
		Assert.hasText(propertyName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass())
		{
			try
			{
				return superClass.getDeclaredField(propertyName);
			}
			catch (NoSuchFieldException e)
			{
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
	}

	/**
	 * 暴力获取对象变量值,忽略private,protected修饰符的限制.
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 */
	public static Object forceGetProperty(Object object, String propertyName) throws NoSuchFieldException
	{
		Assert.notNull(object);
		Assert.hasText(propertyName);
		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		Object result = null;
		try
		{
			result = field.get(object);
		}
		catch (IllegalAccessException e)
		{
			logger.info("error wont' happen");
		}
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * 暴力设置对象变量值,忽略private,protected修饰符的限制.
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 */
	public static void forceSetProperty(Object object, String propertyName, Object newValue) throws NoSuchFieldException
	{
		Assert.notNull(object);
		Assert.hasText(propertyName);
		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try
		{
			field.set(object, newValue);
		}
		catch (IllegalAccessException e)
		{
			logger.info("Error won't happen");
		}
		field.setAccessible(accessible);
	}

	/**
	 * 暴力调用对象函数,忽略private,protected修饰符的限制.
	 * @throws NoSuchMethodException 如果没有该Method时抛出.
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object... params) throws NoSuchMethodException
	{
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++)
		{
			types[i] = params[i].getClass();
		}
		Class clazz = object.getClass();
		Method method = null;
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass())
		{
			try
			{
				method = superClass.getDeclaredMethod(methodName, types);
				break;
			}
			catch (NoSuchMethodException e)
			{
				// 方法不在当前类定义,继续向上转型
			}
		}
		if (method == null)
			throw new NoSuchMethodException("No Such Method:" + clazz.getSimpleName() + methodName);
		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = null;
		try
		{
			result = method.invoke(object, params);
		}
		catch (Exception e)
		{
			ReflectionUtils.handleReflectionException(e);
		}
		method.setAccessible(accessible);
		return result;
	}

	/**
	 * 按Filed的类型取得Field列表.
	 */
	public static List<Field> getFieldsByType(Object object, Class type)
	{
		List<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields)
		{
			if (field.getType().isAssignableFrom(type))
			{
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * 按FiledName获得Field的类型.
	 */
	public static Class getPropertyType(Class type, String name) throws NoSuchFieldException
	{
		return getDeclaredField(type, name).getType();
	}

	/**
	 * 获得field的getter函数名称.
	 */
	public static String getGetterName(Class type, String fieldName)
	{
		Assert.notNull(type, "Type required");
		Assert.hasText(fieldName, "FieldName required");
		if (type.getName().equals("boolean"))
		{
			return "is" + StringUtils.capitalize(fieldName);
		}
		else
		{
			return "get" + StringUtils.capitalize(fieldName);
		}
	}

	/**
	 * 获得field的getter函数,如果找不到该方法,返回null.
	 */
	public static Method getGetterMethod(Class type, String fieldName)
	{
		try
		{
			return type.getMethod(getGetterName(type, fieldName));
		}
		catch (NoSuchMethodException e)
		{
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
