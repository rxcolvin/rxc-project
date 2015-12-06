package com.rxc.logging;

import com.rxc.lang.F0;

/**
 * Created by richard on 05/12/2015.
 */
public interface LogStream {
  void $(F0<String> log);

  void $(F0<String> log, F0<Exception> e);

  boolean isEnabled();

  void isEnabled(boolean flag);
}
