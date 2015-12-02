package com.rxc.daocache;


import com.rxc.dao.DaoModule;
import com.rxc.lang.Timing;

public class  DaoCacheModule implements DaoModule {
  private final Timing timing;

  public DaoCacheModule(final Timing timing) {
    this.timing = timing;
  }

  public <K, T, C> DaoCache.Factory<K, T, C> daoCacheFactory() {
      return new DaoCache.Factory<>(timing);
  }

}
