package com.rxc.dao;

/**
 * Created by richard.colvin on 02/11/2015.
 */
public interface RxDao<K, T, C>  extends RxDataDao<K, T, C>, RxQueryDao<K, T, C> {
}
