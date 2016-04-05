package com.rxc.dao;


import com.rxc.lang.tuple.T2;

import java.util.Collection;

/**
 * A Dao interface that abstracts
 * @param <K>
 * @param <T>
 * @param <C>
 */
public interface QueryDao<K, T, C> {
    /**
     * Get
     * @param context
     * @param id
     * @return instance of the entity data class
     * @throws NotFoundException
     */
   T getById(C context, K id);

    /**
     * Return a Collection of all the data that match a given query specification
     * @param context
     * @param queryData
     * @return
     */
    Collection<T2<K, T>> query(C context, QueryData<T> queryData);

    /**
     * A query that can return some meta data or function for a given query. For example,
     * could be used to implement a count for query type function.
     * @param context
     * @param queryData
     * @param <R>
     * @return
     */
   <R > R queryMeta(C context, QueryData<R> queryData);
}
