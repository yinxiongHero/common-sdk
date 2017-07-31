package com.hero.commons.util;

import java.util.ResourceBundle;

import com.hero.commons.tool.log.LogFactory;

/**
 * 资源工具类
 * @author yin.xiong
 * 2017年7月27日
 */
public class ResourceUtil {
	/**
	 *
	 * @param bundleName 资源文件名称  根路径下  /a/b
	 * @param key
	 * @return
	 */
	public static String getPropertyStr(String bundleName,String key){
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
			return bundle.getString(key);
		} catch (Exception e) {
			LogFactory.getLog().error(ResourceUtil.class,e);
			return "";
		}
	}
}
