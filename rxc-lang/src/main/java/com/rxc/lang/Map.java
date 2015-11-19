package com.rxc.lang;

import java.util.HashMap;

public class Map<K, T> {

  private final java.util.Map<K, T> map;

  public Map(Array<T> elements, F1<K, T> f) {
    map = new HashMap<>(elements.size());
    elements.forEach((t) -> map.put(f.apply(t), t));
  }

  public final T $(K k) {
    return map.get(k);
  }
}
