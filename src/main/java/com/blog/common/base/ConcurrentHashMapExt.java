package com.blog.common.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: heqq
 * Date: 11-3-11
 * Time: 上午9:23
 * To change this template use File | Settings | File Templates.
 */
public class ConcurrentHashMapExt<K, V> extends ConcurrentHashMap<K, V> {
    public ConcurrentHashMapExt() {
        super();
    }

    public ConcurrentHashMapExt(int initialCapacity) {
        super(initialCapacity);
    }

    public ConcurrentHashMapExt(Map<? extends K, ? extends V> m) {
        super(m);
    }

    synchronized public V put(K key, V value) {
        if (value != null) {
            return super.put(key, value);
        }else {
            super.remove(key);
        }
        return value;
    }
}
