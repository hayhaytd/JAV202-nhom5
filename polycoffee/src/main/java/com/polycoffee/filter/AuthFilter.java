package com.polycoffee.filter;

import java.io.IOException;

import com.polycoffee.utils.AuthUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri = request.getRequestURI();

        // Cho phép truy cập tự do
        if (uri.contains("login") || uri.contains("css") || uri.contains("js")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession();

<<<<<<< HEAD
        // if (!AuthUtil.isLogin(session)) {
        //     response.sendRedirect("/login");
        //     return;
        // }
        // Ví dụ chặn admin
        // if (uri.contains("admin") && !AuthUtil.isAdmin(session)) {
        //     response.sendRedirect("403.jsp");
        //     return;
        // }
=======
        // Nếu chưa login → redirect và DỪNG
        if (!AuthUtil.isLogin(session)) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return; //  BẮT BUỘC
        }
>>>>>>> 0246108772daa96ea05e7d6346a833d098d538d0

        // Nếu qua được hết → cho đi tiếp
        chain.doFilter(request, response);
    }
}