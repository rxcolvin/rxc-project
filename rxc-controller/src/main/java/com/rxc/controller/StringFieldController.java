package com.rxc.controller;

import com.rxc.lang.*;
import com.rxc.meta.ValidState;
import com.rxc.ui.UIEditField;

import static com.rxc.lang.Tuple.*;

public class StringFieldController<T> implements FieldController<T> {

  private final UIEditField                   ui;
  private final F1<T2<ValidState, String>, T> validator;
  private final F1<T, String>                 fromString;
  private final F1<String, T>                 toString;
  private final Class<T>                      tType;

  public StringFieldController(UIEditField ui, F1<T2<ValidState, String>, T> validator,
                               F1<T, String> fromString,
                               F1<String, T> toString,
                               Class<T> tType) {
    this.ui = ui;
    this.validator = validator;
    this.fromString = fromString;

    this.toString = toString;
    this.tType = tType;
    ui.listener(this::validate);
  }

   @Override public Class<T> tType() {
    return tType;
  }


  public void value(T value) {
    ui.value(toString.$(value));
  }

  public T value() {
    return fromString.$(ui.value());
  }

  private T3<String, UIEditField.Status, String> validate(String s) {
    try {
      T value = fromString.$(s);
      T2<ValidState, String> v = validator.$(value);
      return $(s, makeStatus(v._1), v._2);
    } catch (Exception e) {
      return $(s, UIEditField.Status.INVALID, e.getMessage());
    }
  }

  private UIEditField.Status makeStatus(ValidState validState) {
    return UIEditField.Status.VALID;
  }


  public static class Factory<T> implements FieldController.Factory<T> {

    private final F1<T,String> fromString;
    private final F1<String, T> toString;
    private final Class<T> tType;

    public Factory(final F1<T, String> fromString, final F1<String, T> toString, final Class<T> tType) {
      this.fromString = fromString;
      this.toString = toString;
      this.tType = tType;
    }

    @Override
    public FieldController<T> create(final UIEditField ui, final F1<T2<ValidState, String>, T> validator) {
      return new StringFieldController<T>(ui, validator, fromString, toString, tType);
    }
  }
}
