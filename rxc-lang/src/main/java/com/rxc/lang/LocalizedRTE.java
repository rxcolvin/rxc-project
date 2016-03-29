package com.rxc.lang;

/**
 * Created by richard.colvin on 29/03/2016.
 */
public class LocalizedRTE extends RuntimeException {
    public final LString message;
    public final Object[] params;


    public LocalizedRTE(LString message, Object... params) {
        this.message = message;
        this.params = params;
    }
}
