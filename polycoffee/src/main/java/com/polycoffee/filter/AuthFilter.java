package com.polycoffee.filter;

import java.io.IOException;

import com.polycoffee.utils.AuthUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServlet;
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

        if (uri.contains("login") || uri.contains("css") || uri.contains("js")) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = request.getSession();

        if (!AuthUtil.isLogin(session)) {
            response.sendRedirect("Login.jsp");
        }
        // // Ví dụ chặn admin
        // if (uri.contains("admin") && !AuthUtil.isAdmin(session)) {
        //     response.sendRedirect("403.jsp");
        //     return;
        // }

        chain.doFilter(req, resp);
    }
}
