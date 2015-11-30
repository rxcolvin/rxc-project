package com.rxc.meta;

import com.rxc.lang.Builder;
import com.rxc.lang.F1;
import com.rxc.lang.F2V;

public interface FieldMeta<T, X, B extends Builder<X>> {
  Class<T> tType();

  String name();

  FieldValidator<T> validator();

  F1<T, X> getter();

  F2V<T, B> setter();
}
