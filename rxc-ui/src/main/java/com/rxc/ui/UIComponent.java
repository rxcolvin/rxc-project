package com.rxc.ui;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public interface UIComponent {
  String name();

  interface Factory<T extends UIComponent> {
      T create(String name);
  }

}
