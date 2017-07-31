package com.hero.commons.util;

public class ClassUtil {
	public static Class getClass(String name){
		try {
			return Class.forName(name);
		} catch (Exception e) {
			return null;
		}
	}
}
