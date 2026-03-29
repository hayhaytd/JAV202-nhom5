package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.AuthUtil;
import java.io.IOException;

@WebFilter({"/manager/*"})
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Chưa login
        if (!AuthUtil.isAuthenticated(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Không phải manager
        if (!AuthUtil.isManager(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        chain.doFilter(req, res);
    }
}