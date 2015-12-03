package com.rxc.lang;

/**
 * Created by richard.colvin on 03/12/2015.
 */
public class Conversions {
  public static F1<String, String> s2s = s -> s;
  public static F1<String, Integer> i2s = i -> "" +i;
  public static F1<Integer, String> s2i = Integer::parseInt;

}
