package com.rxc.dao;

/**
 * Created by richard.colvin on 19/11/2015.
 */
public class NotFoundException extends RuntimeException {
  public final Object key;

  public NotFoundException(Object key) {
    super("Not found: key " + key);

    this.key = key;
  }
}
