package com.polycoffee.utils;

import jakarta.servlet.http.*;
import com.polycoffee.entity.User;

public class AuthUtil {
    public static void login(HttpSession session, User user) {
        session.setAttribute("user", user);
    }

    public static User getUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    public static void logout(HttpSession session) {
        session.removeAttribute("user");
    }

    public static boolean isLogin(HttpSession session) {
        return getUser(session) != null;
    }

    public static boolean isAdmin(HttpSession session) {
        User user = getUser(session);
        return user != null && user.getRole() == 0;
    }
}
