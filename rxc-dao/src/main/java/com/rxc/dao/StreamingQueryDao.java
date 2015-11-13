package com.rxc.dao;

import com.rxc.lang.T2;

import java.util.Collection;

/**
 * Created by richard.colvin on 12/11/2015.
 */
public interface StreamingQueryDao<K, T, C> {

  interface ItemDelta<T> {

  }

  interface ItemStream<T> {
    void onUpdate(T item);
    void onUpdate(ItemDelta<T> delta);
    void onRemoved();
    void onStateChanged();
  }

  interface CollectionStream<K, T> {
    void onUpdate(Collection<T2<K, T>> data);
    void onItemInserted(K key, T item, int position);
    void onItemRemoved(K key);
    void onItemUpdated(K key, T item);
    void inItemUpdated(K key, ItemDelta<T> delta);
  }

  void getById(K id, ItemStream<T> stream);
  void query(C context, QueryData<T> queryData, CollectionStream<K, T> stream);
}
