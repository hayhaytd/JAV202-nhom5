package com.polycoffee.servlet;

import com.polycoffee.dao.UserDAO;
import com.polycoffee.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String activeStr = request.getParameter("active");
            String pageStr = request.getParameter("page");

            Boolean active = (activeStr != null && !activeStr.isEmpty())
                    ? Boolean.parseBoolean(activeStr) : null;

            int page = (pageStr != null && !pageStr.isEmpty())
                    ? Integer.parseInt(pageStr) : 1;

            int size = 10;

            // ===== SEARCH ONLY EMPLOYEE =====
            List<User> list = userDAO.search(fullname, email, active, page, size)
                    .stream()
                    .filter(u -> u.getRole() == 1) // chỉ employee
                    .toList();

            long total = userDAO.count(fullname, email, active);
            int totalPages = (int) Math.ceil((double) total / size);

            request.setAttribute("employees", list);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            request.setAttribute("fullname", fullname);
            request.setAttribute("email", email);
            request.setAttribute("active", active);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("view", "/views/employeeManager.jsp");
        request.getRequestDispatcher("/views/index.jsp").forward(request, response);
    }

    // ================= POST =================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                create(request);
            } else if ("update".equals(action)) {
                update(request);
            } else if ("delete".equals(action)) {
                delete(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("employee");
    }

    // ================= CREATE =================
    private void create(HttpServletRequest request) {
        User u = new User();

        u.setFullname(request.getParameter("fullname"));
        u.setEmail(request.getParameter("email"));
        u.setPassword(request.getParameter("password"));
        u.setActive(Boolean.parseBoolean(request.getParameter("active")));

        u.setRole(1); // Employee

        userDAO.create(u);
    }

    // ================= UPDATE =================
    private void update(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));

        User u = userDAO.findById(id);

        if (u != null) {
            u.setFullname(request.getParameter("fullname"));
            u.setEmail(request.getParameter("email"));
            u.setActive(Boolean.parseBoolean(request.getParameter("active")));

            userDAO.update(u);
        }
    }

    // ================= DELETE =================
    private void delete(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));

        // bạn có thể đổi sang deleteSoft
        userDAO.delete(id);
    }
}