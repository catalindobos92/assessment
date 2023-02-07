package com.catalin.app.service;

import com.catalin.app.dto.RelationDTO;
import com.catalin.app.dto.RelationDTOMapper;
import com.catalin.app.dto.RelationRequestBody;
import com.catalin.app.entity.Relation;
import com.catalin.app.entity.RelationName;
import com.catalin.app.exception.ApiRelationException;
import com.catalin.app.repository.RelationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RelationService {
  private final RelationRepository relationRepository;
  private final RelationDTOMapper relationDTOMapper;

  public List<RelationDTO> getRelations(String wordRelation) {
    if (wordRelation != null && !wordRelation.isEmpty())
      return relationRepository.findByWordRelation(wordRelation).stream()
          .map(relation -> relationDTOMapper.transform(relation, false))
          .toList();
    return relationRepository.findAll().stream()
        .map(relation -> relationDTOMapper.transform(relation, false))
        .toList();
  }

  public RelationDTO addRelation(RelationRequestBody relation) throws ApiRelationException {
    Relation newRelation = new Relation();
    newRelation.setWordOne(formatValue(relation.getWordOne()));
    newRelation.setWordTwo(formatValue(relation.getWordTwo()));
    newRelation.setWordRelation(RelationName.fromLabel(formatValue(relation.getWordRelation())));

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

    Relation result = relationRepository.save(newRelation);
    return relationDTOMapper.transform(result, false);
  }

  public List<RelationDTO> getInverseRelations() {
    List<Relation> existingRelations = relationRepository.findAll();

    // add DB entries with 'no' flag
    List<RelationDTO> response =
        new ArrayList<>(
            existingRelations.stream()
                .map(relation -> relationDTOMapper.transform(relation, false))
                .toList());

    // add generated entries with 'yes' flag
    response.addAll(
        existingRelations.stream()
            .map(relation -> relationDTOMapper.transform(relation, true))
            .toList());
    return response;
  }

  private String formatValue(String value) {
    return value.trim().toLowerCase();
  }
}
