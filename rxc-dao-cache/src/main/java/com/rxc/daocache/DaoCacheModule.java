package com.rxc.daocache;

import com.rxc.dao.RxDao;

/**
 * Created by richard.colvin on 17/11/2015.
 */
public class  DaoCacheModule {


    public <T, K,C> RxDao<K, T, C> daoFor(Class<T> tClass, Class<K> kClass, Class<C> cClass) {
      return new DaoCache<K, T, C>(inner, timing) ;
    }

}
