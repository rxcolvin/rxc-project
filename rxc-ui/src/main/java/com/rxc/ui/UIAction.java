package com.rxc.ui;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public interface UIAction extends UIComponent {

  void enabled(boolean enabled);
  void listener(Listener t);

  interface Listener {
    void onFired();
  }
}
