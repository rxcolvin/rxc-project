package com.rxc.ui;

import com.rxc.lang.F0;

/**
 * Created by richard on 05/12/2015.
 */
public interface UIAppContainer {

  UIContainer rootContainer();

  void closeListener(F0<Boolean> f);
}
