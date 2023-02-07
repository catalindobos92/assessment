package com.catalin.app.entity;

import com.catalin.app.exception.ApiRelationException;

import java.util.stream.Stream;

public enum RelationName {
  SYNONYM("synonym"),
  ANTONYM("antonym"),
  RELATED("related");

  private final String label;

  RelationName(String label) {
    this.label = label;
  }

  public static RelationName fromLabel(String relation) {
    return Stream.of(values())
        .filter(item -> item.getLabel().equals(relation))
        .findFirst()
        .orElseThrow(() -> new ApiRelationException("Non existent relation"));
  }

  public String getLabel() {
    return label;
  }
}
