package com.rxc.lang;

/**
 * Immutable Map Of Maps : shit implementation
 * TODO: Proper implementation.
 */
public class Map2<K1, K2, V> {

  private final $$<T3<K1, K1, V>> ts;

  private Map2($$<T3<K1, K1, V>> ts) {
    this.ts = ts;
  }

  public final V $(K1 k1, K2 k2) {
    for (T3<K1, K1, V> t : ts) {
      if (k1.equals(t._1) && k2.equals(t._2)) {
        return t._3;
      }
    }
    return null;
  }

  public static <K1, K2, V> Map2<K1, K2, V> $__($$<T3<K1, K1, V>> ts) {
    return new Map2<K1, K2, V>(ts);
  }

}
