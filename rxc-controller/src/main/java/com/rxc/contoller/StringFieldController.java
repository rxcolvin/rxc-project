package com.rxc.contoller;

import com.rxc.lang.*;
import com.rxc.meta.ValidState;
import com.rxc.ui.UIEditField;

import static com.rxc.lang.Tuple.*;

public class StringFieldController<T> implements FieldController<T, String, UIEditField> {

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

  @Override
  public Class<String> vType() {
    return String.class;
  }

  @Override public Class<T> tType() {
    return tType;
  }


  public void value(T value) {
    ui.value(toString.apply(value));
  }

  public T value() {
    return fromString.apply(ui.value());
  }

  private T3<String, UIEditField.Status, String> validate(String s) {
    try {
      T value = fromString.apply(s);
      T2<ValidState, String> v = validator.apply(value);
      return $(s, makeStatus(v._1), v._2);
    } catch (Exception e) {
      return $(s, UIEditField.Status.INVALID, e.getMessage());
    }
  }

  private UIEditField.Status makeStatus(ValidState validState) {
    return UIEditField.Status.VALID;
  }



}
