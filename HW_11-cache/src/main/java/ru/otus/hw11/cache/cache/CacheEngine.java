package ru.otus.hw11.cache.cache;

import java.lang.ref.SoftReference;

public interface CacheEngine<K, V> {

    void put(SoftReference<CacheElement<K, V>> element);

    SoftReference<CacheElement<K, V>> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
}
