package com.example.endpoints.model.repository;

import com.example.endpoints.model.connection.ConnMysql;

import java.sql.Connection;

public class ConnRepository {

  private Connection conn;

  public ConnRepository() throws Exception {
    ConnMysql connMysql = new ConnMysql();
    this.conn = connMysql.getConn();
  }

  public Connection getInstance(){
    return this.conn;
  }

}
