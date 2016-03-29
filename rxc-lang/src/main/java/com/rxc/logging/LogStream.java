package com.rxc.logging;

import com.rxc.lang.F0;
import com.rxc.lang.F1;

import java.util.function.Supplier;

/**
 * Created by richard on 05/12/2015.
 */
public interface LogStream {
  void $(Supplier<Object> log);

  boolean isEnabled();

  void isEnabled(boolean flag);
}
