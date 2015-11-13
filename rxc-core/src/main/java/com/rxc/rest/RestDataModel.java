package com.rxc.rest;

import java.util.Map;

/**
 * Created by richard.colvin on 02/11/2015.
 */
public interface RestDataModel {
  String entityName();

  String get(Map<String, String[]> parameterMap);
}
