package com.rxc.ui.tvm;

import org.teavm.jso.dom.xml.Node;
import org.teavm.jso.dom.xml.NodeList;

import java.util.Iterator;

/**
* Created by richard.colvin on 21/12/2015.
*/
public class NodeListIterator implements Iterator<Node> {
  private final NodeList nodelist;
  int ref = 0;

  public NodeListIterator(final NodeList nodelist) {
    this.nodelist = nodelist;
  }

  @Override
  public boolean hasNext() {
    return ref < nodelist.getLength();
  }

  @Override
  public Node next() {
    return nodelist.item(ref++);
  }
}
