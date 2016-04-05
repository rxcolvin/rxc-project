package com.rxc.ui;

import com.rxc.lang.functional.F1;
import com.rxc.lang.tuple.T3;

/**
 *  Simple Edit field
 */
public interface UIEditField extends UIComponent {
  enum Status {

    EMPTY("EMPTY"), //Never been edited
    VALID("VALID"), //
    PARTIAL("PARTIAL"), // No completely valid but doesn't contain any illegal constructs. Only used while in focus.
    INVALID("INVALID");

    private final String value;

    private Status(final String value) {
      this.value = value;
    }


  }
  void readonly(boolean flag);
  boolean readonly();

  boolean hasFocus();

  /**
   * Provide a callback function that takes the latest version
   *
   * @param t
   */
  void listener(F1<T3<String, Status, String>, String> t);

  void value(String value);

  String value();

  @Override default Class<? extends UIComponent> uiType() {
    return UIEditField.class;
  }
}

