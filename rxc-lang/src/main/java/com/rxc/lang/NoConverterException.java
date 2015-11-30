package com.rxc.lang;

public final class NoConverterException extends RuntimeException {
  private final Class<? extends Object> fromType;
  private final Class<? extends Object> toType;
  private final Converter               source;

  public NoConverterException(Class<? extends Object> fromType, Class<? extends Object> toType, Converter source) {
    super(String.format("No converter from %s to %s in %s", fromType.getName(), toType.getName(), source));
    this.fromType = fromType;
    this.toType = toType;
    this.source = source;
  }
}
