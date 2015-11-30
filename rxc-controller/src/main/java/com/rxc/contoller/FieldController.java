package com.rxc.contoller;

import com.rxc.ui.UIComponent;

/**
 * Created by richard on 22/11/2015.
 */
public interface FieldController<T, V, C extends UIComponent> {
  Class<V> vType();

  Class<T> tType();

  void value(T value);

  T value();

}
