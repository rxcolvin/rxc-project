package com.rxc.logging;

import com.rxc.lang.F0;

import java.io.PrintWriter;

/**
 * Created by richard on 05/12/2015.
 */
public class SyncPwLogStream implements LogStream {

  private boolean isEnabled = true;
  //TODO STACKTRACE VIEWS

  private final String name;

  private final PrintWriter pw;

  public SyncPwLogStream(String name, PrintWriter pw) {
    this.name = name;
    this.pw = pw;
  }

  @Override public void $(F0<String> log) {
    if (isEnabled) {
      synchronized (pw) {
        pw.write(log.$());
        pw.write('\n');
        pw.flush();
      }
    }
  }

  @Override public void $(F0<String> log, F0<Exception> e) {
    if (isEnabled) {
      synchronized (pw) {
        pw.write(log.$());
        pw.write('\n');
        e.$().printStackTrace(pw);
      }
    }

  }

  @Override public boolean isEnabled() {
    return isEnabled;
  }

  @Override public void isEnabled(boolean flag) {
    isEnabled = flag;
  }
}
