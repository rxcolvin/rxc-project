package com.rxc.ui.tvm;

import com.rxc.lang.$_;
import com.rxc.ui.UIAction;
import com.rxc.ui.UIContainer;
import com.rxc.ui.UIEditField;
import org.teavm.jso.dom.html.*;
import org.teavm.jso.dom.xml.Node;
import org.teavm.jso.dom.xml.NodeList;

/**
 * Created by richard on 11/12/2015.
 */
public class TeavmUIContainer implements UIContainer {

  private final String name;

  private final HTMLElement ui;


  final $_<String, TeavmUIEditField> editFieldMap;
  final $_<String, TeavmUIContainer> containerMap;
  final $_<String, TeavmUIAction>    actionMap;


  public TeavmUIContainer(String name, HTMLElement ui) {
    this.name = name;
    this.ui = ui;

    editFieldMap = buildEditFieldMap(ui);
  }

  private $_<String, TeavmUIEditField> buildEditFieldMap(HTMLElement ui) {

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
