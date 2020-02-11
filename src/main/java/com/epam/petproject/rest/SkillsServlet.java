package com.epam.petproject.rest;

import com.epam.petproject.model.Skill;
import com.epam.petproject.repository.jdbc.ConnectionUtil;
import com.epam.petproject.repository.jdbc.JDBCSkillRepository;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SkillsServlet extends HttpServlet {

    private ConnectionUtil factory = new ConnectionUtil();
    private JDBCSkillRepository repository = new JDBCSkillRepository(factory.getMySQLDataSource());
    private Gson gson = new Gson();
    private ResponsePrinter printer;


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        try {
            String skillJsonString;
            String id = request.getParameter("id");
            if (id == null) {
                List<Skill> skills = repository.getAll();
                skillJsonString = this.gson.toJson(skills);
            } else {
                Skill skill = repository.getById(Long.parseLong(id));
                skillJsonString = this.gson.toJson(skill);
            }
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(skillJsonString);
            out.flush();
        } catch (IOException e) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.sendError(500);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String skillJsonString;
            String name = request.getParameter("name");
            Skill toSave = new Skill(null, name);
            Skill saved = repository.save(toSave);
            skillJsonString = this.gson.toJson(saved);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(skillJsonString);
            out.flush();
        } catch (IOException e) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.sendError(500);
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            String skillJsonString;
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            Skill toUpdate = new Skill(Long.parseLong(id), name);
            repository.update(toUpdate);
            skillJsonString = this.gson.toJson(repository.getById(toUpdate.getSkillID()));
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(skillJsonString);
            out.flush();
        } catch (IOException e) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.sendError(500);
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String skillJsonString;
            String id = request.getParameter("id");
            Skill deleted = repository.getById(Long.parseLong(id));
            repository.deleteById(Long.parseLong(id));
            skillJsonString = this.gson.toJson(deleted);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(skillJsonString);
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
