package com.polycoffee.servlet;

import java.io.IOException;
import java.util.List;

import com.polycoffee.dao.UserDAO;
import com.polycoffee.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserDAO dao;

    @Override
    public void init() {
        dao = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String activeStr = req.getParameter("active");
        String pageStr = req.getParameter("page");

        Integer page = (pageStr != null) ? Integer.parseInt(pageStr) : 0;
        int size = 10;

        Boolean active = null;
        if (activeStr != null && !activeStr.isEmpty()) {
            active = Boolean.parseBoolean(activeStr);
        }

        List<User> list = dao.search(fullname, email, active, page, size);
        Long total = dao.count(fullname, email, active);

        int totalPages = (int) Math.ceil(total * 1.0 / size);

        req.setAttribute("list", list);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.setAttribute("fullname", fullname);
        req.setAttribute("email", email);
        req.setAttribute("active", activeStr);

        req.getRequestDispatcher("/views/user.jsp").forward(req, resp);
    }
}