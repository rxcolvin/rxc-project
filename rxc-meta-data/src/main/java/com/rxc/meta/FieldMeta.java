package com.rxc.meta;

import com.rxc.lang.Builder;
import com.rxc.lang.F1;
import com.rxc.lang.F2V;
import com.rxc.lang.T2;

public interface FieldMeta<T, X, B extends Builder<X>> {
  Class<T> tType();

  String name();

  F1<T2<ValidState, String> ,T> validator();

  F1<T, X> getter();

  F2V<T, B> setter();
}
