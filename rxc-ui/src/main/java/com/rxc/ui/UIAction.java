package com.rxc.ui;

import com.rxc.lang.F0V;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public interface UIAction extends UIComponent<String> {

  void enabled(boolean enabled);

  void listener(F0V t);
}