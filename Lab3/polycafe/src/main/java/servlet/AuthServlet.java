package servlet;

import dao.UserDAO;
import entity.User;
import util.AuthUtil;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/dang-nhap")
public class AuthServlet extends HttpServlet {

    UserDAO dao = new UserDAO();

    // HIỂN THỊ LOGIN
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Logout
        if (request.getParameter("logout") != null) {
            AuthUtil.clear(request);
            response.sendRedirect("dang-nhap");
            return;
        }

        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    // XỬ LÝ LOGIN
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = dao.findByEmail(email);

        if (user == null) {
            request.setAttribute("message", "Email không tồn tại!");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        if (!user.getPassword().equals(password)) {
            request.setAttribute("message", "Sai mật khẩu!");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        if (!user.isActive()) {
            request.setAttribute("message", "Tài khoản bị khóa!");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        // LOGIN OK
        AuthUtil.setUser(request, user);

        // Phân quyền
        if (user.getRole() == 1) {
            response.sendRedirect(request.getContextPath() + "/manager/home.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/home.jsp");
        }
    }
}