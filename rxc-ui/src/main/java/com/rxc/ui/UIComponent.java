package com.rxc.ui;

/**
 * Base class for all components in the abstract UI framework
 */
public interface UIComponent {
  String name();

  Class<? extends UIComponent> uiType();
}
