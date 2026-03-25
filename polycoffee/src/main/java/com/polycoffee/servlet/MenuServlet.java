package com.polycoffee.servlet;

import com.polycoffee.dao.UserDAO;
import com.polycoffee.dao.DrinkDAO;
import com.polycoffee.entity.User;
import com.polycoffee.entity.Drink;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    private UserDAO userDAO;
    private DrinkDAO drinkDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        drinkDAO = new DrinkDAO();
    }

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null || action.equals("home")) {

            req.setAttribute("view", "/views/homeGuest.jsp");

        } else if (action.equals("drink")) {

            try {
                List<Drink> list = drinkDAO.findAll();
                req.setAttribute("drinks", list);

                System.out.println("👉 Drink size = " + list.size());

            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Lỗi load đồ uống");
            }

            req.setAttribute("view", "/views/drink.jsp");

        } else if (action.equals("login")) {

            req.setAttribute("view", "/views/login.jsp");
        }

        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }

    // ================= POST (LOGIN) =================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("login".equals(action)) {

            String email = req.getParameter("email");
            String password = req.getParameter("password");

            try {
                User user = userDAO.findByEmail(email);

                if (user != null && user.getPassword().equals(password)) {

                    if (!user.getActive()) {
                        req.setAttribute("error", "Tài khoản đã bị khóa!");
                        req.setAttribute("view", "/views/login.jsp");
                        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
                        return;
                    }

                    // lưu session
                    HttpSession session = req.getSession();
                    session.setAttribute("user", user);

                    int role = user.getRole();

                    // 👉 load drink nếu cần
                    List<Drink> list = drinkDAO.findAll();
                    req.setAttribute("drinks", list);
                    System.out.println("👉 Input email: " + email);
                    System.out.println("👉 User found: " + (user != null));
                    if (role == 0) {
                        req.setAttribute("view", "/views/homeAdmin.jsp");

                    } else if (role == 1) {
                        req.setAttribute("view", "/views/homeEmployee.jsp");

                    } else {
                        req.setAttribute("view", "/views/homeGuest.jsp");
                    }

                } else {
                    req.setAttribute("error", "Sai email hoặc mật khẩu!");
                    req.setAttribute("view", "/views/login.jsp");
                }

            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Tài khoản không tồn tại!");
                req.setAttribute("view", "/views/login.jsp");
            }
        }

        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }
}