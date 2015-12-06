package com.rxc.lang;

/**
 * Created by richard.colvin on 19/11/2015.
 */
public class Minutes implements Duration {
  private final long value;

  public Minutes(final long value) {
    this.value = value;
  }

  @Override
  public final long millis() {

    return value * 60 * 1000;
  }

  public static Minutes $(long value) {
    return new Minutes(value);
  }
}
