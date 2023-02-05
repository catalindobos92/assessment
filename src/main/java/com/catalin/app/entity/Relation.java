package com.catalin.app.entity;

import jakarta.persistence.*;

@Entity
public class Relation {
  @Id
  @SequenceGenerator(name = "relation_id_sequence", sequenceName = "relation_id_sequence")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "relation_id_sequence")
  Integer id;

  @Column(nullable = false)
  String w1;

  @Column(nullable = false)
  String w2;

  @Column(nullable = false)
  String r;

  public Relation() {}

  public Relation(Integer id, String w1, String w2, String r) {
    this.id = id;
    this.w1 = w1;
    this.w2 = w2;
    this.r = r;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getW1() {
    return w1;
  }

  public void setW1(String w1) {
    this.w1 = w1;
  }

  public String getW2() {
    return w2;
  }

  public void setW2(String w2) {
    this.w2 = w2;
  }

  public String getR() {
    return r;
  }

  public void setR(String r) {
    this.r = r;
  }
}
