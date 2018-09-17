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
  /*
  private String DATABASE = "meli_test";
  private String SQL_INSTANCE = "ml-api-216502:southamerica-east1:meli-test";

  public Connection getConn() throws Exception {
    try {

      String url = "jdbc:mysql://google/";
      try {
        //Class.forName("com.mysql.jdbc.GoogleDriver");
        return DriverManager.getConnection(url);
      } catch (SQLException e) {
        throw new ServletException("Unable to connect to Cloud SQL " + url + e.getMessage(), e);
      }

    } finally {

    }

  }
  */
}
