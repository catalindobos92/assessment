package com.catalin.app.entity;

import com.catalin.app.exception.ApiRelationException;

import java.util.stream.Stream;

public enum RelationType {
  SYNONYM,
  ANTONYM,
  RELATED;

  public static RelationType fromString(String relation) {
    return Stream.of(values())
        .filter(item -> item.name().equalsIgnoreCase(relation))
        .findFirst()
        .orElseThrow(() -> new ApiRelationException("Non existent relation"));
  }
}
