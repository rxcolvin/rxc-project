package com.rxc.uimock;

import com.rxc.lang.Array;
import com.rxc.lang.F1;
import com.rxc.lang.Map;
import com.rxc.lang.T2;
import com.rxc.ui.UIAction;
import com.rxc.ui.UIComponent;
import com.rxc.ui.UIContainer;
import com.rxc.ui.UIEditField;

/**
 * Created by richard.colvin on 02/12/2015.
 */
public class MockUIContainer implements UIContainer{

  final Map<String, UIEditField> editFieldMap;

  public MockUIContainer(Array<String> uiFieldNames) {
     editFieldMap = Map.$_(uiFieldNames.<UIEditField>map(MockUIEditField::new), UIComponent::name);
  }
  @Override
  public UIEditField editField(final String name) {
    return editFieldMap.$(name);
  }

  @Override
  public UIContainer container(final String name) {
    return null;
  }

  @Override
  public UIAction action(final String name) {
    return null;
  }
}
