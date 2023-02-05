package com.catalin.app.repository;

import com.catalin.app.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RelationRepository
    extends JpaRepository<Relation, Integer>, JpaSpecificationExecutor<Relation> {}
