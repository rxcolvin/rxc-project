package com.rxc.meta;

import com.rxc.lang.F1;
import com.rxc.lang.T2;

public class FieldDef<T> {
  public final Class<T> tType;
  public final String   name;

  public final F1<T2<ValidState, String>, T> validator;


  public FieldDef(final Class<T> tType, final String name, final F1<T2<ValidState, String>, T> validator) {
    this.tType = tType;
    this.name = name;
    this.validator = validator;
  }
}
