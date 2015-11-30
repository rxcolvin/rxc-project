package com.rxc.ui;

public interface UIContainer {
  <X, T extends UIComponent<X>> T component(String name);

}
