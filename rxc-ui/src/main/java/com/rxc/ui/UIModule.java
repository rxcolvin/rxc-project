package com.rxc.ui;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public interface UIModule {

  <T extends UIComponent> UIComponent.Factory<T> factory(Class<T> type );

}
