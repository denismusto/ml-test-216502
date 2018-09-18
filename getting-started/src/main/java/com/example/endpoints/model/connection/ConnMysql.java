/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.endpoints.model.connection;

import com.google.apphosting.api.ApiProxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;

public class ConnMysql {

  private String database = "meli_test";
  private String INSTANCE_CONNECTION_NAME = "ml-api-216502:southamerica-east1:meli-test";
  private String user = "root";
  private String password = "meli321@!";

  public Connection getConn() throws Exception {
    try {

      //String url = "jdbc:mysql://google/"+this.database+"?cloudSqlInstance="+this.INSTANCE_CONNECTION_NAME+"&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user="+this.user+"&password="+this.password+"&useSSL=false";
      String url = "jdbc:google:mysql://"+this.INSTANCE_CONNECTION_NAME+"/"+this.database+"?user="+this.user+"&password="+this.password;
      try {
        Class.forName("com.mysql.jdbc.GoogleDriver");
        return DriverManager.getConnection(url);
      } catch (SQLException e) {
        throw new ServletException("Unable to connect to Cloud SQL " + url + e.getMessage(), e);
      }

    } finally {

    }

  }

}
