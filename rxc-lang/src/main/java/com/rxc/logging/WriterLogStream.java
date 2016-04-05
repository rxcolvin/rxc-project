package com.rxc.logging;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.function.Supplier;

/**
 * Created by richard.colvin on 03/11/2015.
 */
public class WriterLogStream implements LogStream {

  private final String name;
  private final PrintWriter writer;

  public boolean isActive = true;

  public WriterLogStream(final String name, final Writer writer) {
    this.name = name;
    this.writer = new PrintWriter(writer);
  }

  @Override
  public void $(Supplier<Object> log) {
    if (isActive) {
      writer.print(name);
      writer.print(" ");
      Object t = log.get();
      if (t instanceof Throwable) {
        Throwable tt = (Throwable) t;
        writer.println(tt.getMessage());
        tt.printStackTrace(writer);
      } else {
        writer.print(t);
      }
      writer.println();
      writer.flush();
    }

  }

  @Override
  public boolean isEnabled() {
    return isActive;
  }

  @Override
  public void isEnabled(boolean flag) {
    this.isActive = true;
  }


}

