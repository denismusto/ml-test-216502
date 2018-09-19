package com.example.endpoints.model.entity;

import java.util.Date;

public class Person {

  private int idPerson;
  private String seqAdn;
  private java.util.Date datVerify;
  private String type;

  public int getIdPerson() {
    return idPerson;
  }

  public void setIdPerson(int idPerson) {
    this.idPerson = idPerson;
  }

  public String getSeqAdn() {
    return seqAdn;
  }

  public void setSeqAdn(String seqAdn) {
    this.seqAdn = seqAdn;
  }

  public Date getDatVerify() {
    return datVerify;
  }

  public void setDatVerify(Date datVerify) {
    this.datVerify = datVerify;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
