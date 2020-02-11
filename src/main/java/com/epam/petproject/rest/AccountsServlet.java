package com.epam.petproject.rest;

import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;
import com.epam.petproject.repository.jdbc.ConnectionUtil;
import com.epam.petproject.repository.jdbc.JDBCAccountRepositoty;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AccountsServlet extends HttpServlet {
    private ConnectionUtil factory = new ConnectionUtil();
    private JDBCAccountRepositoty repository = new JDBCAccountRepositoty(factory.getMySQLDataSource());
    private Gson gson = new Gson();
    private ResponsePrinter printer;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        try {
            String accountJsonString;
            String id = request.getParameter("id");
            if (id == null) {
                List<Account> accounts = repository.getAll();
                accountJsonString = this.gson.toJson(accounts);
            } else {
                Account account = repository.getById(Long.parseLong(id));
                accountJsonString = this.gson.toJson(account);
            }
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(accountJsonString);
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
            String accountJsonString;
            String name = request.getParameter("name");
            Account toSave = new Account(null, AccountStatus.valueOf(name));
            Account account = repository.save(toSave);
            accountJsonString = this.gson.toJson(account);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(accountJsonString);
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

            String accountJsonString;
            String id = request.getParameter("id");
            String accountValue = request.getParameter("account");

            Account account = new Account(Long.parseLong(id), AccountStatus.valueOf(accountValue));
            Account updated = repository.update(account);
            accountJsonString = this.gson.toJson(updated);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(accountJsonString);
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
            String accountJsonString;
            String id = request.getParameter("id");
            Account deleted = repository.getById(Long.parseLong(id));
            repository.deleteById(Long.parseLong(id));
            accountJsonString = this.gson.toJson(deleted);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(accountJsonString);
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
