package com.polycoffee.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.polycoffee.dao.BillDAO;
import com.polycoffee.dao.DrinkDAO;
import com.polycoffee.entity.Drink;
import com.polycoffee.entity.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class HomeServlet extends HttpServlet {

    private DrinkDAO drDao;
    private BillDAO billDao;

    @Override
    public void init() {
        drDao = new DrinkDAO();
        billDao = new BillDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        // ================= USER =================
        if (user == null) {
            req.setAttribute("view", "/views/home.jsp");
        }

        // ================= ADMIN / EMP =================
        else if (user.getRole() == 0 || user.getRole() == 1) {

            req.setAttribute("view", "/views/homeManager.jsp");

            // 🔥 load dashboard data
            loadDashboard(req);
            top5drink(req);
        }

        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }

    // ================= DASHBOARD =================
    private void loadDashboard(HttpServletRequest req) {
        try {
            Date today = new Date();

            // ⚠️ bạn cần viết các hàm này trong BillDAO
            double todayRevenue = billDao.getTodayRevenue(today);
            int todayBills = billDao.countTodayBills(today);
            int unpaidBills = billDao.countByStatus(0);
            int cancelBills = billDao.countByStatus(-1);

            req.setAttribute("todayRevenue", todayRevenue);
            req.setAttribute("todayBills", todayBills);
            req.setAttribute("unpaidBills", unpaidBills);
            req.setAttribute("cancelBills", cancelBills);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= TOP 5 DRINK =================
    private void top5drink(HttpServletRequest req) {

        try {
            String from = req.getParameter("fromDate");
            String to = req.getParameter("toDate");

            List<Object[]> topList;
            List<Object[]> result = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date fromDate, toDate;

            if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
                fromDate = sdf.parse(from);
                toDate = sdf.parse(to);

                req.setAttribute("fromDate", from);
                req.setAttribute("toDate", to);
            } else {
                Date today = new Date();
                Date past = new Date(today.getTime() - 7L * 24 * 60 * 60 * 1000);

                fromDate = past;
                toDate = today;

                req.setAttribute("fromDate", sdf.format(past));
                req.setAttribute("toDate", sdf.format(today));
            }

            topList = drDao.getTop5Drinks(fromDate, toDate);

            for (Object[] row : topList) {
                Integer id = (Integer) row[0];
                String name = (String) row[1];
                Long quantity = (Long) row[2];

                Drink d = drDao.findById(id);

                result.add(new Object[]{
                        name,
                        quantity,
                        d.getImage(),
                        d.getPrice()
                });
            }

            req.setAttribute("top5List", result);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi: " + e.getMessage());
        }
    }
}