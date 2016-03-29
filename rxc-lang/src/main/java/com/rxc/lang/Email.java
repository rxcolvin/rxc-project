package com.rxc.lang;

import java.util.UUID;

/**
 * Created by richard.colvin on 29/03/2016.
 */
public class Email {
    interface $ {
        LString parseError = new LString("%s is not a valid email address", UUID.fromString("c0d5c3b0-77e6-44c9-9fb5-cd632f21849b"));
    }
    public final String domain;
    public final String name;
    private final String toString;

    public Email(String domain, String name) {
        this.domain = domain;
        this.name = name;
        toString = name + "@" + domain;
    }

    public String toString() {
        return toString;
    }

    public static Email fromString(String s) {
        String[] ss = s.split("@");
        if (ss.length != 2) {
            throw new LocalizedRTE($.parseError, s);
        }
        return new Email(ss[1], ss[0]);
    }
}

