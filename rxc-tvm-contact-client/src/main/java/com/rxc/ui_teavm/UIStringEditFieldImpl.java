package com.rxc.ui_teavm;

import com.rxc.lang.T3;
import com.rxc.ui.UIComponent;
import com.rxc.ui.UIStringEditField;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLInputElement;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public class UIStringEditFieldImpl implements UIStringEditField {

  private final String name;
  private final HTMLInputElement element;

  private Listener t;

  public UIStringEditFieldImpl(String name, final HTMLInputElement element) {
    this.name = name;
    this.element = element;
    setInnerStatus(Status.EMPTY);
    setInnerValue(null);
    element.addEventListener("keypress", this::handleKeyPress);
  }


  @Override
  public String name() {
    return name;
  }

  @Override
  public String value() {
    return element.getValue();
  }

  @Override
  public void value(final String value) {
    setInnerValue(value);
  }

  @Override
  public void readonly(final boolean flag) {
    element.setReadOnly(flag);
  }

  @Override
  public boolean readonly() {
    return element.isReadOnly();
  }

  @Override
  public void listener(final Listener t) {
    this.t = t;
  }

  private void handleKeyPress(Event evt) {
      if (t != null) {
        final T3<String, Status, String> t3 = t.proposeChange(element.getValue());
        if (t3._1.equals(element.getValue())) {
            setInnerValue(t3._1);
            setInnerStatus(t3._2);
            setInnerMessage(t3._3);
        }
      }
  }

  private void setInnerMessage(final String msg) {
     element.setTitle(msg);
  }

  private void setInnerValue(String value) {
    if (value != null) {
      element.setValue(value);
    } else {
      element.clear();
    }
  }

  private void setInnerStatus(final Status status) {
    element.setAttribute("data-status", status.name());
  }


  public static class FactoryImpl implements UIComponent.Factory {
    private final HTMLDocument document;

    public FactoryImpl(final HTMLDocument document) {
      this.document = document;
    }

    @Override
    public UIStringEditField create(final String name) {
      return new UIStringEditFieldImpl(name, (HTMLInputElement) document.getElementById(name));
    }
  }
}
