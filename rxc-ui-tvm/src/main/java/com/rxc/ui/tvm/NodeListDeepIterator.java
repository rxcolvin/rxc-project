package com.rxc.ui.tvm;

import org.teavm.jso.dom.xml.Node;
import org.teavm.jso.dom.xml.NodeList;

import java.util.Iterator;
import java.util.Stack;

/**
* Created by richard.colvin on 21/12/2015.
*/
class NodeListDeepIterator implements Iterator<Node> {
  private final Stack<NodeListIterator> stack = new Stack<>();
  int ref = 0;

  public NodeListDeepIterator(final NodeList nodelist) {
    this.stack.push(new NodeListIterator(nodelist));
  }

  @Override
  public boolean hasNext() {
    boolean ret;
    if (!stack.peek().hasNext() && stack.size() == 1) {
      ret = false;
    } else {
      final NodeListIterator k = stack.pop();
      ret = stack.peek().hasNext();
      stack.push(k);
    }
    return ret;

  }

  @Override
  public Node next() {
    Node node;
    Iterator<Node> k = stack.peek();
    if (k.hasNext()) {
      node = k.next();
    } else {
      stack.pop();
      node = next();
    }

    final NodeList<Node> childNodes = node.getChildNodes();
    if (childNodes.getLength() > 0) {
      stack.push(new NodeListIterator(childNodes));
    }
    return node;
  }
}
