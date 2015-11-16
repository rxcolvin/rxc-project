package com.rxc.ui.controller;

import com.rxc.lang.*;
import com.rxc.meta.FieldMeta;
import com.rxc.meta.ValidState;
import com.rxc.ui.model.UIStringEditField;

public class StringFieldController {

    private final UIStringEditField ui;
    private final FieldMeta<String> fieldMeta;
    private final F1V<String> setter;
    private final F0<String> getter;

    public StringFieldController(UIStringEditField ui, FieldMeta<String> fieldMeta, F1V<String> setter, F0<String> getter
    ) {
        this.ui = ui;
        this.fieldMeta = fieldMeta;
        this.setter = setter;
        this.getter = getter;
    }

    public void start() {
        ui.value(getter.apply());
        ui.listener(new UIStringEditField.Listener() {
            @Override
            public T3<String, UIStringEditField.Status, String> proposeChange(String value) {
                return null;
            }
        });
    }

    public T3<String, UIStringEditField.Status, String> validate(String value) {
        T2<ValidState, String> v = fieldMeta.validator().validate(value);
        if (v._1 != ValidState.ERROR) {
            setter.apply(value);
        }
        return Tuple.$(v._1, makeStatus(v._1), v._2);
    }

    private UIStringEditField.Status makeStatus(ValidState validState) {
        return UIStringEditField.Status.VALID;
    }
}
