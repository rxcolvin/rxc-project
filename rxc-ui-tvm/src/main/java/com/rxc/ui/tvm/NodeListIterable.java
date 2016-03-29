package com.rxc.ui.tvm;

import org.teavm.jso.dom.xml.Node;
import org.teavm.jso.dom.xml.NodeList;

import java.util.Iterator;

/**
* Created by richard.colvin on 21/12/2015.
*/
public class NodeListIterable implements Iterable<Node> {
  private final NodeList nodeList;

  NodeListIterable(final NodeList nodeList) {
    this.nodeList = nodeList;
  }

  @Override
  public Iterator<Node> iterator() {
    return new NodeListIterator(nodeList);
  }
}
