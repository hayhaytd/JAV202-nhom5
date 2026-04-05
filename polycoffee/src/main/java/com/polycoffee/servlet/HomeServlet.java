package com.polycoffee.servlet;

import java.io.IOException;

import com.polycoffee.entity.User;
import com.polycoffee.utils.AuthUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

// @WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && ( user.getRole() == 0 || user.getRole() == 1)) {
            req.setAttribute("view", "/views/homeManager.jsp");
        } else {
            req.setAttribute("view", "/views/home.jsp");
        }
        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }

}
