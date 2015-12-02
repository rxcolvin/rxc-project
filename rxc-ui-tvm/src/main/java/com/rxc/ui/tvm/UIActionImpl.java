package com.rxc.ui.tvm;

import com.rxc.lang.F0V;
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

  private F0V listener;

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

  @Override
  public void listener(final F0V t) {
    this.listener = t;;
  }
}
