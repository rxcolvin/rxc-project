package com.rxc.controller;

import com.rxc.lang.F1;
import com.rxc.lang.T2;
import com.rxc.meta.ValidState;
import com.rxc.ui.UIEditField;

/**
 * Created by richard on 22/11/2015.
 */
public interface FieldController<T> {

  Class<T> tType();

  void value(T value);

  T value();

  interface Factory<T> {
     FieldController<T> create(UIEditField ui, F1<T2<ValidState, String>, T> validator);
  }
}
