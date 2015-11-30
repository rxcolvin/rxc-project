package com.rxc.lang;

/**
 * Created by richard on 21/11/2015.
 */
public interface Converter {
  <T, F> T convert(F value, Class<T> type);
}
