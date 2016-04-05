package com.rxc.meta;

import com.rxc.lang.tuple.T2;

import java.util.function.Function;

public class FieldDef<T> {
  public final Class<T> tType;
  public final String   name;

  public final Function<T, T2<ValidState, String>> validator;


  public FieldDef(final Class<T> tType, final String name, final  Function<T, T2<ValidState, String>> validator) {
    this.tType = tType;
    this.name = name;
    this.validator = validator;
  }
}
