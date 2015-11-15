package com.rxc.ui.model;

import com.rxc.lang.T3;

/**
 * Created by richard.colvin on 12/11/2015.
 */
public interface UIStringEditField extends UIComponent {
  enum Status {

    EMPTY("EMPTY"), //Never been edited
    VALID("VALID"), //Not being edited and valid
    INVALID("INVALID");

    private final String value;

    private Status(final String value) {
      this.value = value;
    }


  }
  String value();
  void value(String value);
  void readonly(boolean flag);
  boolean readonly();
  void listener(Listener t);

  interface Listener {
    T3<String, Status, String> proposeChange(String value);
  }
}

