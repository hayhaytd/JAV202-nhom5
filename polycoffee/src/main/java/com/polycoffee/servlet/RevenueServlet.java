package com.polycoffee.servlet;

import com.polycoffee.dao.RevenueDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/revenue")
public class RevenueServlet extends HttpServlet {

    RevenueDAO dao = new RevenueDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("view", "/views/revenue.jsp");
        req.getRequestDispatcher("/views/index.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String type = req.getParameter("type");

        List<Object[]> data = null;

        try {
            if ("date".equals(type)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Date from = sdf.parse(req.getParameter("from"));
                Date to = sdf.parse(req.getParameter("to"));

                data = dao.getRevenueByDate(from, to);

            } else if ("month".equals(type)) {

                int year = Integer.parseInt(req.getParameter("year"));
                data = dao.getRevenueByMonth(year);

            } else if ("year".equals(type)) {

                data = dao.getRevenueByYear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("data", data);
        req.setAttribute("type", type);

        req.setAttribute("view", "/views/revenue.jsp");
        req.getRequestDispatcher("/views/index.jsp")
                .forward(req, resp);
    }
}