package com.rxc.lang;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public abstract class Tuple {

  /*
  * @param _1
  * @param _2
  * @param _3
  * @param <P1>
  * @param <P2>
  * @param <P3>
  * @return
  */

  public static <P1, P2, P3> T3<P1, P2, P3> $(P1 _1, P2 _2, P3 _3) {
    return new T3<P1, P2, P3>(_1, _2, _3);
  }
}
