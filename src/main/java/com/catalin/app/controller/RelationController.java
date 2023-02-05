package com.catalin.app.controller;

import com.catalin.app.entity.Relation;
import com.catalin.app.service.RelationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/relation")
public class RelationController {
  public final RelationService relationService;

  @GetMapping
  public List<Relation> getRelations() {
    return relationService.getRelations();
  }

  @GetMapping("/filter")
  public List<Relation> getRelations(@Nullable @RequestParam(name = "relation") String relation) {
    return relationService.getRelationsFilter(relation);
  }

  @GetMapping("/inverse")
  public List<RelationInverseResponse> getInverseRelations() {
    return relationService.getInverseRelations();
  }

  @PostMapping
  public Relation addRelation(@RequestBody RelationRequest relationRequest) {
    Relation relation = new Relation();
    relation.setW1(relationRequest.w1());
    relation.setW2(relationRequest.w2());
    relation.setR(relationRequest.r());

    return relationService.addRelation(relation);
  }

  record RelationRequest(String w1, String w2, String r) {}

  public record RelationInverseResponse(String w1, String w2, String r, String yesOrNo) {}
}
