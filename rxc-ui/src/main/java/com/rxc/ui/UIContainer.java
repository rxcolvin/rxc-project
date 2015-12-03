package com.rxc.ui;

public interface UIContainer extends UIComponent {
  UIEditField editField(String name);
  UIContainer container(String name);
  UIAction action(String name);

  default Class<? extends UIComponent> uiType()  {
    return UIContainer.class;
  }
}
