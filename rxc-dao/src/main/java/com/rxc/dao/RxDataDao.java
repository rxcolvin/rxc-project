package com.rxc.dao;


import rx.Observable;

public interface RxDataDao<K, T, C> {
  Observable<K> createKey(C context);
  Observable<T> create(K id, T entity, C context);
  Observable<T> update(K id, T entity, C context);
  Observable<Void> remove(K id, C context);
}

