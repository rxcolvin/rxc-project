package com.rxc.controller;

import com.rxc.lang.collection.$_;
import com.rxc.lang.Conversions;

import static com.rxc.lang.collection.$$.$$;


public class ControllerModule {
  private final EditFieldController.Factory<String> editFieldControllerFactory;
  private final $_<Class<?>, EditFieldController.Factory<?>> editFieldControllerFactoryMap;
  public final EntityController.Factory entityControllerFactory;

  public ControllerModule() {
    editFieldControllerFactory = new EditFieldController.Factory<>(Conversions.s2s, Conversions.s2s, String.class);
    editFieldControllerFactoryMap =  $_.$_($$(editFieldControllerFactory), f -> f.tType);
    entityControllerFactory = new EntityController.Factory(editFieldControllerFactoryMap);
  }
}
