package com.rxc.controller;

import com.rxc.lang.*;
import com.rxc.meta.ValidState;
import com.rxc.ui.UIEditField;

import java.util.function.Function;

import static com.rxc.lang.Tuple.*;

public class EditFieldController<T> {

    private final UIEditField ui;
    private final Function<T, T2<ValidState, String>> validator;
    private final Function<String, T> fromString;
    private final Function<T, String> toString;
    private final Class<T> tType;

    public EditFieldController(
            final UIEditField ui,
            final Function<T, T2<ValidState, String>> validator,
            final Function<String, T> fromString,
            final Function<T, String> toString,
            final Class<T> tType) {
        this.ui = ui;
        this.validator = validator;
        this.fromString = fromString;

        this.toString = toString;
        this.tType = tType;
        ui.listener(this::validate);
    }

    public Class<T> tType() {
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
            final T value = fromString.apply(s);
            final T2<ValidState, String> v = validator.apply(value);
            return $(s, makeStatus(v._1), v._2);
        } catch (Exception e) {
            return $(s, UIEditField.Status.INVALID, e.getMessage());
        }
    }

    private UIEditField.Status makeStatus(ValidState validState) {
        return UIEditField.Status.VALID;
    }


    public static class Factory<T> {

        private final Function<String, T> fromString;
        private final Function<T, String> toString;
        public final Class<T> tType;

        public Factory(
                final Function<String, T> fromString,
                final Function<T, String> toString,
                final Class<T> tType
        ) {
            this.fromString = fromString;
            this.toString = toString;
            this.tType = tType;
        }

        public EditFieldController<T> create(
                final UIEditField ui,
                final Function<T, T2<ValidState, String>> validator
        ) {
            return new EditFieldController<T>(ui, validator, fromString, toString, tType);
        }
    }
}
