package com.rxc.logging;

import com.rxc.lang.F0;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * Created by richard.colvin on 03/11/2015.
 */
public class WriterLogStream implements Logger.LogStream {

  private final String name;
  private final PrintWriter writer;

  public boolean isActive = true;

  public WriterLogStream(final String name, final Writer writer) {
    this.name = name;
    this.writer = new PrintWriter(writer);
  }

  @Override
  public void write(final F0<String> log) {
    if (isActive) {
      writer.print(name);
      writer.print(" ");
      writer.print(log.apply());
      writer.println();
      writer.flush();
    }
  }

  @Override
  public void write(final F0<String> log, final F0<Exception> e) {
    if (isActive) {
      writer.print(name);
      writer.print(" ");
      writer.print(log.apply());
      writer.println();
      e.apply().printStackTrace(writer);
      writer.flush();
    }
  }

}

