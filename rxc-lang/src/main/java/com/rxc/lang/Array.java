package com.rxc.lang;


import java.util.Iterator;

public class Array<T> implements Iterable<T> {
    private final T[] ts;

  private Array(T... ts) {
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
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {

      int ref = 0;

      @Override
      public boolean hasNext() {
        return ref < ts.length;
      }

      @Override
      public T next() {
        return ts[ref++];
      }
    }

  public static <T> Array<T> $$(T... ts) {
    return new Array<T>(ts);
    }
}
