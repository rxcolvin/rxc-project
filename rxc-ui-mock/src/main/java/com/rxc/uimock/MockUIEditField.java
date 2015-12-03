package com.rxc.uimock;

import com.rxc.lang.F1;
import com.rxc.lang.T3;
import com.rxc.ui.UIEditField;

public class MockUIEditField implements UIEditField {
  private final String name;

  private boolean flag;
  private boolean readonly;
  private boolean hasFocus;
  private F1<T3<String, Status, String>, String> listener;

  public String value;
  public Status status;
  public String msg;

  MockUIEditField(final String name) {
    this.name = name;
  }

  @Override
  public void readonly(final boolean flag) {
    this.flag = flag;
  }

  @Override
  public boolean readonly() {
    return readonly;
  }

  @Override
  public boolean hasFocus() {
    return hasFocus;
  }

  @Override
  public void listener(final F1<T3<String, Status, String>, String> t) {
    this.listener = t;
  }

  @Override
  public void value(final String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return value;
  }

  @Override
  public String name() {
    return name;
  }

  public void mockUpdate(String v) {
    if (listener != null) {
      final T3<String, Status, String> t3 = listener.$(v);
      value = t3._1;
      status = t3._2;
      msg = t3._3;
    }
  }

  public static MockUIEditField $(String name) {
    return new MockUIEditField(name);
  }
}
