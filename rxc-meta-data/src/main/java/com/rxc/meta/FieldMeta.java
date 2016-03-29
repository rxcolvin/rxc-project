package com.rxc.meta;

import com.rxc.lang.T2;

import java.util.function.BiConsumer;
import java.util.function.Function;


public final class FieldMeta<T, E, B> {

    public final FieldDef<T> fieldDef;
    public final Function<E, T> getter;
    public final BiConsumer<T, B> setter;

    public FieldMeta(FieldDef<T> fieldDef, Function<E, T> getter, BiConsumer<T, B> setter) {
        this.fieldDef = fieldDef;
        this.getter = getter;
        this.setter = setter;
    }

    public Class<T> tType() {
        return fieldDef.tType;
    }

    public String name() {
        return fieldDef.name;
    }

    public Function<T, T2<ValidState, String>> validator() {
        return fieldDef.validator;
    }


    public Function<E, T> getter() {
        return getter;
    }

    public BiConsumer<T, B> setter() {
        return setter;
    }

    public static <T, E, B> FieldMeta<T, E, B> FieldMeta(
            final FieldDef<T> fieldDef,
            final Function<E, T> getter,
            final BiConsumer<T, B> setter) {
        return new FieldMeta<>(fieldDef, getter, setter);
    }
}
