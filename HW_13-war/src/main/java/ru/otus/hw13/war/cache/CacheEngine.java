package ru.otus.hw13.war.cache;

import java.lang.ref.SoftReference;

public interface CacheEngine<K, V> {

    void put(SoftReference<CacheElement<K, V>> element);

    SoftReference<CacheElement<K, V>> get(K key);

    int getHitCount();

    int getMissCount();

    int getElementsCount();

    void dispose();

}
