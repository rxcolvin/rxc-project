package com.rxc.controller;


import com.rxc.lang.$$;
import com.rxc.lang.Builder;
import com.rxc.lang.$_;
import com.rxc.meta.EntityMeta;
import com.rxc.meta.FieldMeta;
import com.rxc.ui.UIContainer;
import com.rxc.ui.UIEditField;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <E> Not ThreadSafe
 */
public class EntityController<E, B extends Builder<E>> {

  private final EntityMeta<E, B> entityMeta;
  private final $$<EditFieldController<?>> fieldControllers;

  private E model;

  public EntityController(final EntityMeta<E, B> entityMeta,
      final UIContainer container,
      final $_<Class<?>, EditFieldController.Factory<?>> fieldControllerFactoryMap
  ) {
    this.entityMeta = entityMeta;
    this.fieldControllers = buildFieldControllers(entityMeta, container, fieldControllerFactoryMap);
  }

  private $$<EditFieldController<?>> buildFieldControllers(
      final EntityMeta<E, B> entityMeta,
      final UIContainer container,
      final $_<Class<?>, EditFieldController.Factory<?>> fieldControllerFactoryMap
  ) {
    final List<EditFieldController<?>> list = new ArrayList<>();
    for (FieldMeta fieldMeta : entityMeta.fieldMetas()) {
      UIEditField ui = container.editField(fieldMeta.name());
      // ui.constraint(..)
      EditFieldController<?> fc = fieldControllerFactoryMap.$(fieldMeta.tType()).create(ui, fieldMeta.validator());
      list.add(fc);
    }
    return $$.$$(list.toArray(new EditFieldController<?>[entityMeta.fieldMetas().size()]));
  }

  public final void model(E model) {
    this.model = model;
    reset();
  }

  public final E model() {
    Builder<E> b = entityMeta.builderFactory.apply();
    for (int i = 0; i < entityMeta.fieldMetas().size(); i++) {
      FieldMeta fm = entityMeta.fieldMetas().$(i);
      EditFieldController fc = fieldControllers.$(i);
      Object value = fc.value();
      fm.setter().apply(value, b);
    }
    return b.build();
  }


  private void reset() {
    if (model != null) {
      for (int i = 0; i < entityMeta.fieldMetas().size(); i++) {
        FieldMeta fm = entityMeta.fieldMetas().$(i);
        EditFieldController fc = fieldControllers.$(i);
        fc.value(fm.getter().$(model));
      }
    }

  }

  /**
   *
   */
  public static class Factory {


    private final $_<Class<?>, EditFieldController.Factory<?>> fieldControllerFactoryMap;

    public Factory(final $_<Class<?>, EditFieldController.Factory<?>> fieldControllerFactoryMap) {
      this.fieldControllerFactoryMap = fieldControllerFactoryMap;
    }

    public <X, B extends Builder<X>> EntityController<X, B> create(final EntityMeta<X, B> entityMeta,
        final UIContainer uiContainer) {
      return new EntityController<X, B>(entityMeta, uiContainer, fieldControllerFactoryMap);
    }
  }
}
