package com.rxc.lang.collection;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * A Simple Immutable List
 * @param <T> element type
 */
public class $$<T> implements Iterable<T> {
    private final List<T> ts;

    @SafeVarargs
    private $$(T... ts) {
        this.ts = Arrays.asList(ts);
    }

    private $$($$<T>... tts) {
        ts = new ArrayList<>();
        for ($$<T> tt : tts) {
            for (T t : tt) {
                ts.add(t);
            }
        }
    }

    private $$(List<T> ts) {
        this.ts = ts;
    }

    public final int size() {
        return ts.size();
    }

    public T $(int index) {
        return ts.get(index);
    }

    public Stream<T> stream() {
        return ts.stream();
    }

    @Override
    public Iterator<T> iterator() {
        return ts.iterator();
    }

    /**
     * Construct from a varargs of T's
     * @return
     */
    public static <T> $$<T> $$(T... ts) {
        return new $$<T>(ts);
    }

    /**
     * Construct from a varargs of $$[T] - ie a list of lists
     * @param tts
     * @param <T>
     * @return
     */
    public static <T> $$<T> $$($$<T>... tts) {
        return new $$<T>(tts);
    }

    private final static $$<?> $0 = new $$<>();

    /** Empty $$ */
    public static <X> $$<X> $0() {
        return ($$<X>) $0;
    }

}