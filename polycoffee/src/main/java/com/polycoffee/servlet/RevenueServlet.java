package com.polycoffee.servlet;

import com.polycoffee.dao.RevenueDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/revenue")
public class RevenueServlet extends HttpServlet {

    private RevenueDAO dao = new RevenueDAO();

    private Date parseDate(String dateStr) throws Exception {
        if (dateStr == null || dateStr.isEmpty()) {
            return new Date();
        }
        return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
    }

    private Date getDefaultFrom(Date to) {
        return new Date(to.getTime() - 7L * 24 * 60 * 60 * 1000);
    }

    // ================= LOAD DASHBOARD =================
    private void loadDashboard(HttpServletRequest req, Date from, Date to) {

        req.setAttribute("summary", dao.getDashboardSummary(from, to));

        req.setAttribute("dateData", dao.getRevenueByDate(from, to));
        req.setAttribute("topData", dao.getTopDrinks(from, to));
        req.setAttribute("staffData", dao.getRevenueByStaff(from, to));
        req.setAttribute("categoryData", dao.getRevenueByCategory(from, to));
        req.setAttribute("hourData", dao.getRevenueByHour(from, to));

        req.setAttribute("from", new SimpleDateFormat("yyyy-MM-dd").format(from));
        req.setAttribute("to", new SimpleDateFormat("yyyy-MM-dd").format(to));
    }

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Date to = new Date();
            Date from = getDefaultFrom(to);

            loadDashboard(req, from, to);

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("view", "/views/revenue.jsp");
        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }

    // ================= POST (FILTER DATE) =================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Date from = parseDate(req.getParameter("from"));
            Date to = parseDate(req.getParameter("to"));

            loadDashboard(req, from, to);

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("view", "/views/revenue.jsp");
        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }
}