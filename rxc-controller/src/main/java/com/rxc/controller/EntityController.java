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
import java.util.function.Supplier;

/**
 * @param <E> Not ThreadSafe
 */
public class EntityController<E, B extends Supplier<E>> {

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
            final UIEditField ui = container.editField(fieldMeta.fieldDef.name);
            // ui.constraint(..)
            final EditFieldController<?> fc
                    = fieldControllerFactoryMap.$(fieldMeta.fieldDef.tType).create(ui, fieldMeta.fieldDef.validator);
            list.add(fc);
        }
        return $$.$$(list.toArray(new EditFieldController<?>[entityMeta.fieldMetas().size()]));
    }

    public final void model(E model) {
        this.model = model;
        reset();
    }

    public final E model() {
        final Supplier<E> b = entityMeta.builderFactory.get();
        for (int i = 0; i < entityMeta.fieldMetas().size(); i++) {
            FieldMeta fm = entityMeta.fieldMetas().$(i);
            EditFieldController fc = fieldControllers.$(i);
            Object value = fc.value();
            fm.setter.accept(value, b);
        }
        return b.get();
    }


    private void reset() {
        if (model != null) {
            for (int i = 0; i < entityMeta.fieldMetas().size(); i++) {
                final FieldMeta fm = entityMeta.fieldMetas().$(i);
                final EditFieldController fc = fieldControllers.$(i);
                fc.value(fm.getter.apply(model));
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

        public <E, B extends Supplier<E>> EntityController<E, B> create(
                final EntityMeta<E, B> entityMeta,
                final UIContainer uiContainer
        ) {
            return new EntityController<E, B>(entityMeta, uiContainer, fieldControllerFactoryMap);
        }
    }
}
