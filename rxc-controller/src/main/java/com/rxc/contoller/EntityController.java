package com.rxc.contoller;


import com.rxc.lang.Array;
import com.rxc.lang.Builder;
import com.rxc.lang.Map;
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
  private final Array<? extends FieldController<?>> fieldControllers;

  private E model;

  public EntityController(final EntityMeta<E, B> entityMeta,
      final UIContainer container,
      final Map<Class<?>, FieldController.Factory<?>> fieldControllerFactoryMap
  ) {
    this.entityMeta = entityMeta;
    this.fieldControllers = buildFieldControllers(entityMeta, container, fieldControllerFactoryMap);
  }

  private Array<? extends FieldController<?>> buildFieldControllers(
      final EntityMeta<E, B> entityMeta,
      final UIContainer container,
      final Map<Class<?>, FieldController.Factory<?>> fieldControllerFactoryMap
  ) {
    final List<FieldController<?>> list = new ArrayList<>();
    for (FieldMeta fieldMeta : entityMeta.fieldMetas()) {
      UIEditField ui = container.editField(fieldMeta.name(), fieldMeta.tType());
      FieldController<?> fc = fieldControllerFactoryMap.$(fieldMeta.tType()).create(ui, fieldMeta.validator());
      list.add(fc);
    }
    return Array.$$(list.toArray(new FieldController<?>[entityMeta.fieldMetas().size()]));
  }

  public final void model(E model) {
    this.model = model;
    reset();
  }

  public final E model() {
    Builder<E> b = entityMeta.builderFactory.apply();
    for (int i = 0; i < entityMeta.fieldMetas().size(); i++) {
      FieldMeta fm = entityMeta.fieldMetas().at(i);
      FieldController fc = fieldControllers.at(i);
      Object value = fc.value();
      fm.setter().apply(value, b);
    }
    return b.build();
  }


  private void reset() {
    if (model != null) {
      for (int i = 0; i < entityMeta.fieldMetas().size(); i++) {
        FieldMeta fm = entityMeta.fieldMetas().at(i);
        FieldController fc = fieldControllers.at(i);
        fc.value(fm.getter().apply(model));
      }
    }

  }

  /**
   *
   * @param <X>
   * @param <B>
   */
  public static class Factory<X, B extends Builder<X>> {


    private final Map<Class<?>, FieldController.Factory<?>> fieldControllerFactoryMap;

    public Factory(final Map<Class<?>, FieldController.Factory<?>> fieldControllerFactoryMap) {
      this.fieldControllerFactoryMap = fieldControllerFactoryMap;
    }

    public EntityController<X, B> create(final EntityMeta<X, B> entityMeta,
        final UIContainer uiContainer) {
      return new EntityController<X, B>(entityMeta, uiContainer, fieldControllerFactoryMap);
    }
  }
}
