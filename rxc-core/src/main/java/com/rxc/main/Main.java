package com.rxc.main;

import com.rxc.logging.Logger;
import com.rxc.logging.SyncPwLogStream;
import com.rxc.logging.WriterLogStream;
import com.rxc.rest.RestDataModel;
import com.rxc.rest.RestEngine;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by richard.colvin on 21/10/2015.
 */
public class Main {

  public static void main(String[] args) {
    PrintWriter writer = new PrintWriter(System.out);
    Logger      logger = new Logger(new SyncPwLogStream("DEBUG", writer, null, null), new SyncPwLogStream("INFO",
        writer, null, null));
    RestEngine engine = new RestEngine("Engine", 8080, logger, new EchoDataModel("foo"), new EchoDataModel("bar"));
    engine.start();
  }

  private static class EchoDataModel implements RestDataModel {
    private final String name;

    private EchoDataModel(final String name) {
      this.name = name;
    }

    @Override
    public String entityName() {
      return name;
    }

    @Override
    public String get(final Map<String, String[]> parameterMap) {
      return "Hello From " + name + " echo=" + parameterMap.get("echo")[0];
    }
  }
}
