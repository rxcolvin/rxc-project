package com.rxc.app;

/**
 * Created by richard on 05/12/2015.
 */
public interface LifeCycle {

  void start();

  void stop();

  /**
   * @return true, if no unsaved data
   */
  boolean isSafeToStop();
}
