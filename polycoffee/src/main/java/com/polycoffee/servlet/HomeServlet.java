package com.polycoffee.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.polycoffee.dao.DrinkDAO;
import com.polycoffee.entity.Drink;
import com.polycoffee.entity.User;
import com.polycoffee.utils.AuthUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

// @WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private DrinkDAO drDao;

    @Override
    public void init() {
        drDao = new DrinkDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && (user.getRole() == 0 || user.getRole() == 1)) {
            req.setAttribute("view", "/views/homeManager.jsp");
        } else {
            req.setAttribute("view", "/views/home.jsp");
        }
        top5drink(req);
        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);

    }

    protected void top5drink(HttpServletRequest req) {

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

            // 🔥 Lấy thêm thông tin Drink
            for (Object[] row : topList) {
                Integer id = (Integer) row[0];
                String name = (String) row[1];
                Long quantity = (Long) row[2];

                // lấy thêm từ DB
                Drink d = drDao.findById(id);

                result.add(new Object[] {
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

    // @Override
    // public void doPost(HttpServletRequest req, HttpServletResponse resp)
    // throws ServletException, IOException {

    // req.setAttribute("view", "/views/top5drink.jsp");
    // req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    // }

}
