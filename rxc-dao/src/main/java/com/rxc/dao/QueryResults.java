package com.rxc.dao;

import com.rxc.lang.tuple.T2;

import java.util.Collection;

/**
 * Created by richard.colvin on 11/01/2016.
 */
public interface  QueryResults<K, T> {
    Collection<T2<K, T>> data();
}
