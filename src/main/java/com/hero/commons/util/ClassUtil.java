package com.hero.commons.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtil {
	public static Class getClass(String name){
		try {
			return Class.forName(name);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static Type[] getTypes(Object obj) {
		ParameterizedType parameterizedType = (ParameterizedType) obj.getClass().getGenericSuperclass();
		return parameterizedType.getActualTypeArguments();
	}
	
}
