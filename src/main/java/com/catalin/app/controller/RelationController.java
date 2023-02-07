package com.catalin.app.controller;

import com.catalin.app.dto.RelationDTO;
import com.catalin.app.dto.RelationInsertDTO;
import com.catalin.app.entity.Relation;
import com.catalin.app.exception.ApiRelationException;
import com.catalin.app.service.RelationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/relation")
public class RelationController {
  private final RelationService relationService;

  @GetMapping()
  public List<RelationDTO> getRelations(
      @Nullable @RequestParam(name = "wordRelation") String wordRelation) {
    return relationService.getRelations(wordRelation).stream()
        .map(relation -> transform(relation, false))
        .toList();
  }

  @GetMapping("/inverse")
  public List<RelationDTO> getInverseRelations() {
    List<Relation> relations = relationService.getRelations(null);
    return Stream.concat(
            relations.stream().map(relation -> transform(relation, false)),
            relations.stream().map(relation -> transform(relation, true)))
        .toList();
  }

  @PostMapping
  public RelationDTO addRelation(@RequestBody @Valid RelationInsertDTO relationRequest)
      throws ApiRelationException {
    var insertRelation =
        new Relation.Insert(
            relationRequest.getWordOne().trim().toLowerCase(),
            relationRequest.getWordTwo().trim().toLowerCase(),
            relationRequest.getWordRelation().trim().toLowerCase());
    return transform(relationService.addRelation(insertRelation), false);
  }

  private RelationDTO transform(Relation relation, boolean inversed) {
    return new RelationDTO(
        relation.getId(),
        inversed ? relation.getWordTwo() : relation.getWordOne(),
        inversed ? relation.getWordOne() : relation.getWordTwo(),
        relation.getWordRelation().name().toLowerCase(),
        inversed ? "Yes" : "No");
  }
}
