package com.hero.commons.common;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
/**
 * key/value 键值对实体  
 * key.value 一对多 或一对一
 * @author yin.xiong
 * 2017年7月13日
 * @param <K>
 * @param <V>
 */
public class KeyValuePair<K extends Comparable<K>, V> implements KVPair<K, V> {
	private K key;
	private List<V> valueList = new ArrayList<V>(1);

	public KeyValuePair(K k, V value) {
		this.key = k;
		if (value != null) {
			valueList.add(value);
		}
	}

	public KeyValuePair(K k, V[] value) {
		this.key = k;
		if (value != null) {
			valueList.addAll(Arrays.asList(value));
		}
	}

	public void setValue(V[] value) {
		valueList.clear();
		valueList.addAll(Arrays.asList(value));
	}

	public void setValue(V value) {
		valueList.clear();
		valueList.add(value);
	}

	public void addValue(V value) {
		valueList.add(value);
	}

	public void addValue(V[] value) {
		valueList.addAll(Arrays.asList(value));
	}

	private int compareTo(KeyValuePair<K, V> o) {
		if (this.key == null)
			return -1;
		if (o.getKey() == null)
			return 1;
		return this.key.compareTo(o.getKey());
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(getKey());
		buf.append("=[");
		buf.append(getValueList());
		buf.append("];");
		return buf.toString();
	}
	/**
	 * k=v&k1=v1 形式
	 * @return
	 */
	public String toParameter() {
		StringBuilder sb=new StringBuilder();
		int i=0;
		for(V value:valueList){
			if (i!=0) {
				sb.append("&");
			}
			sb.append(getKey()+"="+value);
			i++;
		}
		return sb.toString();
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return valueList == null ? null : valueList.get(0);
	}

	@Override
	public V getValue(int i) {
		return valueList.get(0);
	}

	@Override
	public List<V> getValueList() {
		return valueList;
	}

	@Override
	public Iterator<V> getValueIterate() {
		return valueList.iterator();
	}

	@Override
	public int getSize() {
		return valueList.size();
	}

}
