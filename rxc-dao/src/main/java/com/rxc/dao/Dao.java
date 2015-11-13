package com.rxc.dao;

/**
 * Created by richard.colvin on 02/11/2015.
 */
public interface Dao<K, T, C>  extends DataDao<K, T, C>, QueryDao<K, T, C> {
}
