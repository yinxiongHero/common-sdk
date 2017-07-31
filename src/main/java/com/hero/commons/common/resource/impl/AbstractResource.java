package com.hero.commons.common.resource.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.hero.commons.common.KVPair;
import com.hero.commons.common.KeyValuePair;
import com.hero.commons.common.resource.Resource;

public class AbstractResource implements Resource {

	private Map<String, KeyValuePair<String, String>> keyValueMap = new HashMap<String, KeyValuePair<String, String>>();

	private List<String> keyList = new Vector<String>();
	
	/**
     * add value
     *
     * @param name  name
     * @param value value
     */
    protected synchronized final void add(String name, String value) {
        if (keyValueMap.containsKey(name)) {
            KeyValuePair<String, String> pair = keyValueMap.get(name);
            pair.addValue(value);
        } else {
            KeyValuePair<String, String> pair = new KeyValuePair<String, String>(name, value);
            keyValueMap.put(name, pair);
            keyList.add(name);
        }
    }
	
	
	@Override
	public String[] getStringArray(String name) {
		return getStringArray(name, null);
	}

	/**
	 * 根据name 从map中key=name 对应的KeyValuePair 节点。KeyValuePair 节点中存在key-value 1对多
	 * 
	 * @param name
	 * @param vDefault
	 * @return 返回KeyValuePair节点下全部的value
	 */
	@Override
	public String[] getStringArray(String name, String[] vDefault) {
		KeyValuePair<String, String> entity = keyValueMap.get(name);
		if (entity == null) {
			return vDefault;
		}
		String[] result = new String[entity.getSize()];
		entity.getValueList().toArray(result);
		return result;
	}

	protected final KVPair<String, String> getNameValuePair(String name) {
		return keyValueMap.get(name);
	}

	/**
	 * KVPair 类似 string 是一种容器 找出keyList中所有前缀为prefix的key 找出该key对应keyvalueMap中对应的value
	 * 
	 * @param prefix
	 * @return 返回所有符合条件的value集合
	 */
	@Override
	public KVPair[] getByPrefix(String prefix) {
		List<KVPair> list = new ArrayList<KVPair>();
		for (String key : keyList) {
			if (key.startsWith(prefix)) {
				KVPair<String, String> kv = getNameValuePair(key);
				list.add(kv);
			}
		}
		return list.toArray(new KVPair[list.size()]);
	}

	@Override
	public String getString(String name) {
		String[] values = getStringArray(name, null);
		return values == null || values.length == 0 ? null : values[0];
	}

	@Override
	public String getString(String name, String vDefault) {
		String value = getString(name);
		return value == null ? vDefault : value;
	}

	@Override
	public int getInteger(String name) {
		return getInteger(name, 0);
	}

	@Override
	public int getInteger(String name, int vDefault) {
		return getInteger(name, 10, vDefault);
	}

	@Override
	public int getInteger(String name, int digit, int vDefault) {
		String result = getString(name);
		if (result == null)
			return 0;
		try {
			return Integer.parseInt(result.trim(), digit);
		} catch (Exception e) {
		}
		return vDefault;
	}

	@Override
	public long getLong(String name) {
		return getLong(name, 0);
	}

	@Override
	public long getLong(String name, long vDefault) {
		return getLong(name, 10, vDefault);
	}

	@Override
	public long getLong(String name, int digit, long vDefault) {
		String s = getString(name, null);
		if (s == null) {
			return vDefault;
		}
		try {
			return Long.parseLong(s.trim(), digit);
		} catch (Exception e) {
		}
		return vDefault;
	}

	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}

	public boolean getBoolean(String name, boolean vDefault) {
		String s = getString(name, null);
		return s == null ? vDefault : ("true".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s));
	}

	public BigDecimal getDecimal(String name) {
		return getDecimal(name, null);
	}

	public BigDecimal getDecimal(String name, BigDecimal vDefault) {
		String s = getString(name, null);
		if (s==null) {
			return vDefault;
		}
		try {
			return new BigDecimal(s.trim());
		} catch (Exception e) {
		}
		return vDefault;
	}

	@Override
	public Enumeration<String> getkey() {
		return null;
	}

	@Override
	public Iterator<String> keyIterator() {
		return keyList.iterator();
	}

	@Override
	public String[] getKeyArray() {
		String[] result = new String[keyList.size()];
		keyList.toArray(result);
		return result;
	}

	@Override
	public boolean containsKey(String key) {
		return keyValueMap.containsKey(key);
	}

	@Override
	public String getName() {
		return null;
	}

}
