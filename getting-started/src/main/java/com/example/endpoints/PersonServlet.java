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
import com.example.endpoints.model.entity.Person;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mutant")
public class PersonServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.addHeader("Content-Encoding", "application/json");

    Object responseBody;

    try {

      List<String> dnaList = this.parseJson(req.getReader());

      Person person = new Person();
      person.setSeqAdn(new Gson().toJson(dnaList));
      person.setDatVerify(new Date());

      PersonModel personModel = new PersonModel();
      JsonObject jsonObject = personModel.newPerson(person);

      if (jsonObject.get("typePerson").getAsString().equals("H")) {
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
      error.addProperty("message", "Body was not valid JSON. " + je.getMessage());
      responseBody = error;
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      JsonObject error = new JsonObject();
      error.addProperty("code", HttpServletResponse.SC_BAD_REQUEST);
      error.addProperty("message", "oi4" + e.getMessage() + Arrays.toString(e.getStackTrace()));
      responseBody = error;
    }

    new Gson().toJson(responseBody, resp.getWriter());

  }

  private List<String> parseJson(BufferedReader jsonBody) throws JsonParseException, IOException {
    try {
      List<String> dnaList = new ArrayList<String>();

      if(!jsonBody.ready()){
        throw new JsonParseException("Null JSON");
      }

      JsonReader jsonReader = new JsonReader(jsonBody);
      if(jsonReader.peek() == JsonToken.NULL){
        throw new JsonParseException("Object JSON null");
      }

      while (jsonReader.hasNext()) {
        JsonToken jsonToken = jsonReader.peek();

        if(jsonToken.BEGIN_OBJECT.equals(jsonToken)){
          jsonReader.beginObject();
        } else if(jsonToken.NAME.equals(jsonToken)){
          String name = jsonReader.nextName();
          if (name.equals("dna")) {
            jsonToken = jsonReader.peek();
            if(jsonToken.BEGIN_ARRAY.equals(jsonToken)){
              jsonReader.beginArray();
              while(jsonReader.hasNext()){
                dnaList.add(jsonReader.nextString());
              }
              jsonReader.endArray();
            }
          }
        } else if(jsonToken.END_OBJECT.equals(jsonToken)){
          jsonReader.endObject();
        } else if(jsonToken.NULL.equals(jsonToken)){
          break;
        }
      }

      if(dnaList.size() == 0){
        throw new JsonParseException("Without ADN list.");
      }

      return dnaList;

    } finally {

    }
  }
}
