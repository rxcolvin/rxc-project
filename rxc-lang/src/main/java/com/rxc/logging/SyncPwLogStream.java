package com.rxc.logging;


import java.io.PrintWriter;
import java.util.function.Supplier;


/**
 * Created by richard on 05/12/2015.
 */
public class SyncPwLogStream implements LogStream {

    private final WriterLogStream writerLogStream;


    public SyncPwLogStream(String name, PrintWriter pw) {
        writerLogStream = new WriterLogStream(name, pw);
    }

    @Override
    public synchronized void $(Supplier<Object> log) {
        writerLogStream.$(log);
    }

    @Override
    public boolean isEnabled() {
        return writerLogStream.isEnabled();
    }

    @Override
    public void isEnabled(boolean flag) {
        writerLogStream.isEnabled(flag);
    }
}
