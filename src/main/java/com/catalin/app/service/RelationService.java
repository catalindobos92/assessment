package com.catalin.app.service;

import com.catalin.app.controller.RelationController;
import com.catalin.app.entity.Relation;
import com.catalin.app.repository.RelationRepository;
import com.catalin.app.specification.FilterCriteria;
import com.catalin.app.specification.FilterSpecification;
import exception.ApiRelationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RelationService {
  public final RelationRepository relationRepository;

  public List<Relation> getRelations() {
    return relationRepository.findAll();
  }

  public List<Relation> getRelationsFilter(String w1, String w2, String relation) {
    List<FilterCriteria> criteria = new ArrayList<>();
    if (w1 != null) criteria.add(new FilterCriteria("w1", w1));
    if (w2 != null) criteria.add(new FilterCriteria("w2", w2));
    if (relation != null) criteria.add(new FilterCriteria("r", relation));

    FilterSpecification spec = new FilterSpecification(criteria);
    return relationRepository.findAll(spec);
  }

  public Relation addRelation(Relation relation) throws ApiRelationException {
    validateRequestData(relation);
    relation.setW1(formatValue(relation.getW1()));
    relation.setW2(formatValue(relation.getW2()));
    relation.setR(formatValue(relation.getR()));
    validateRequestAlreadyExistingRelation(relation);
    return relationRepository.save(relation);
  }

  public List<RelationController.RelationInverseResponse> getInverseRelations() {
    List<Relation> existingRelations = relationRepository.findAll();

    // add DB entries with 'no' flag
    List<RelationController.RelationInverseResponse> response =
        new ArrayList<>(
            existingRelations.stream()
                .map(
                    relation ->
                        new RelationController.RelationInverseResponse(
                            relation.getW1(), relation.getW2(), relation.getR(), "no"))
                .toList());

    // add generated entries with 'yes' flag
    response.addAll(
        existingRelations.stream()
            .map(
                relation ->
                    new RelationController.RelationInverseResponse(
                        relation.getW2(), relation.getW1(), relation.getR(), "yes"))
            .toList());
    return response;
  }

  private String formatValue(String value) {
    return value.trim().toLowerCase();
  }

  private void validateRequestAlreadyExistingRelation(Relation relation)
      throws ApiRelationException {
    List<Relation> existingRelation = getRelationsFilter(relation.getW1(), relation.getW2(), null);
    if (!existingRelation.isEmpty())
      throw new ApiRelationException("Relation already existing for these 2 words!");

    List<Relation> existingInverseRelation =
        getRelationsFilter(relation.getW2(), relation.getW1(), null);
    if (!existingInverseRelation.isEmpty())
      throw new ApiRelationException("Relation inverse already existing for these two words!");
  }

  private void validateRequestData(Relation relation) throws ApiRelationException {
    if (relation.getW1() == null || relation.getW1().isEmpty())
      throw new ApiRelationException("Field w1 cannot be empty or null!");
    if (relation.getW2() == null || relation.getW2().isEmpty())
      throw new ApiRelationException("Field w2 cannot be empty or null!");
    if (relation.getR() == null || relation.getR().isEmpty())
      throw new ApiRelationException("Field r cannot be empty or null!");

    if (!relation.getW1().matches("[a-zA-Z ]*"))
      throw new ApiRelationException("Only letters and spaces are allowed!");

    if (!relation.getW2().matches("[a-zA-Z ]*"))
      throw new ApiRelationException("Only letters and spaces are allowed!");

    if (!relation.getR().matches("[a-zA-Z ]*"))
      throw new ApiRelationException("Only letters and spaces are allowed!");
  }
}
