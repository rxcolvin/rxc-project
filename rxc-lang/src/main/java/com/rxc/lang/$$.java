package com.rxc.lang;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class $$<T> implements Iterable<T> {
  private final List<T> ts;

  @SafeVarargs
  private $$(T... ts) {
    this.ts = Arrays.asList(ts);
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

  public <X> $$<X> map(F1<X, T> f) {
    List<X> xs = new ArrayList<>(ts.size());
    for (T t : ts) {
      xs.add(f.$(t));
    }
    return new $$<>(xs);
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
   * @return
   */

  public static <T> $$<T> $$(T... ts) {
    return new $$<T>(ts);
  }

  private  final static $$<?> $0 = new $$<>();

  public static <X> $$<X> $0() {
    return ($$<X>) $0;
  }

}