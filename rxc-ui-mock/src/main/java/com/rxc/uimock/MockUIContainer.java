package com.rxc.uimock;

import com.rxc.lang.collection.$$;
import com.rxc.lang.collection.$_;
import com.rxc.ui.UIAction;
import com.rxc.ui.UIComponent;
import com.rxc.ui.UIContainer;
import com.rxc.ui.UIEditField;

import static com.rxc.lang.collection.$_.$_;

/**
 * Created by richard.colvin on 02/12/2015.
 */
public class MockUIContainer implements UIContainer {

  final $_<String, MockUIEditField> editFieldMap;
  final $_<String, MockUIContainer> containerMap;
  final $_<String, MockUIAction> actionMap;

  private final String name;

  public MockUIContainer(String name, $$<MockUIEditField> editFields, $$<MockUIContainer> containers, $$<MockUIAction> actions) {
    this.name = name;
    editFieldMap = $_(editFields, UIComponent::name);
    containerMap = $_(containers, UIComponent::name);
    actionMap = $_(actions, UIComponent::name);
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public UIEditField editField(final String name) {
    return editFieldMap.$(name);
  }

  @Override
  public UIContainer container(final String name) {
    return containerMap.$(name);
  }

  public MockUIEditField mockEditField(String name) {
    return editFieldMap.$(name);
  }

  @Override
  public UIAction action(final String name) {
    return actionMap.$(name);
  }

  public static MockUIContainer $(String name, $$<MockUIEditField> editFields, $$<MockUIContainer> containers, $$<MockUIAction> actions) {
    return new MockUIContainer(name, editFields, containers, actions);
  }
}
