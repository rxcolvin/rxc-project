package com.rxc.lang;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Array<T> implements Iterable<T> {
  private final List<T> ts;

  @SafeVarargs
  private Array(T... ts) {
    this.ts = Arrays.asList(ts);
  }

  private Array(List<T> ts) {
    this.ts = ts;
  }


  public final int size() {
    return ts.size();
  }

  public T at(int index) {
    return ts.get(index);
  }

  public <X> Array<X> map(F1<X, T> f) {
    List<X> xs = new ArrayList<>(ts.size());
    for (T t : ts) {
      xs.add(f.$(t));
    }
    return new Array<>(xs);
  }


  @Override
  public Iterator<T> iterator() {
    return new MyIterator();
  }

  private class MyIterator implements Iterator<T> {

    int ref = 0;

    @Override
    public boolean hasNext() {
      return ref < ts.size();
    }

    @Override
    public T next() {
      return ts.get(ref++);
    }
  }

  /**
   *
   * @param f
   * @param classX have to clue up the compile because the inference is shit
   * @param <X>
   * @return
   */

  public static <T> Array<T> $$(T... ts) {
    return new Array<T>(ts);
  }


}
