package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import entity.User;

public class AuthUtil {

    public static void setUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute("user", user);
    }

    public static User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    public static boolean isAuthenticated(HttpServletRequest request) {
        return getUser(request) != null;
    }

    public static boolean isManager(HttpServletRequest request) {
        User user = getUser(request);
        return user != null && user.getRole() == 1; // 1 = admin
    }

    public static void clear(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
    }
}