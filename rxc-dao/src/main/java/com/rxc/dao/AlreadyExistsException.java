package com.rxc.dao;

/**
 * Created by richard.colvin on 19/11/2015.
 */
public class AlreadyExistsException extends RuntimeException {
  public final Object key;

  public AlreadyExistsException(Object key) {
    super("Key Already Exists: key " + key);

    this.key = key;
  }
}
