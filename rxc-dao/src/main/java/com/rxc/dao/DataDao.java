package com.rxc.dao;


public interface DataDao<K, T, C> {
  K createKey(C context);
  T create(K id, T entity, C context);
  T update(K id, T entity, C context);
  void remove(K id, C context);
  void installSchema();
}
