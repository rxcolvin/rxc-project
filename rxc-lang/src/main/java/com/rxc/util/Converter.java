package com.rxc.util;

import com.rxc.lang.collection.$$;
import com.rxc.lang.collection.$_;
import com.rxc.lang.functional.F1C;
import com.rxc.lang.tuple.T2;


import java.util.function.Function;

import static com.rxc.lang.collection.$$.$$;
import static com.rxc.lang.collection.$_.*;
import static com.rxc.lang.tuple.Tuple.*;

/**
 * Converts a given instance to a given type.
 */
public class Converter implements F1C<Object> {
    final $_<T2<Class, Class>, Function<Object, Object>> map;

    public Converter(
            final $$<P> ps
    ) {
        map = $_(
                ps, p -> $(p.from, p.to),
                p -> p.f,
                (t) -> {throw new NoneFound(t._1, t._2);}
        );
    }

    @Override
    public <R> R apply(Object o, Class<R> aClass) {
        return (R) (o.getClass() != aClass ? map.$($(o.getClass(), aClass)).apply(o) : o);
    }

    /**
     * Parameter Structure
     */
    public static class P<F, T> {
        public final Class<F> from;
        public final Class<T> to;
        public final Function<F, T> f;

        private P(
                final Class<F> from,
                final Class<T> to,
                final Function<F, T> f
        ) {
            this.from = from;
            this.to = to;
            this.f = f;
        }
    }

    /**
     * Constructor Function
     */
    public static <F, T> P<F, T> P(
            final Class<F> from,
            final Class<T> to,
            final Function<F, T> f

    ) {
        return new P<>(from, to, f);
    }

    interface $ {
        $$<P> ps = $$(
                P(Integer.class, String.class, Object::toString),
                P(String.class, Integer.class, Integer::parseInt),
                P(Long.class, String.class, Object::toString),
                P(String.class, Long.class, Long::parseLong),
                P(Boolean.class, String.class, Object::toString),
                P(String.class, Boolean.class, Boolean::parseBoolean)
        );

    }

    public static class NoneFound extends RuntimeException {
        public final Class from;
        public final Class to;
        private final String msg;

        public NoneFound(Class from, Class to) {
            this.from = from;
            this.to = to;
            msg = "No Converter Found to convert from "  + from + " to " + to;
        }

        @Override
        public String getMessage() {
            return msg;
        }
    }
}
