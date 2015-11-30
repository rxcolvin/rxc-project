package com.rxc.ui;

public interface UIContainer {
  UIEditField editField(String name);
  UIContainer container(String name);
  UIAction action(String name);
}
