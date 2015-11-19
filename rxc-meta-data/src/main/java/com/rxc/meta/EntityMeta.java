package com.rxc.meta;

import com.rxc.lang.Array;


public interface EntityMeta {
    String name();

    FieldMeta fieldMeta(final String name);

    Array<FieldMeta> fieldMetas();
}
