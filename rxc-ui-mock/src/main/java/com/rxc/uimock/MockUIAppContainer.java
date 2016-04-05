package com.rxc.uimock;

import com.rxc.lang.functional.F0;
import com.rxc.ui.UIAppContainer;
import com.rxc.ui.UIContainer;

/**
 * Created by richard on 05/12/2015.
 */
public class MockUIAppContainer implements UIAppContainer {
  private final UIContainer rootContainer;

  public MockUIAppContainer(UIContainer rootContainer) {
    this.rootContainer = rootContainer;
  }

  @Override public UIContainer rootContainer() {
    return rootContainer;
  }

  @Override public void closeListener(F0<Boolean> f) {

  }

  public static MockUIAppContainer $(UIContainer rootContainer) {
    return new MockUIAppContainer(rootContainer);
  }
}
