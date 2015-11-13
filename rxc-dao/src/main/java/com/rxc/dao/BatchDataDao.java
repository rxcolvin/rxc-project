package com.rxc.dao;

/**
 * Created by richard.colvin on 12/11/2015.
 */
public interface BatchDataDao<T, C> {
  void remove(QueryData<T> queryData, C context);

}
