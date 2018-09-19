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

package com.example.endpoints.model;

import com.example.endpoints.model.entity.Person;
import com.example.endpoints.model.repository.PersonRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.EnumUtils;

import java.util.List;

public class PersonModel {

  public JsonObject newPerson(Person person) throws Exception {

    try {

      List<String> dnaList = new Gson().fromJson(person.getSeqAdn(), new TypeToken<List<String>>(){}.getType());
      String[] dna = new String[]{};
      dna = dnaList.toArray(dna);

      JsonObject ret = new JsonObject();
      String[][] matrix = new String[6][6];

      for (int i = 0; i < dna.length; i++) {
        String[] seq = dna[i].split("");
        for (int m = 0; m < 6; m++) {
          if (!EnumUtils.isValidEnum(DnaEnum.class, seq[m])) {
            ret.addProperty("success", false);
            ret.addProperty("message", "Nucleotides " + seq[m] + " not recognized.");
            return ret;
          }
          matrix[i][m] = seq[m];
        }
      }

      int duplicHoriz = 0;
      int duplicDiag = 0;
      int duplicVerti = 0;
      int sequenceOk = 0;

      for (int i = 0; i < 6; i++) {
        for (int m = 0; m < 6; m++) {

          //Horiz ---
          if (m == 0) {
            duplicHoriz = 0;
          } else {
            if (matrix[i][m].equals(matrix[i][m - 1])) {
              duplicHoriz++;
              if (duplicHoriz == 3) {
                sequenceOk++;
              }
            } else {
              duplicHoriz = 0;
            }
          }

          //Verti ---
          if (m == 0) {
            duplicVerti = 0;
          } else {
            if (matrix[m][i].equals(matrix[m - 1][i])) {
              duplicVerti++;
              if (duplicVerti == 3) {
                sequenceOk++;
              }
            } else {
              duplicVerti = 0;
            }
          }
        }
      }

      //Diag ---
      for (int i = 0; i < 6; i++) {
        if (i == 0) {
          duplicDiag = 0;
        } else {
          if (matrix[i][i].equals(matrix[i - 1][i - 1])) {
            duplicDiag++;
            if (duplicDiag == 3) {
              sequenceOk++;
            }
          }
        }
      }

      person.setType((sequenceOk > 1 ? "M" : "H"));

      PersonRepository personRepository = new PersonRepository();
      personRepository.newPerson(person);

      ret.addProperty("success", true);
      ret.addProperty("message", "verified");
      ret.addProperty("typePerson", person.getType());

      return ret;

    } catch (Exception e){
      throw new Exception(e);
    }

  }

  public JsonObject getStats() throws Exception {
    try {

      PersonRepository personRepository = new PersonRepository();
      return personRepository.getPersonsStats();

    } finally {
    }
  }

}
