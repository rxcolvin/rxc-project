package com.rxc.dao;


import com.rxc.lang.T2;

import java.util.Collection;

public interface QueryDao<K, T, C> {
   T getById(K id);
   Collection<T2<K, T>> query(C context, QueryData<T> queryData);
}
