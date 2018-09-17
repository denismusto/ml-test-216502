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

package com.example.endpoints;

import com.example.endpoints.model.PersonModel;
import com.example.endpoints.model.connection.ConnMysql;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that echoes JSON message bodies.
 */
@WebServlet("/mutant")
public class Person extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.addHeader("Content-Encoding", "application/json");

    Object responseBody;
    try {
      JsonReader jsonReader = new JsonReader(req.getReader());

      List<String> dnaList = new ArrayList<String>();

      jsonReader.beginObject();
      while (jsonReader.hasNext()) {
        String name = jsonReader.nextName();
        if (name.equals("dna")) {
          jsonReader.beginArray();
          while (jsonReader.hasNext()) {
            dnaList.add(jsonReader.nextString());
          }
          jsonReader.endArray();
        }
      }
      jsonReader.endObject();

      String[] dnaArray = new String[dnaList.size()];
      dnaArray = dnaList.toArray(dnaArray);

      PersonModel personModel = new PersonModel();
      JsonObject jsonObject = personModel.newPerson(dnaArray);

      //Conn ---
      //ConnMysql connMysql = new ConnMysql();
      //Connection conn = connMysql.getConn();
      //log("Connected");
      //Conn ---

      if (jsonObject.get("personType").getAsString().equals("human")) {
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        JsonObject error = new JsonObject();
        error.addProperty("code", HttpServletResponse.SC_FORBIDDEN);
        jsonObject = error;
      }

      responseBody = jsonObject;
    } catch (JsonParseException | MalformedJsonException je) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      JsonObject error = new JsonObject();
      error.addProperty("code", HttpServletResponse.SC_BAD_REQUEST);
      error.addProperty("message", "Body was not valid JSON.");
      responseBody = error;
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      JsonObject error = new JsonObject();
      error.addProperty("code", HttpServletResponse.SC_BAD_REQUEST);
      error.addProperty("message", e.getMessage());
      responseBody = error;
    }

    new Gson().toJson(responseBody, resp.getWriter());
  }
}
