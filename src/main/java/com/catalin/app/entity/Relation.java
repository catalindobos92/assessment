package com.catalin.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "relation")
@Getter
@Setter
@NoArgsConstructor
public class Relation {
  @Id
  @SequenceGenerator(name = "relation_id_sequence", sequenceName = "relation_id_sequence")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "relation_id_sequence")
  Integer id;

  @Column(nullable = false, name = "word_one")
  String wordOne;

  @Column(nullable = false, name = "word_two")
  String wordTwo;

  @Column(nullable = false, name = "word_relation")
  @Enumerated(value = EnumType.STRING)
  RelationType wordRelation;

  public record Insert(String wordOne, String wordTwo, String wordRelation) {}
}
