package com.rxc.logging;

/**
 * TODO: decouple format & output
 */
public class Logger {

  public final LogStream debug;
  public final LogStream info;

  public Logger(final LogStream debug, final LogStream info) {
    this.debug = debug;
    this.info = info;
  }


}
