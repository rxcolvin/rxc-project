package com.rxc.meta;

import com.rxc.lang.collection.$$;
import com.rxc.lang.collection.$_;

import java.util.function.Supplier;

import static com.rxc.lang.collection.$_.$_;

/**
 * TODO: Allow versioning of fields somehow?
 *
 * @param <E>
 */
public final class EntityMeta<E, B extends Supplier<E>> {

    public final String name;
    public final Class<E> tType;
    private final $$<FieldMeta<?, E, B>> fieldMetas;
    public final $_<String, FieldMeta<?, E, B>> fieldMetaMap;
    public final Supplier<B> builderFactory;

    public EntityMeta(String name, Class<E> tType, $$<FieldMeta<?, E, B>> fieldMetas, Supplier<B> builderFactory) {
        this.name = name;
        this.tType = tType;
        this.fieldMetas = fieldMetas;
        this.builderFactory = builderFactory;
        fieldMetaMap = $_(fieldMetas, x -> x.fieldDef.name);
    }


    public FieldMeta<?, E, B> fieldMeta(final String name) {
        return fieldMetaMap.$(name);
    }

    public $$<FieldMeta<?, E, B>> fieldMetas() {
        return fieldMetas;
    }

}
