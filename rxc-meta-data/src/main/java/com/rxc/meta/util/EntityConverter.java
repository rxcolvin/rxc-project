package com.rxc.meta.util;

import com.rxc.lang.F1C;
import com.rxc.meta.EntityMeta;
import com.rxc.meta.FieldMeta;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Created by richard.colvin on 29/03/2016.
 */
public class EntityConverter<E, B extends Supplier<E>, F> {

    private final EntityMeta<E, B> entityMeta;
    private final EntityMeta<F, ?> fromEntityMeta;
    private final F1C<Object> converter;

    public EntityConverter(
            final EntityMeta<E, B> entityMeta,
            final EntityMeta<F, ?> fromEntityMeta,
            final F1C<Object> converter
    ) {
        this.entityMeta = entityMeta;
        this.fromEntityMeta = fromEntityMeta;
        this.converter = converter;
        //TODO: assert compatibility
    }

    public final E apply(F from) {

        final B builder = entityMeta.builderFactory.get();

        for (FieldMeta<?, E, B> fieldMeta : entityMeta.fieldMetas()) {
            final String name = fieldMeta.name();
            final FieldMeta<?, F, ?> fromFieldMeta = fromEntityMeta.fieldMetaMap.$(name);
            final Object fromValue = fromFieldMeta.getter.apply(from);
            final Object toValue = converter.apply(fromValue, fieldMeta.fieldDef.tType);
            final BiConsumer<Object, B> bb = (BiConsumer<Object, B>) fieldMeta.setter;
            bb.accept(toValue, builder);
        }
        return builder.get();
    }
}
