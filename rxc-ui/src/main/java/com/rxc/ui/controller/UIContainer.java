package com.rxc.ui.controller;

/**
 * Created by richard.colvin on 20/11/2015.
 */
public interface UIContainer extends UIComponent {
  <T extends UIComponent> T component(String name, Class<T> type);

}
