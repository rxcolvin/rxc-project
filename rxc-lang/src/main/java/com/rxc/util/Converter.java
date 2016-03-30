package com.rxc.util;

import com.rxc.lang.*;


import java.util.function.BiFunction;
import java.util.function.Function;

import static com.rxc.lang.$$.$$;
import static com.rxc.lang.$_.*;
import static com.rxc.lang.Tuple.*;

/**
 * Created by richard.colvin on 30/03/2016.
 */
public class Converter implements F1C<Object> {
    final $_<T2<Class, Class>, Function<Object, Object>> map;

    public Converter(
            $$<P> t3s
    ) {
        map = $_(t3s, p -> $(p.from, p.to), p -> p.f);
    }

    @Override
    public <R> R apply(Object o, Class<R> aClass) {
        return (R) (o.getClass() != aClass ? map.$($(o.getClass(), aClass)).apply(o) : o);
    }

    //    @Override
//    public <R> R apply(Object o, Class aClass) {
//    }

    public static class P<F, T> {
        public final Class<F> from;
        public final Class<T> to;
        public final Function<F, T> f;

        public P(
                final Class<F> from,
                final Class<T> to,
                final Function<F, T> f
        ) {
            this.from = from;
            this.to = to;
            this.f = f;
        }
    }

    public static <F, T>  P<F,T> P(
        final Class<F> from,
        final Class<T> to,
        final Function<F, T> f

    ) {
        return new P<F, T>(from, to, f);
    }

    interface $ {
        P int2String = P(Integer.class, String.class, Object::toString);
        P string2Int = P(String.class, Integer.class, Integer::parseInt);
    }

    public static void main(String[] args) {
        Converter converter = new Converter($$($.int2String, $.string2Int));
        Object x = converter.apply(23, String.class);

    }
}
