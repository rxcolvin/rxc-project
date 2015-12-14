package com.rxc.logging;

import com.rxc.lang.F0;
import com.rxc.lang.F1;
import com.rxc.lang.F2;

import java.io.PrintWriter;

/**
 * Created by richard on 05/12/2015.
 */
public class SyncPwLogStream implements LogStream {

  private boolean isEnabled = true;

  private final String name;

  private final PrintWriter pw;
  private final F2<String, String, F0<String>> f;
  private final F1<String, Throwable>          ep;


  public SyncPwLogStream(String name, PrintWriter pw, F2<String, String, F0<String>> f, F1<String, Throwable> ep) {
    this.name = name;
    this.pw = pw;
    this.f = f;
    this.ep = ep;
  }

  @Override public final void $(F0<String> log) {
    if (isEnabled) {
      synchronized (pw) {
        pw.write(f.$(name, log));
        pw.write('\n');
        pw.flush();
      }
    }
  }

  @Override public final void $(F0<String> log, F0<Throwable> e) {
    if (isEnabled) {
      synchronized (pw) {
        pw.write(f.$(name, log));
        pw.write('\n');
        pw.write(ep.$(e.$()));
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
