package com.polycoffee.utils;

import jakarta.servlet.http.HttpServletRequest;

public class ParamUtil {
    public static String getString(HttpServletRequest req, String name, String defaulvalue){
        String value = req .getParameter(name);
        return value != null ? value : defaulvalue;
    }
    public static int getInt(HttpServletRequest req, String name, int defaulvalue ){
        try {
            return Integer.parseInt(req.getParameter(name));
        } catch (Exception e) {
            // TODO: handle exception
            return defaulvalue;
        }
    }
    public static double getDouble(HttpServletRequest req, String name, double defaulvalue){
        try {
            return Double.parseDouble(req.getParameter(name));
        } catch (Exception e) {
            // TODO: handle exception
            return defaulvalue;
        }
    }
}
