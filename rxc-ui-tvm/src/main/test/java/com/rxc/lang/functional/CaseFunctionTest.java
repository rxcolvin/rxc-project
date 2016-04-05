package com.rxc.lang.functional;

import com.rxc.lang.collection.$$;
import com.rxc.lang.tuple.T2;
import com.rxc.lang.tuple.Tuple;
import com.rxc.util.Converter;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

import static com.rxc.lang.collection.$$.$$;
import static com.rxc.lang.tuple.Tuple.*;
import static org.junit.Assert.*;

/**
 * Created by richard.colvin on 05/04/2016.
 */
public class CaseFunctionTest {


    @Test
    public void testApply() throws Exception {
        CaseFunction<String, String, Integer> target =
                new CaseFunction<>(
                        "name",
                        $$(
                                $(1,  s -> s),
                                $(2,  s -> s + s)
                        ),
                        Integer::parseInt
                );

        assertEquals("1", target.apply("1"));
        assertEquals("22", target.apply("2"));

    }

    @Test
    public void testApplyFail() throws Exception {
        final String KEY = "1";
        CaseFunction<String, String, Integer> target =
                new CaseFunction<>(
                        "name",
                        $$(),
                        Integer::parseInt
                );

        try {
            target.apply(KEY);
        } catch (CaseFunction.KeyNotFound e) {
            assertSame(target, e.dispatcher);
            assertEquals(KEY, e.key);
        }
    }


    @Test
    public void testToString() throws Exception {
        CaseFunction<String, String, String> target = new CaseFunction<>("name", null, null);

        assertEquals("name", target.toString());
    }
}