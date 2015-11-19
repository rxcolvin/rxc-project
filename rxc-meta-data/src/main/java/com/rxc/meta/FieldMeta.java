package com.rxc.meta;

public class FieldMeta<T> {
    public final Class<T> tType;
    public final String name;

    public final FieldValidator<T> validator;


  public FieldMeta(final Class<T> tType, final String name, final FieldValidator<T> validator) {
    this.tType = tType;
    this.name = name;
    this.validator = validator;
  }
}
