package com.rxc.ui.tvm;

import com.rxc.lang.functional.F0V;
import com.rxc.ui.UIAction;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.html.HTMLButtonElement;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public class TeavmUIAction implements UIAction {

  private final HTMLButtonElement element;
  private final String name;

  private F0V listener;

  public TeavmUIAction(String name, final HTMLButtonElement element) {
    this.element = element;
    this.name = name;
    element.addEventListener("click", this::onClick);
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
    this.listener = t;
  }

  private void onClick(Event event) {
    if (listener != null) {
      listener.$();
    }
  }
}
