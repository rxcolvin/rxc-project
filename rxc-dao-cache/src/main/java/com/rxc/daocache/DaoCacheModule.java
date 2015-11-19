package com.rxc.daocache;


import com.rxc.lang.Timing;

public class  DaoCacheModule {

  public <K, T, C> DaoCache.Factory<K, T, C> daoCacheFactory(Timing timing) {
      return new DaoCache.Factory<>(timing);
  }

}
