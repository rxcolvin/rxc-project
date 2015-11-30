package com.rxc.lang;

/**
 * Created by richard.colvin on 20/10/2015.
 */
public class T2<P1, P2> {
  public final P1 _1;
  public final P2 _2;

  public T2(final P1 p1, final P2 p2) {
    _1 = p1;
    _2 = p2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    T2<?, ?> t2 = (T2<?, ?>) o;

    if (!_1.equals(t2._1)) {
      return false;
    }
    return _2.equals(t2._2);

  }

  @Override
  public int hashCode() {
    int result = _1.hashCode();
    result = 31 * result + _2.hashCode();
    return result;
  }
}
