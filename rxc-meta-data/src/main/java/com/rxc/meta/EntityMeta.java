package com.rxc.meta;

import com.rxc.lang.$$;
import com.rxc.lang.Builder;
import com.rxc.lang.F0;
import com.rxc.lang.$_;

import static com.rxc.lang.$_.$_;

/**
 * TODO: Allow versioning of fields somehow?
 *
 * @param <T>
 * @param <B>
 */
public final class EntityMeta<T, B extends Builder<T>> {

  public final  String                          name;
  public final  Class<T>                        tType;
  private final $$<FieldMeta<?, T, B>> fieldMetas;
  public final $_<String, FieldMeta<?, T, B>> fieldMetaMap;
  public final  F0<B>                           builderFactory;

  public EntityMeta(String name, Class<T> tType, $$<FieldMeta<?, T, B>> fieldMetas, F0<B> builderFactory) {
    this.name = name;
    this.tType = tType;
    this.fieldMetas = fieldMetas;
    this.builderFactory = builderFactory;
    fieldMetaMap = $_(fieldMetas, x -> x.name());
  }


  public FieldMeta<?, T, B> fieldMeta(final String name) {
    return fieldMetaMap.$(name);
  }

  public $$<FieldMeta<?, T, B>> fieldMetas() {
    return fieldMetas;
  }

}
