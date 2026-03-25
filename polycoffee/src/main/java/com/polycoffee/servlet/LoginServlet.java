package com.polycoffee.servlet;

import com.polycoffee.dao.UserDAO;
import com.polycoffee.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("view", "/views/login.jsp");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userDAO.findByEmail(email);

            if (user != null && user.getPassword().equals(password)) {

                // check active
                if (!user.getActive()) {
                    request.setAttribute("error", "Tài khoản đã bị khóa!");
                    request.setAttribute("view", "/views/login.jsp");
                    request.getRequestDispatcher("/views/index.jsp").forward(request, response);
                    return;
                }

                // lưu session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // phân quyền
                int role = user.getRole();

                if (role == 0) {
                    request.setAttribute("view", "/views/homeAdmin.jsp");

                } else if (role == 1) {
                    request.setAttribute("view", "/views/homeEmployee.jsp");

                } else {
                    request.setAttribute("view", "/views/homeGuest.jsp");
                }

                request.getRequestDispatcher("/views/index.jsp").forward(request, response);

            } else {
                request.setAttribute("error", "Sai email hoặc mật khẩu!");
                request.setAttribute("view", "/views/login.jsp");
                request.getRequestDispatcher("/views/index.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("error", "Tài khoản không tồn tại!");
            request.setAttribute("view", "/views/login.jsp");
            request.getRequestDispatcher("/views/index.jsp").forward(request, response);
        }
    }
}