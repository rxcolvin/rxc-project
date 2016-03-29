package com.rxc.ui.tvm;

import org.junit.Assert;
import org.teavm.jso.dom.xml.Node;
import org.teavm.jso.dom.xml.NodeList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NodeListIteratorTest {

  @org.junit.Test
  public void testHasNextEmpty() throws Exception {

    NodeList nodeList = mock(NodeList.class);

    when(nodeList.getLength()).thenReturn(0);

    NodeListIterator target = new NodeListIterator(nodeList);
    assertFalse((target.hasNext()));
  }

  @org.junit.Test
  public void testHasNextThree() throws Exception {

    NodeList nodeList = mock(NodeList.class);

    when(nodeList.getLength()).thenReturn(3);
    when(nodeList.item(0)).thenReturn(mock(Node.class));
    when(nodeList.item(1)).thenReturn(mock(Node.class));
    when(nodeList.item(2)).thenReturn(mock(Node.class));


    NodeListIterator target = new NodeListIterator(nodeList);

    assertTrue((target.hasNext()));
    assertTrue(target.next() != null);
    assertTrue((target.hasNext()));
    assertTrue(target.next() != null);
    assertTrue((target.hasNext()));
    assertTrue(target.next() != null);
    assertFalse((target.hasNext()));
  }




  @org.junit.Test
  public void testNext() throws Exception {
    Node node = mock(Node.class);
    //when(node.getChildNodes()).thenReturn(nodeList);


  }


}