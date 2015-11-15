package com.rxc.meta;

import com.rxc.lang.Array;

/**
 * Created by richard on 15/11/2015.
 */
public interface EntityMeta {
    String name();

    FieldMeta fieldMeta(final String name);

    Array<FieldMeta> fieldMetas();
}
