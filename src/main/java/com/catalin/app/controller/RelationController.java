package com.catalin.app.controller;

import com.catalin.app.dto.RelationDTO;
import com.catalin.app.dto.RelationRequestBody;
import com.catalin.app.exception.ApiRelationException;
import com.catalin.app.service.RelationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/relation")
public class RelationController {
  private final RelationService relationService;

  @GetMapping()
  public List<RelationDTO> getRelations(
      @Nullable @RequestParam(name = "wordRelation") String wordRelation) {
    return relationService.getRelations(wordRelation);
  }

  @GetMapping("/inverse")
  public List<RelationDTO> getInverseRelations() {
    return relationService.getInverseRelations();
  }

  @PostMapping
  public RelationDTO addRelation(@RequestBody @Valid RelationRequestBody relationRequest)
      throws ApiRelationException {
    return relationService.addRelation(relationRequest);
  }
}
