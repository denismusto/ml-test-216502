package com.example.endpoints;

import com.example.endpoints.model.PersonModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/stats")
public class PersonStatServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.addHeader("Content-Encoding", "application/json");
    try {

      PersonModel personModel = new PersonModel();
      JsonObject jsonObject = personModel.getStats();

      new Gson().toJson(jsonObject, resp.getWriter());

    } catch (Exception e){
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("message", e.getMessage());
    }

  }


}
