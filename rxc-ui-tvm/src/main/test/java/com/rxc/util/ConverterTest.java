package com.rxc.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by richard.colvin on 01/04/2016.
 */
public class ConverterTest {

    @Test
    public void testApply() throws Exception {
        Converter converter = new Converter(Converter.$.ps);
        Assert.assertEquals(23, (int) converter.apply("23", Integer.class));
        Assert.assertEquals("23", converter.apply(23, String.class));
        Assert.assertEquals(23L, (long) converter.apply("23", Long.class));
        Assert.assertEquals("23", converter.apply(23L, String.class));

    }

    @Test
    public void testApplyNotFound() {
        Converter converter = new Converter(Converter.$.ps);
        try {
            converter.apply("dfsdf", Object.class);
        } catch (Converter.NoneFound t) {
            assertSame(t.from, String.class);
            assertSame(t.to, Object.class);
            return;
        }
        fail("Expected " + Converter.NoneFound.class + " to be raised");

    }
}