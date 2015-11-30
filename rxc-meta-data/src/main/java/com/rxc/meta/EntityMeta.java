package com.rxc.meta;

import com.rxc.lang.Array;
import com.rxc.lang.Builder;
import com.rxc.lang.F0;
import com.rxc.lang.Map;


public final class EntityMeta<T, B extends Builder<T>> {

  public final  String                          name;
  public final  Class<T>                        tType;
  private final Array<FieldMeta<?, T, B>>       fieldMetas;
  public final  Map<String, FieldMeta<?, T, B>> fieldMetaMap;
  public final  F0<B>                           builderFactory;

  public EntityMeta(String name, Class<T> tType, Array<FieldMeta<?, T, B>> fieldMetas, F0<B> builderFactory) {
    this.name = name;
    this.tType = tType;
    this.fieldMetas = fieldMetas;
    this.builderFactory = builderFactory;
    fieldMetaMap = Map.$_(fieldMetas, x -> x.name());
  }


  public FieldMeta<?, T, B> fieldMeta(final String name) {
    return fieldMetaMap.$(name);
  }

  public Array<FieldMeta<?, T, B>> fieldMetas() {
    return fieldMetas;
  }

}
