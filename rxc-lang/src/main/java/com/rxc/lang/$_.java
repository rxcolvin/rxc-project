package com.rxc.lang;

import java.util.HashMap;

public class $_<K, T> {

    private final java.util.Map<K, T> map;
    private final F1<T, K> whenNotFound;

    private $_($$<T2<K, T>> ts, F1<T, K> whenNotFound) {
        this.whenNotFound = whenNotFound;
        map = new HashMap<>(ts.size());
        ts.forEach((t) -> map.put(t._1, t._2));

    }

    private $_($$<T> elements, F1<K, T> f, F1<T, K> whenNotFound) {
        this.whenNotFound = whenNotFound;
        map = new HashMap<>(elements.size());
        elements.forEach((t) -> map.put(f.$(t), t));
    }

    private <X> $_($$<X> elements, F1<K, X> f1, F1<T, X> f2, F1<T, K> whenNotFound) {
        this.whenNotFound = whenNotFound;
        map = new HashMap<>(elements.size());
        elements.forEach((t) -> map.put(f1.$(t), f2.$(t)));
    }


    public final T $(K k) {

        T ret = map.get(k);
        if (ret == null && whenNotFound != null) {
            ret = whenNotFound.$(k);
        }
        return ret;
    }

    public final boolean isDefinedFor(K k) {
        return map.containsKey(k);
    }

    public static <K, T> $_<K, T> $_($$<T2<K, T>> ts) {
        return new $_<K, T>(ts, null);
    }

    public static <K, T> $_<K, T> $_(F1<T, K> whenNotFound, $$<T> ts, F1<K, T> f) {
        return new $_<K, T>(ts, f, whenNotFound);
    }

    public static <K, T> $_<K, T> $_($$<T> ts, F1<K, T> f) {
        return new $_<K, T>(ts, f, null);
    }


    public static <K, T, X> $_<K, T> $_($$<X> ts, F1<K, X> f1, F1<T, X> f2) {
        return new $_<K, T>(ts, f1, f2, null);
    }
}
