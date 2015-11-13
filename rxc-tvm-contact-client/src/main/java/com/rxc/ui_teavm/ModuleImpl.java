package com.rxc.ui_teavm;

import com.rxc.ui.UIModule;
import com.rxc.ui.UIAction;
import com.rxc.ui.UIComponent;
import com.rxc.ui.UIStringEditField;
import org.teavm.jso.dom.html.HTMLDocument;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public class ModuleImpl implements UIModule {

  private final Map<Class<? extends UIComponent>, UIComponent.Factory> map = new HashMap<>();

  public ModuleImpl(HTMLDocument document) {
      map.put(UIAction.class, new UIActionImpl.FactoryImpl(document));
      map.put(UIStringEditField.class, new UIStringEditFieldImpl.FactoryImpl(document));
  }
  @Override
  public <T extends UIComponent> UIComponent.Factory<T> factory(final Class<T> type) {
    return (UIComponent.Factory<T>)map.get(type);
  }
}
