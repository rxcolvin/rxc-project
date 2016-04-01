package com.rxc.meta.util;

import com.rxc.lang.$_;
import com.rxc.lang.F1C;
import com.rxc.meta.EntityMeta;
import com.rxc.meta.FieldMeta;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by richard.colvin on 31/03/2016.
 */
public class EntityFromMap<E, B extends Supplier<E>>
        implements Function<$_<String, Object>, E> {

    private final EntityMeta<E, B> entityMeta;
    private final F1C<Object> converter;

    public EntityFromMap(
            final EntityMeta<E, B> entityMeta,
            final F1C<Object> converter

    ) {
        this.entityMeta = entityMeta;
        this.converter = converter;
    }

    public final E apply(
            final $_<String, Object> from
    ) {
        final B builder = entityMeta.builderFactory.get();
        for (final FieldMeta<?, E, B> fieldMeta : entityMeta.fieldMetas()) {
            final String name = fieldMeta.fieldDef.name;
            final BiConsumer<Object, B> bb = (BiConsumer<Object, B>) fieldMeta.setter;
            if (from.isDefinedFor(name)) {
                final Object fromValue = from.$(name);
                final Object toValue = converter.apply(fromValue, fieldMeta.fieldDef.tType);
                bb.accept(toValue, builder);
            } else {
                bb.accept(fieldMeta.defaultValue, builder);
            }
        }
        return builder.get();
    }
}
