package com.rxc.ui_teavm;

import com.rxc.ui.UIAction;
import com.rxc.ui.UIComponent;
import org.teavm.jso.dom.html.HTMLButtonElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public class UIActionImpl implements UIAction {

  private final HTMLButtonElement element;
  private final String name;

  public UIActionImpl(String name, final HTMLButtonElement element) {
    this.element = element;
    this.name = name;
  }

  @Override
  public final void enabled(final boolean enabled) {
    element.setDisabled(!enabled);
  }

  @Override
  public final String name() {
    return name;
  }

  public static class FactoryImpl implements Factory<UIAction> {
    private final HTMLDocument document;

    public FactoryImpl(final HTMLDocument document) {

      this.document = document;
    }

    @Override
    public UIAction create(final String name) {
      final HTMLElement elementById = document.getElementById(name);

      return elementById != null ?
          new UIActionImpl(name, (HTMLButtonElement) elementById) :
          null;
    }
  }


}
