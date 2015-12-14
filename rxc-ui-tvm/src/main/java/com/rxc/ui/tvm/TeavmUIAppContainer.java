package com.rxc.ui.tvm;

import com.rxc.lang.F0;
import com.rxc.ui.UIAppContainer;
import com.rxc.ui.UIContainer;

/**
 * Created by richard on 11/12/2015.
 */
public class TeavmUIAppContainer implements UIAppContainer {
  @Override public UIContainer rootContainer() {
    return null;
  }

  @Override public void closeListener(F0<Boolean> f) {

  }
}
