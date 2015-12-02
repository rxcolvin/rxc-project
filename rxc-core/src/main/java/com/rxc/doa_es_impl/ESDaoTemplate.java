package com.rxc.doa_es_impl;

import com.rxc.dao.Dao;
import com.rxc.dao.QueryData;
import com.rxc.lang.T2;

import java.util.Collection;
import java.util.function.Function;

/**
 * Created by richard.colvin on 02/11/2015.
 */
public class ESDaoTemplate<K, T, C> implements Dao<K, T, C> {

  private final Class<K> class_T;
  private final Class<T> class_K;
  private final Class<C> class_C;
  private final Function<T, String> toJson;
  private final Function<T, String> fromJson;
  private final ESHelper esHelper;

  public ESDaoTemplate(final Class<K> class_t, final Class<T> class_k, final Class<C> class_c, final Function<T, String> toJson, final Function<T, String> fromJson, final ESHelper esHelper) {
    class_T = class_t;
    class_K = class_k;
    class_C = class_c;
    this.toJson = toJson;
    this.fromJson = fromJson;
    this.esHelper = esHelper;
  }


  @Override
  public K createKey(final C context) {
    return null;
  }

  @Override
  public T create(final K id, final T entity, final C context) {
    return null;
  }

  @Override
  public T update(final K id, final T entity, final C context) {
    try {
      String json = toJson.apply(entity);
      esHelper.insert("" + id, json);
      return entity;
    } catch (Exception e) {
      rethrow(e);
      return null;//Unreachable
    }
  }

  @Override
  public void remove(final K id, final C context) {

  }


  @Override
  public T getById(final K id) {
    return null;
  }

  @Override
  public Collection<T2<K, T>> query(final C context, final QueryData<T> queryData) {
    return null;
  }

  @Override
  public void installSchema() {

  }

  private void rethrow(Exception e) throws RuntimeException {
    if (e instanceof RuntimeException) {
      throw (RuntimeException) e;
    } else {
      throw new RuntimeException(e);
    }
  }
}
