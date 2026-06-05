package com.example.demo;

import jakarta.servlet.http.HttpSession;

public final class SessionUtil {

    private SessionUtil() {
    }

    public static Long getUserId(HttpSession session) {
        return (Long) session.getAttribute("userId");
    }

    public static String getRole(HttpSession session) {
        return (String) session.getAttribute("role");
    }

    public static boolean isLoggedIn(HttpSession session) {
        return getUserId(session) != null;
    }

    public static boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(getRole(session));
    }
}
