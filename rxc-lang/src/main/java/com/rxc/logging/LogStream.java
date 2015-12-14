package com.rxc.logging;

import com.rxc.lang.F0;
import com.rxc.lang.F1;

/**
 * Created by richard on 05/12/2015.
 */
public interface LogStream {
  void $(F0<String> log);

  void $(F0<String> log, F0<Throwable> e);

  boolean isEnabled();

  void isEnabled(boolean flag);
}
