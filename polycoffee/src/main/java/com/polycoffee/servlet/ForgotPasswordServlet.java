package com.polycoffee.servlet;

import java.io.IOException;
import java.util.UUID;

import com.polycoffee.dao.UserDAO;
import com.polycoffee.entity.User;
import com.polycoffee.mail.Mailer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("view", "/views/forgot.jsp");
        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");

        try {
            User user = dao.findByEmail(email);

            if (user == null) {
                req.setAttribute("message", "Email không tồn tại!");
            } else {
                // 🔥 tạo password mới
                String newPass = UUID.randomUUID().toString().substring(0, 8);

                user.setPassword(newPass);
                dao.update(user);

                // 🔥 gửi mail
                int result = Mailer.send(
                        "dinhdhtts01689@gmail.com",
                        email,
                        "Reset Password",
                        "Mật khẩu mới của bạn là: " + newPass);

                if (result < 0) {
                    req.setAttribute("message", "Gửi mail thất bại!");
                } else {
                    req.setAttribute("message", "Đã gửi mật khẩu qua email!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "Lỗi hệ thống!");
        }
        req.setAttribute("view", "/views/forgot.jsp");
        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }
}