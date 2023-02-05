package com.catalin.app.service;

import com.catalin.app.controller.RelationController;
import com.catalin.app.entity.Relation;
import com.catalin.app.repository.RelationRepository;
import com.catalin.app.specification.FilterCriteria;
import com.catalin.app.specification.FilterSpecification;
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

  public Relation addRelation(Relation relation) throws IllegalArgumentException {
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
      throws IllegalArgumentException {
    List<Relation> existingRelation =
        getRelationsFilter(relation.getW1(), relation.getW2(), relation.getR());
    if (!existingRelation.isEmpty())
      throw new IllegalArgumentException("Relation already existing!");

    List<Relation> existingInverseRelation =
        getRelationsFilter(relation.getW2(), relation.getW1(), relation.getR());
    if (!existingInverseRelation.isEmpty())
      throw new IllegalArgumentException("Relation inverse already existing!");
  }

  private void validateRequestData(Relation relation) throws IllegalArgumentException {
    if (relation.getW1() == null || relation.getW1().isEmpty())
      throw new IllegalArgumentException("Field w1 cannot be empty or null!");
    if (relation.getW2() == null || relation.getW2().isEmpty())
      throw new IllegalArgumentException("Field w2 cannot be empty or null!");
    if (relation.getR() == null || relation.getR().isEmpty())
      throw new IllegalArgumentException("Field r cannot be empty or null!");

    if (!relation.getW1().matches("[a-zA-Z ]*"))
      throw new IllegalArgumentException("Only letters and spaces are allowed!");

    if (!relation.getW2().matches("[a-zA-Z ]*"))
      throw new IllegalArgumentException("Only letters and spaces are allowed!");

    if (!relation.getR().matches("[a-zA-Z ]*"))
      throw new IllegalArgumentException("Only letters and spaces are allowed!");
  }
}
