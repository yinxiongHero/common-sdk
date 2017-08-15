package com.hero.commons.util;

/**
 * string 处理工具类
 * 
 * @author yin.xiong 2017年8月4日
 */
public class StringUtil {

	public static boolean isEmpty(String str) {
		return str == null || str == "";
	}

	public static boolean isNotEmpty(String str) {
		return str != null && str != "";
	}
}
