package com.rxc.lang.functional;

import com.rxc.lang.collection.$$;
import com.rxc.lang.collection.$_;
import com.rxc.lang.tuple.T2;

import java.util.function.Function;

import static com.rxc.lang.collection.$_.$_;

/**
 * A Function that switches on a key  that is extracted from the request and then dispatched to a function
 * mapped to that key.
 *
 */
public class CaseFunction<Req, Resp, Key> implements Function<Req, Resp> {

    private final $_<Key, Function<Req, Resp>> map;
    private final String name;
    private final Function<Req, Key> keyFunc;

    /**
     * Constructor
     * @param name a symbolic name - fro debug and logging only
     * @param ts set of pairs containing the key value and associated function
     * @param keyFunc
     */
    public CaseFunction(
            final String name,
            final $$<T2<Key, Function<Req, Resp>>> ts,
            final Function<Req, Key> keyFunc
    ) {
        this.name = name;
        this.keyFunc = keyFunc;
        map = $_(ts, t -> t._1, t -> t._2, this::exception);
    }

    public final Resp apply(final Req req) {
        return map.$(keyFunc.apply(req)).apply(req);
    }

    private Function<Req, Resp> exception(Key key ) {
        throw new KeyNotFound(key, this);
    }

    @Override
    public String toString() {
        return name;
    }

    public static class KeyNotFound extends RuntimeException {
        public final Object key;
        public final CaseFunction dispatcher;
        public final String msg;

        public KeyNotFound(
                final Object key,
                final CaseFunction dispatcher
        ) {
            this.key = key;
            this.dispatcher = dispatcher;
            msg = "No Key found for Dispatcher " + key + " for dispatcher " + dispatcher;
        }
    }
}
