package com.rxc.meta;

import com.rxc.lang.T2;

/**
 * Created by richard on 15/11/2015.
 */
public interface FieldValidator<T> {

    T2<ValidState, String> validate(T t);
}
