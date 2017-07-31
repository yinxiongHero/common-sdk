package com.hero.commons.common;

import java.util.Iterator;
import java.util.List;

/**
 * K/V 键值对 接口定义
 * 
 * @author yin.xiong 2017年7月13日
 * @param <K>
 * @param <V>
 */
public interface KVPair<K extends Comparable<K>, V> {

	K getKey();

	V getValue();

	V getValue(int i);

	List<V> getValueList();

	Iterator<V> getValueIterate();

	int getSize();
}
