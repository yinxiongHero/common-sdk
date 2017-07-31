package com.hero.commons.tool;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.hero.commons.exception.BaseException;



/**
 * 缓存工厂
 * @author yin.xiong
 * 2017年7月27日
 * @param <K>
 * @param <V>
 */
public abstract class CacheFactory<K, V> {

    private final Map<K, V> map = new HashMap<K, V>();

    /**
     * build object by key
     *
     * @param key key
     * @return value
     * @throws BaseException throw exception if build object fail
     */
    protected abstract V build(K key) throws BaseException;

    /**
     * get object by key from cache
     *
     * @param key key
     * @return value
     * @throws BaseException .
     */
    public V get(K key)
            throws BaseException {
        V value = map.get(key);
        if (value == null) {
            synchronized (map) {
                value = map.get(key);
                if (value == null) {
                    value = build(key);
                    map.put(key, value);
                }
            }
        }
        return value;
    }

    public Iterator<K> keyIterator() {
        return map.keySet().iterator();
    }

    public Collection<V> values() {
        return map.values();
    }
}
