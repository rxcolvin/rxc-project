package com.rxc.dao;

/**
 * A Dao abstraction that allows for parameterization of the Key type, the Data type and a context type. The Context
 * allows defined information, typically relating to the user and/or calling process, usually for audit purposes
 *
 * @param <K> Key Type
 * @param <T> Data Type
 * @param <C> Context Type
 */
public interface DataDao<K, T, C> {
    /**
     * Allows abstraction of the key creation process. This allows creation of keys that depjend
     * on the underlying technology or need to include Context or data type informat
     * ion.
     *
     * @param context Contains context information
     * @return a new, unique to the data type, key
     */
    K createKey(C context);

    /**
     * Creates a new representation of the data in the underlying data store, returning a copy of the data entity
     * perhaps augmented with server generated data, like timestamps.
     *
     * @param id      the id
     * @param entity
     * @param context
     * @return
     */
    T create(K id, T entity, C context);

    /**
     * Update the entity with the given key in te underlying data store.
     *
     * @param id
     * @param entity
     * @param context
     * @return
     */
    T update(K id, T entity, C context);

    /**
     * Remove the entity with the given id
     *
     * @param id
     * @param context
     */
    void remove(K id, C context);

    /**
     * Remove all entities that match a given query
     * @param id
     * @param queryData
     * @param context
     */
    void remove(QueryData<T> queryData, C context);
}
