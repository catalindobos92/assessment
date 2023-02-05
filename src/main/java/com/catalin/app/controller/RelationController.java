package com.catalin.app.controller;

import com.catalin.app.entity.Relation;
import com.catalin.app.service.RelationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/relation")
public class RelationController {
  public final RelationService relationService;

  @PostMapping
  public Relation addRelation(@RequestBody RelationRequest relationRequest) {
    Relation relation = new Relation();
    relation.setW1(relationRequest.w1());
    relation.setW2(relationRequest.w2());
    relation.setR(relationRequest.r());

    return relationService.addRelation(relation);
  }

  record RelationRequest(String w1, String w2, String r) {}
}
