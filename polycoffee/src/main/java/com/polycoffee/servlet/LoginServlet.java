package com.polycoffee.servlet;

import com.polycoffee.dao.*;
import com.polycoffee.entity.Drink;
import com.polycoffee.entity.User;
import com.polycoffee.utils.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();
    private DrinkDAO drinkDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        drinkDAO = new DrinkDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("view", "/views/login.jsp");
        request.getRequestDispatcher("/views/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserDAO udao = new UserDAO();
        User user = udao.Login(email, password);

        if (user != null) {
            AuthUtil.login(req.getSession(), user);
            int role = user.getRole();

            if (role == 0 || role == 1) {
                req.setAttribute("view", "/views/homeManager.jsp");
            } else if(role == 2){
                req.setAttribute("view", "/views/home.jsp");
            }
        } else {
            req.setAttribute("error", "sai email hoặc mật khẩu!");
            req.setAttribute("view", "/views/login.jsp");
        }
        // req.setAttribute("view", "/views/login.jsp");
        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }
}