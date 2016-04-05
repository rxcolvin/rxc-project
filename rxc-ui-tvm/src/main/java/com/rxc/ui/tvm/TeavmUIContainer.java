package com.rxc.ui.tvm;

import com.rxc.lang.collection.$_;
import com.rxc.ui.UIAction;
import com.rxc.ui.UIContainer;
import com.rxc.ui.UIEditField;
import org.teavm.jso.dom.html.*;

/**
 * Created by richard on 11/12/2015.
 */
public class TeavmUIContainer implements UIContainer {

  private final String name;

  private final HTMLElement ui;


  final $_<String, TeavmUIEditField> editFieldMap;
  final $_<String, TeavmUIContainer> containerMap = null; //TODO
  final $_<String, TeavmUIAction>    actionMap = null;


  public TeavmUIContainer(String name, HTMLElement ui) {
    this.name = name;
    this.ui = ui;

    editFieldMap = buildEditFieldMap(ui);
  }

  private $_<String, TeavmUIEditField> buildEditFieldMap(HTMLElement ui) {
    return null;
  }


  @Override public UIEditField editField(String name) {
    return null;
  }

  @Override public UIContainer container(String name) {
    return null;
  }

  @Override public UIAction action(String name) {
    return null;
  }

  @Override public String name() {
    return name;
  }
}
