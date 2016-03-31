package com.rxc.meta;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.rxc.lang.Constants.trueF;


public final class FieldMeta<T, E, B> {

    public final FieldDef<T> fieldDef;
    public final T defaultValue;
    public final Function<E, Boolean> hasValue;
    public final Function<E, T> getter;
    public final BiConsumer<T, B> setter;
    public final boolean isMandatory;

    private FieldMeta(
            final FieldDef<T> fieldDef,
            final boolean isMandatory,
            final T defaultValue,
            final Function<E, Boolean> hasValue,
            final Function<E, T> getter,
            final BiConsumer<T, B> setter
    ) {
        this.fieldDef = fieldDef;
        this.isMandatory = isMandatory;
        this.defaultValue = defaultValue;
        this.hasValue = hasValue;
        this.getter = getter;
        this.setter = setter;
    }

    public static <T, E, B> FieldMeta<T, E, B> FieldMeta(
            final FieldDef<T> fieldDef,
            final boolean isMandatory,
            final T defaultValue,
            final Function<E, Boolean> hasValue,
            final Function<E, T> getter,
            final BiConsumer<T, B> setter
    ) {
        return new FieldMeta<>(fieldDef, isMandatory, defaultValue,hasValue, getter, setter);
    }

    public static <T, E, B> FieldMeta<T, E, B> FieldMeta(
            final FieldDef<T> fieldDef,
            final Function<E, T> getter,
            final BiConsumer<T, B> setter
    ) {
        return new FieldMeta<>(fieldDef, true, null, trueF(), getter, setter);
    }

}
