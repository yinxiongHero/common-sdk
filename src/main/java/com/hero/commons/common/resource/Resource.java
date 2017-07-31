package com.hero.commons.common.resource;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Iterator;

import com.hero.commons.common.KVPair;

/**
 * 资源文件操作接口
 * 
 * @author yin.xiong 2017年7月13日
 */
public interface Resource {
	String[] getStringArray(String name);

	String[] getStringArray(String name, String[] vDefault);

	@SuppressWarnings("rawtypes")
	KVPair[] getByPrefix(String prefix);

	String getString(String name);

	String getString(String name, String vDefault);

	int getInteger(String name);

	int getInteger(String name, int vDefault);

	int getInteger(String name, int digit, int vDefault);

	long getLong(String name);

	long getLong(String name, long vDefault);

	long getLong(String name, int digit, long vDefault);

	boolean getBoolean(String name);

	boolean getBoolean(String name, boolean vDefault);

	BigDecimal getDecimal(String name);

	BigDecimal getDecimal(String name, BigDecimal vDefault);

	// 集合
	Enumeration<String> getkey();

	Iterator<String> keyIterator();

	String[] getKeyArray();

	boolean containsKey(String key);

	String getName();

}
