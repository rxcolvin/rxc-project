package com.rxc.logging;

import com.rxc.lang.F0;

/**
 * Created by richard.colvin on 02/11/2015.
 */
public class Logger {

  public final LogStream debug;
  public final LogStream info;

  public Logger(final LogStream debug, final LogStream info) {
    this.debug = debug;
    this.info = info;
  }

  public static interface LogStream {
    void write(F0<String> log);
    void write(F0<String> log, F0<Exception> e);
   }
}
