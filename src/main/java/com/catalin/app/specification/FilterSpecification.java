package com.catalin.app.specification;

import com.catalin.app.entity.Relation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@AllArgsConstructor
public class FilterSpecification implements Specification<Relation> {
  private final List<FilterCriteria> filterCriteria;

  @Override
  public Predicate toPredicate(
      Root<Relation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates =
        filterCriteria.stream()
            .map(criteria -> criteriaBuilder.equal(root.get(criteria.key()), criteria.value()))
            .toList();
    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
