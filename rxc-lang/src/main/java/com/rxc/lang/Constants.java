package com.rxc.lang;

import java.util.function.Function;

/**
 * Created by richard.colvin on 31/03/2016.
 */
public class Constants {
    public static <E> Function<E, Boolean> trueF() {
        return x -> true;
    }
}
