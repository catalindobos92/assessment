package com.catalin.app.service;

import com.catalin.app.entity.Relation;
import com.catalin.app.repository.RelationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RelationService {
  public final RelationRepository relationRepository;

  public List<Relation> getRelations() {
    return relationRepository.findAll();
  }

  public Relation addRelation(Relation relation) {
    return relationRepository.save(relation);
  }
}
