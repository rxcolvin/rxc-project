package com.rxc.uimock;

import com.rxc.lang.F0V;
import com.rxc.ui.UIAction;
import com.rxc.ui.UIComponent;

/**
 * Created by richard.colvin on 02/12/2015.
 */
public class MockUIAction implements UIAction {
  private boolean enabled;
  private F0V t;
  private final String name;

  public MockUIAction(final String name) {
    this.name = name;
  }

  @Override
  public void enabled(final boolean enabled) {

    this.enabled = enabled;
  }

  @Override
  public void listener(final F0V t) {

    this.t = t;
  }

  @Override
  public String name() {
    return name;
  }


}
