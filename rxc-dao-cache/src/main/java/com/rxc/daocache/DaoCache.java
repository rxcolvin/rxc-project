package com.rxc.daocache;

import com.rxc.dao.AlreadyExistsException;
import com.rxc.dao.NotFoundException;
import com.rxc.dao.QueryData;
import com.rxc.dao.RxDao;
import com.rxc.lang.*;
import com.rxc.lang.collection.$$;
import com.rxc.lang.collection.$_;
import com.rxc.lang.functional.F1;
import com.rxc.lang.tuple.T2;
import rx.Observable;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import static com.rxc.lang.collection.$_.*;
import static com.rxc.lang.tuple.Tuple.*;

public class DaoCache<K, T, C> implements RxDao<K, T, C> {

  private final ConcurrentHashMap<K, T2<T, Long>> cache = new ConcurrentHashMap<>();

  private final Duration cachePeriod;
  private final RxDao<K, T, C> inner;

  private final Timing timing;
  private final F1<K, C> keyFactory;

  private final $_<Class<? extends QueryData<T>>, QueryHandler<K, T>> queryHandlers;

  public DaoCache(
      final Duration cachePeriod,
      final RxDao<K, T, C> inner,
      final F1<K, C> keyFactory,
      final $$<QueryHandler<K, T>> queryHandlers,
      final Timing timing
  ) {
    this.cachePeriod = cachePeriod;
    this.inner = inner;
    this.timing = timing;
    this.keyFactory = keyFactory;
    this.queryHandlers = $_(queryHandlers, QueryHandler::queryType);
  }

  @Override
  public Observable<K> createKey(final C context) {
    return Observable.just(keyFactory.$(context));
  }

  @Override
  public Observable<T> create(final K id, final T entity, final C context) {
    final T2<T, Long> x = cache.putIfAbsent(id, $(entity, timing.now() + cachePeriod.millis()));
    if (x._1 != entity) {
      return Observable.error(new AlreadyExistsException(id));
    }
    return Observable.just(entity);
  }

  @Override
  public Observable<T> update(final K id, final T entity, final C context) {
    if (!cache.containsKey(id)) {
      Observable.just(new NotFoundException(id));
    }
    return Observable.just($(entity, timing.now() + cachePeriod.millis())._1);
  }

  @Override
  public Observable<Void> remove(final K id, final C context) {
    if (!cache.containsKey(id)) {
      Observable.just(new NotFoundException(id));
    }
    return Observable.just(null); //Ugly
  }

  @Override
  public Observable<T> getById(final K id) {
    T2<T, Long> t2 = cache.get(id);

    if (t2 != null) {

      return Observable.just(t2._1);
    } else {
      //TODO: check expiry and load from cache
      return Observable.error(new NotFoundException(id));
    }
  }




  @Override
  public final <QD extends QueryData<T>>  Observable<Collection<T2<K, T>>> query(final C context, final QD queryData) {
    final QueryHandler<K, T> x = queryHandlers.$((Class<? extends QueryData<T>>) queryData.getClass());
    if (x == null) {
      return Observable.error(new RuntimeException("Bad"));
    }
    return x.query(queryData);
  }


  public static interface QueryHandler<K, T> {
    Class<? extends QueryData<T>> queryType();

    Observable<Collection<T2<K, T>>> query(QueryData<T> queryData);
  }

  public static class Factory<K, T, C> {
      private final Timing timing;

    public Factory(final Timing timing) {
      this.timing = timing;
    }

    DaoCache<K, T, C> create(
        final Duration cachePeriod,
        final RxDao<K, T, C> inner,
        final F1<K, C> keyFactory,
        final $$<QueryHandler<K, T>> queryHandlers
    ) {
      return new DaoCache<>(cachePeriod, inner, keyFactory, queryHandlers, timing);
    }
  }
}
