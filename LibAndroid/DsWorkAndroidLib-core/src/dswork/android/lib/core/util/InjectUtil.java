package dswork.android.lib.core.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

public class InjectUtil 
{
	
	@Target(ElementType.FIELD)  
	@Retention(RetentionPolicy.RUNTIME) 
	public @interface InjectView
	{
		int id();
	}    
	/**
	 * 根据注解注入视图控件
	 * @param activity
	 */
	public static void injectView(Activity activity)  
    {  
        Field[] fields = activity.getClass().getDeclaredFields();   //得到Activity中的所有定义的字段  
        if (fields != null && fields.length > 0)  
        {  
            for (Field field : fields)  
            {  
            	System.out.println("属性名："+field.getName());
                if (field.isAnnotationPresent(InjectView.class))                    //方法返回true，如果指定类型的注解存在于此元素上  
                {  
                    Log.i("Field", field.toString());  
                      
                    InjectView mInjectView = field.getAnnotation(InjectView.class); //获得该成员的annotation  
                    int viewId = mInjectView.id();                                  //获得该注解的id  
                    View view=activity.findViewById(viewId);                        //获得ID为viewID的组件对象  
                      
                    Log.i("Field", String.valueOf(viewId));  
                    Log.i("Field", view.getClass().toString());  
                      
                    try  
                    {  
                        field.setAccessible(true);  //设置类的私有成员变量可以被访问  
                        field.set(activity, view);  //field.set(object,value)===object.fieldValue = value  
                    } catch (Exception e) { e.printStackTrace();}  
                }  
                else  
                    Log.i("Field", "该属性没有被注解");  
            }  
        }  
    }
	/**
	 * 根据注解注入视图控件(用于Fragment)
	 * @param fragment
	 * @param v
	 */
	public static void injectView(Fragment fragment, View v)  
	{  
		Field[] fields = fragment.getClass().getDeclaredFields();   //得到Activity中的所有定义的字段  
		if (fields != null && fields.length > 0)  
		{  
			for (Field field : fields)  
			{  
				System.out.println("属性名："+field.getName());
				if (field.isAnnotationPresent(InjectView.class))                    //方法返回true，如果指定类型的注解存在于此元素上  
				{  
					Log.i("Field", field.toString());  
					
					InjectView mInjectView = field.getAnnotation(InjectView.class); //获得该成员的annotation  
					int viewId = mInjectView.id();                                  //获得该注解的id  
					View view=v.findViewById(viewId);                        //获得ID为viewID的组件对象  
					
					Log.i("Field", String.valueOf(viewId));  
					Log.i("Field", view.getClass().toString());  
					
					try  
					{  
						field.setAccessible(true);  //设置类的私有成员变量可以被访问  
						field.set(fragment, view);  //field.set(object,value)===object.fieldValue = value  
					} catch (Exception e) { e.printStackTrace();}  
				}  
				else  
					Log.i("Field", "该属性没有被注解");  
			}  
		}  
	}
}
