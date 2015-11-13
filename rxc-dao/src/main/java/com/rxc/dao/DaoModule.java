package com.rxc.dao;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public interface DaoModule {

    <T, K, C> RxDao<T, K, C> rxDoa(Class<T> tClass, Class<K> kClass, Class<C> cClass);

}
