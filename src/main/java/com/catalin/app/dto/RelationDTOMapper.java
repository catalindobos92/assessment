package com.catalin.app.dto;

import com.catalin.app.entity.Relation;
import org.springframework.stereotype.Service;

@Service
public class RelationDTOMapper {

  public RelationDTO transform(Relation relation, boolean inversed) {
    return new RelationDTO(
        relation.getId(),
        inversed ? relation.getWordTwo() : relation.getWordOne(),
        inversed ? relation.getWordOne() : relation.getWordTwo(),
        relation.getWordRelation().name().toLowerCase(),
        inversed ? "Yes" : "No");
  }
}
