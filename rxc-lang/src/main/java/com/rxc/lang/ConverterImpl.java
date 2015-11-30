package com.rxc.lang;

import com.sun.tools.javac.util.Convert;

/**
 * Created by richard on 22/11/2015.
 */
public class ConverterImpl implements Converter {


  private final Map2<Class<?>, Class<?>, F1<Object, Object>> converters;

  public ConverterImpl(Map2<Class<?>, Class<?>, F1<Object, Object>> converters) {
    this.converters = converters;
  }

  @Override
  public final <T, F> T convert(F value, Class<T> type) {
    T ret;
    if (value.getClass() == type) {
      ret = (T) value;
    } else {
      F1<Object, Object> f1 = converters.$(value.getClass(), type);
      if (f1 != null) {
        ret = (T) f1.apply(value);
      } else {
        throw new NoConverterException(value.getClass(), type, this);
      }
    }
    return ret;
  }
}
