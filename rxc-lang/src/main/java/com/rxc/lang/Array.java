package com.rxc.lang;


import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by richard on 15/11/2015.
 */
public class Array<T> implements Iterable<T> {
    private final T[] ts;

    public Array(T... ts) {
        this.ts = ts;
    }


    public final int size() {
        return ts.length;
    }

    public T at(int index) {
        return ts[index];
    }


    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super T> action) {

    }

    @Override
    public Spliterator<T> spliterator() {
        return null;
    }
}
