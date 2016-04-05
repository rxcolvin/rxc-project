package com.rxc.lang.tuple;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public abstract class Tuple {



  public static <P1, P2> T2<P1, P2> $(P1 _1, P2 _2) {
    return new T2<P1, P2>(_1, _2);
  }

  public static <P1, P2, P3> T3<P1, P2, P3> $(P1 _1, P2 _2, P3 _3) {
    return new T3<P1, P2, P3>(_1, _2, _3);
  }
}
