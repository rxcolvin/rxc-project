package com.rxc.meta;

/**
 * Created by richard on 15/11/2015.
 */
public interface FieldMeta<T> {
    Class<T> tType();
    String name();

    FieldValidator<T> validator();
}
