package com.catalin.app.repository;

import com.catalin.app.entity.Relation;
import com.catalin.app.entity.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelationRepository extends JpaRepository<Relation, Integer> {

  Optional<Relation> findByWordOneAndWordTwo(String wordOne, String wordTwo);

  List<Relation> findByWordRelation(RelationType wordRelation);
}
