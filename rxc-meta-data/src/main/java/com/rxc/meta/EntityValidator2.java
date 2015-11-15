package com.rxc.meta;

import com.rxc.lang.T2;

/**
 * Created by richard on 15/11/2015.
 */
public interface EntityValidator2<P1, P2> {
    FieldMeta<P1> p1Type();

    FieldMeta<P2> p2Type();

    T2<ValidState, String> validate(P1 _1, P2 _2);
}
