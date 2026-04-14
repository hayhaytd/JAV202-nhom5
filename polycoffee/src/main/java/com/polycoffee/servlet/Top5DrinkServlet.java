package com.polycoffee.servlet;

import java.io.IOException;
import java.rmi.ServerException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.polycoffee.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/top5drink")
public class Top5DrinkServlet extends HttpServlet {
    private DrinkDAO drDao;

    // @Override
    // public void init() {
    //     drDao = new DrinkDAO();
    // }

    // @Override
    // protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    //         throws ServletException, IOException {

    //     try {
    //         String from = req.getParameter("fromDate");
    //         String to = req.getParameter("toDate");

    //         List<Object[]> topList;

    //         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //         if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
    //             Date fromDate = sdf.parse(from);
    //             Date toDate = sdf.parse(to);

    //             topList = drDao.getTop5Drinks(fromDate, toDate);

    //             // giữ lại giá trị form
    //             req.setAttribute("fromDate", from);
    //             req.setAttribute("toDate", to);

    //         } else {
    //             Date today = sdf.parse(sdf.format(new Date()));
    //             Date past = new Date(today.getTime() - 7L * 24 * 60 * 60 * 1000);

    //             topList = drDao.getTop5Drinks(past, today);

    //             req.setAttribute("fromDate", sdf.format(past));
    //             req.setAttribute("toDate", sdf.format(today));
    //         }

    //         req.setAttribute("top5List", topList);

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         req.setAttribute("error", "Lỗi khi lấy dữ liệu Top 5");
    //         req.setAttribute("error", e.toString());
    //     }

    //     req.setAttribute("view", "/views/top5drink.jsp");
    //     req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    // }

    // // @Override
    // // public void doPost(HttpServletRequest req, HttpServletResponse resp)
    // // throws ServletException, IOException {

    // // req.setAttribute("view", "/views/top5drink.jsp");
    // // req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    // // }

}
