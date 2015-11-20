package com.rxc.contoller;


import com.rxc.lang.Map;
import com.rxc.meta.EntityMeta;
import com.rxc.meta.FieldMeta;
import com.rxc.ui.controller.UIContainer;

public class EntityController {

  private Map<String, Object> model;

  private final EntityMeta entityMeta;
  private final UIContainer container;

  public EntityController(final EntityMeta entityMeta, final UIContainer container) {
    this.entityMeta = entityMeta;
    this.container = container;
  }

  public void  model(Map<String, Object> model) {
    this.model = model;
    reset();
  }

  private void reset() {
    for (FieldMeta fieldMeta : entityMeta.fieldMetas()) {
      container.component(fieldMeta.name, null).;
    }
  }


}
