package com.catalin.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RelationInsertDTO {
  @NotBlank(message = "Field wordOne cannot be empty!")
  @Pattern(regexp = "[\\sA-Za-z]*", message = "Only letters and spaces are allowed!")
  String wordOne;

  @NotBlank(message = "Field wordTwo cannot be empty!")
  @Pattern(regexp = "[\\sA-Za-z]*", message = "Only letters and spaces are allowed!")
  String wordTwo;

  @NotBlank(message = "Field wordRelation cannot be empty!")
  @Pattern(regexp = "[\\sA-Za-z]*", message = "Only letters and spaces are allowed!")
  String wordRelation;
}
