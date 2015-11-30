package com.rxc.lang;

import java.util.HashMap;

public class Map<K, T> {

  private final java.util.Map<K, T> map;
  private final F1<T, K> whenNotFound;

  private Map(Array<T2<K, T>> ts, F1<T, K> whenNotFound) {
    this.whenNotFound = whenNotFound;
    map = new HashMap<>(ts.size());
    ts.forEach((t) -> map.put(t._1, t._2));

  }

  private Map(Array<T> elements, F1<K, T> f, F1<T, K> whenNotFound) {
    this.whenNotFound = whenNotFound;
    map = new HashMap<>(elements.size());
    elements.forEach((t) -> map.put(f.apply(t), t));
  }

  private <X> Map(Array<X> elements, F1<K, X> f1, F1<T, X> f2, F1<T, K> whenNotFound) {
    this.whenNotFound = whenNotFound;
    map = new HashMap<>(elements.size());
    elements.forEach((t) -> map.put(f1.apply(t), f2.apply(t)));
  }



  public final T $(K k) {

    T ret = map.get(k);
    if (ret == null) {
      ret = whenNotFound.apply(k);
    }
    return ret;
  }


  public static <K, T> Map<K, T> $_(Array<T2<K, T>> ts) {
    return new Map<K, T>(ts, null);
  }

  public static <K, T> Map<K, T> $_(F1<T, K> whenNotFound, Array<T> ts, F1<K, T> f) {
    return new Map<K, T>(ts, f, whenNotFound);
  }

  public static <K, T> Map<K, T> $_(Array<T> ts, F1<K, T> f) {
    return new Map<K, T>(ts, f, null);
  }


  public static <K, T, X> Map<K, T> $_(Array<X> ts, F1<K, X> f1, F1<T, X> f2) {
    return new Map<K, T>(ts, f1, f2, null);
  }
}
