package com.rxc.contoller;


import com.rxc.lang.Array;
import com.rxc.lang.Builder;
import com.rxc.lang.Converter;
import com.rxc.meta.EntityMeta;
import com.rxc.meta.FieldMeta;
import com.rxc.ui.UIComponent;
import com.rxc.ui.UIContainer;
import com.rxc.ui.UIEditField;
import com.rxc.ui.UIStringEditField;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <X> Not ThreadSafe
 */
public class EntityController<X, B extends Builder<X>> {

  private final EntityMeta<X, B>                                              entityMeta;
  private final Array<? extends FieldController<?, X, ? extends UIEditField>> fieldControllers;
  private       X                                                             model;

  public EntityController(final EntityMeta<X, B> entityMeta,
                          final UIContainer container,
                          FieldController.LookupFactory lookupFactory) {
    this.entityMeta = entityMeta;
    this.fieldControllers = buildFieldControllers(entityMeta, container, lookupFactory);
  }

  private Array<? extends FieldController> buildFieldControllers(EntityMeta<X, B> entityMeta,
                                                                 UIContainer container,
                                                                 FieldController.LookupFactory lookupFactory) {
    List<FieldController<?>> list = new ArrayList<>();
    for (FieldMeta fieldMeta : entityMeta.fieldMetas()) {
      UIComponent<?> ui = container.component(fieldMeta.name());
      FieldController fc = lookupFactory.create(ui);
      list.add(fc);
    }
    return Array.$$(list.toArray(new FieldController<?>[entityMeta.fieldMetas().size()]));
  }

  public final void model(X model) {
    this.model = model;
    reset();
  }

  public final X model() {
    Builder<X> b = entityMeta.builderFactory.apply();
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


  public static class Factory<X, B extends Builder<X>> {

    private final FieldController.LookupFactory fieldControllerFactory;

    public Factory(FieldController.LookupFactory fieldControllerFactory) {
      this.fieldControllerFactory = fieldControllerFactory;
    }

    public EntityController<X, B> create(EntityMeta<X, B> entityMeta, UIContainer uiContainer) {
      return new EntityController<X, B>(entityMeta, uiContainer, fieldControllerFactory);
    }
  }
}
