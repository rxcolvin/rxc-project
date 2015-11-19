package com.rxc.dao;


import com.rxc.dao.QueryData;
import com.rxc.lang.T2;
import rx.Observable;

import java.util.Collection;

public interface RxQueryDao<K, T, C> {
   Observable<T> getById(K id);
   <QD extends QueryData<T>> Observable<Collection<T2<K, T>>> query(C context, QD queryData);
}
