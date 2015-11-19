package com.rxc.lang;

/**
 * Created by richard.colvin on 19/11/2015.
 */
public class TimingImpl implements Timing {

  private final static long MILLIS_IN_DAY = 24 * 60 * 60 * 1000;
  @Override
  public final long now() {
    return System.currentTimeMillis();
  }

  @Override
  public final long today() {
    return (System.currentTimeMillis() / MILLIS_IN_DAY) * MILLIS_IN_DAY ;
  }
}
