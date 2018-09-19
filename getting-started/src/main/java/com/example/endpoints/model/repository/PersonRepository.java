package com.example.endpoints.model.repository;

import com.example.endpoints.model.entity.Person;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PersonRepository extends ConnRepository {

  public PersonRepository() throws Exception {

  }

  public void newPerson(Person person) throws Exception {
    try {

      Connection conn = this.getInstance();

      String sql = "insert into person (seq_adn, type) values (?, ?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1,person.getSeqAdn());
      ps.setString(2, person.getType());
      ps.executeUpdate();

    } finally {
    }
  }

  public JsonObject getPersonsStats() throws Exception {
    try {

      Connection conn = this.getInstance();
      String sql = "select sum(if(type = 'H', 1, 0)) as 'H', sum(if(type = 'M', 1, 0)) as 'M' from person";
      PreparedStatement ps =  conn.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      int h = 0;
      int m = 0;
      while(rs.next()){
        h = rs.getInt("H");
        m = rs.getInt("M");
      }
      double r = 0;
      if(h > 0){
        r = Math.round(m/h);
      }

      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("count_mutant_dna", m);
      jsonObject.addProperty("count_human_dna", h);
      jsonObject.addProperty("ratio", r);

      return jsonObject;

    } finally {
    }
  }

}
