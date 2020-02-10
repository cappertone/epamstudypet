package com.epam.petproject.rest;


import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;
import com.epam.petproject.model.Developer;
import com.epam.petproject.model.Skill;
import com.epam.petproject.repository.jdbc.ConnectionFactory;
import com.epam.petproject.repository.jdbc.JDBCDeveloperRepository;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@WebServlet(name = "DeveloperServlet", urlPatterns = "/developers")
public class DevelopersServlet extends HttpServlet {
    private ConnectionFactory factory = new ConnectionFactory();
    private JDBCDeveloperRepository developerRepository = new JDBCDeveloperRepository(factory.getMySQLDataSource());
    private Gson gson = new Gson();

    //@Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        try {
            String developerJsonString;
            String id = request.getParameter("id");
            if (id == null) {
                List<Developer> developers = developerRepository.getAll();
                developerJsonString = this.gson.toJson(developers);
            } else {
                Developer developer = developerRepository.getById(Long.parseLong(id));
                developerJsonString = this.gson.toJson(developer);
            }
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(developerJsonString);
            out.flush();
        } catch (IOException e) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.sendError(500);
            out.flush();
        }
    }

   // @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String developerJsonString;
            String name = request.getParameter("name");
            Developer toSave = new Developer(null, name, null, null);
            Developer developer = developerRepository.save(toSave);
            developerJsonString = this.gson.toJson(developer);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(developerJsonString);
            out.flush();
        } catch (IOException e) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.sendError(500);
            out.flush();
        }
    }

  //  @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            String developerJsonString;
            String id = request.getParameter("id");
            String name = request.getParameter("id");
            String skill = request.getParameter("skills");
            String accountValue = request.getParameter("account");

            Account account = new Account(null, AccountStatus.valueOf(accountValue));
            Set<Skill> skillSet = new HashSet<>();
            skillSet.add(new Skill(null, skill));
            Developer toUpdate = new Developer(Long.parseLong(id),name,skillSet, account);
            Developer updated = developerRepository.update(toUpdate);
            developerJsonString = this.gson.toJson(updated);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(developerJsonString);
            out.flush();
        } catch (IOException e) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.sendError(500);
            out.flush();
        }
    }

   // @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String developerJsonString;
            String id = request.getParameter("id");
            Developer deleted = developerRepository.getById(Long.parseLong(id));
            developerRepository.deleteById(Long.parseLong(id));
            developerJsonString = this.gson.toJson(deleted);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(developerJsonString);
            out.flush();
        } catch (IOException e) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.sendError(500);
            out.flush();
        }

    }
}