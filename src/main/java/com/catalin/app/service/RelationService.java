package com.catalin.app.service;

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

  public List<Relation> getRelations(String relation) {
    List<FilterCriteria> criteria = new ArrayList<>();
    if (relation != null) criteria.add(new FilterCriteria("r", relation));

    FilterSpecification spec = new FilterSpecification(criteria);
    return relationRepository.findAll(spec);
  }

  public Relation addRelation(Relation relation) {
    return relationRepository.save(relation);
  }
}
