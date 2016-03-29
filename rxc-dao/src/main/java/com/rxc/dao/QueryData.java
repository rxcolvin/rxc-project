package com.rxc.dao;


public abstract class QueryData<T> {
  public final Class<T> typeOf_T;

  protected QueryData(final Class<T> typeOf_T) {
    this.typeOf_T = typeOf_T;
  }
}
