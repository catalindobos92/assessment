package com.catalin.app.service;

import com.catalin.app.entity.Relation;
import com.catalin.app.entity.RelationType;
import com.catalin.app.exception.ApiRelationException;
import com.catalin.app.repository.RelationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RelationService {
  private final RelationRepository relationRepository;

  public List<Relation> getRelations(String wordRelation) {
    if (wordRelation != null && !wordRelation.isEmpty())
      return relationRepository.findByWordRelation(wordRelation);
    return relationRepository.findAll();
  }

  public Relation addRelation(Relation.Insert relation) throws ApiRelationException {
    Relation newRelation = new Relation();
    newRelation.setWordOne(relation.wordOne());
    newRelation.setWordTwo(relation.wordTwo());
    newRelation.setWordRelation(RelationType.fromString(relation.wordRelation()));

    Optional<Relation> existingRelation =
        relationRepository.findByWordOneAndWordTwo(
            newRelation.getWordOne(), newRelation.getWordTwo());

    if (existingRelation.isPresent())
      throw new ApiRelationException("Relation already exists for the words!");

    Optional<Relation> existingInverseRelation =
        relationRepository.findByWordOneAndWordTwo(
            newRelation.getWordTwo(), newRelation.getWordOne());

    if (existingInverseRelation.isPresent())
      throw new ApiRelationException("Relation (inversed) already exists for the words!");

    return relationRepository.save(newRelation);
  }
}
