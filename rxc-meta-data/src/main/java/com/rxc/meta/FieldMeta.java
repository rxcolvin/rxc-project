package com.rxc.meta;

import com.rxc.lang.Builder;
import com.rxc.lang.F1;
import com.rxc.lang.F2V;
import com.rxc.lang.T2;


public final class FieldMeta<T, X, B extends Builder<X>>  {

  private final FieldDef<T> fieldDef;
  private final F1<T, X>    getter;
  private final F2V<T, B>   setter;

  public FieldMeta(FieldDef<T> fieldDef, F1<T, X> getter, F2V<T, B> setter) {
    this.fieldDef = fieldDef;
    this.getter = getter;
    this.setter = setter;
  }

  public Class<T> tType() {
    return fieldDef.tType;
  }

  public String name() {
    return fieldDef.name;
  }

  public F1<T2<ValidState, String>, T>  validator() {
    return fieldDef.validator;
  }

  public F1<T, X> getter() {
    return getter;
  }

  public F2V<T, B> setter() {
    return setter;
  }

  public static <T, X, B extends Builder<X>> FieldMeta<T, X, B> FieldMeta(FieldDef<T> fieldDef, F1<T, X> getter, F2V<T, B> setter) {
    return new FieldMeta<>(fieldDef, getter, setter);
  }
}
