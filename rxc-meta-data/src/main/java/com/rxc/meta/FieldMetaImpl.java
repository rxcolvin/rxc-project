package com.rxc.meta;

import com.rxc.lang.Builder;
import com.rxc.lang.F1;
import com.rxc.lang.F2V;


public final class FieldMetaImpl<T, X, B extends Builder<X>> implements FieldMeta<T, X, B> {

  private final FieldDef<T> fieldDef;
  private final F1<T, X>    getter;
  private final F2V<T, B>   setter;

  public FieldMetaImpl(FieldDef<T> fieldDef, F1<T, X> getter, F2V<T, B> setter) {
    this.fieldDef = fieldDef;
    this.getter = getter;
    this.setter = setter;
  }

  @Override
  public Class<T> tType() {
    return fieldDef.tType;
  }

  @Override
  public String name() {
    return fieldDef.name;
  }

  @Override
  public FieldValidator<T> validator() {
    return fieldDef.validator;
  }

  @Override
  public F1<T, X> getter() {
    return getter;
  }

  @Override
  public F2V<T, B> setter() {
    return setter;
  }
}
