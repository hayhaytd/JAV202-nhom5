package com.polycoffee.servlet;

import com.polycoffee.dao.DrinkDAO;
import com.polycoffee.dao.CategoryDAO;
import com.polycoffee.entity.Drink;
import com.polycoffee.entity.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/drink")
public class DrinkServlet extends HttpServlet {

    private DrinkDAO drinkDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() {
        drinkDAO = new DrinkDAO();
        categoryDAO = new CategoryDAO();
    }

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // String action = request.getParameter("action");

        // // ================= TOP 5 =================
        // if ("top5".equals(action)) {
        //     try {
        //         String fromStr = request.getParameter("fromDate");
        //         String toStr = request.getParameter("toDate");

        //         if (fromStr != null && toStr != null) {
        //             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //             java.util.Date fromDate = sdf.parse(fromStr);
        //             java.util.Date toDate = sdf.parse(toStr);

        //             List<Object[]> topList = drinkDAO.getTop5Drinks(fromDate, toDate);
        //             request.setAttribute("top5List", topList);
        //         }

        //     } catch (Exception e) {
        //         e.printStackTrace();
        //         request.setAttribute("error", "Lỗi thống kê Top 5!");
        //     }

        //     request.getRequestDispatcher("/views/top5drink.jsp")
        //             .forward(request, response);
        //     return; // ❗ rất quan trọng
        // }

        // ================= NORMAL DRINK =================
        try {
            String name = request.getParameter("name");
            String cidStr = request.getParameter("categoryId");
            String activeStr = request.getParameter("active");
            String pageStr = request.getParameter("page");

            Integer categoryId = (cidStr != null && !cidStr.isEmpty())
                    ? Integer.parseInt(cidStr)
                    : null;

            Boolean active = (activeStr != null && !activeStr.isEmpty())
                    ? Boolean.parseBoolean(activeStr)
                    : null;

            int page = (pageStr != null && !pageStr.isEmpty())
                    ? Integer.parseInt(pageStr)
                    : 1;

            int size = 10;

            List<Drink> list = drinkDAO.search(name, categoryId, active, page, size);
            long total = drinkDAO.count(name, categoryId, active);
            int totalPages = (int) Math.ceil((double) total / size);

            request.setAttribute("drinks", list);
            request.setAttribute("categories", categoryDAO.findAll());
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            request.setAttribute("name", name);
            request.setAttribute("categoryId", categoryId);
            request.setAttribute("active", active);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("view", "/views/drink.jsp");
        request.getRequestDispatcher("/views/index.jsp")
                .forward(request, response);
    }

    // ================= POST (CRUD) =================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                create(request);
            } else if ("update".equals(action)) {
                update(request);
            } else if ("delete".equals(action)) {
                delete(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("drink");
    }

    // ================= CREATE =================
    private void create(HttpServletRequest request) {
        String name = request.getParameter("name");
        Double price = Double.parseDouble(request.getParameter("price"));
        Integer categoryId = Integer.parseInt(request.getParameter("categoryId"));
        Boolean active = Boolean.parseBoolean(request.getParameter("active"));

        Category category = categoryDAO.findById(categoryId);

        Drink d = new Drink();
        d.setName(name);
        d.setPrice(price);
        d.setCategory(category);
        d.setActive(active);

        drinkDAO.create(d);
    }

    // ================= UPDATE =================
    private void update(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Drink d = drinkDAO.findById(id);

        if (d != null) {
            d.setName(request.getParameter("name"));
            d.setPrice(Double.parseDouble(request.getParameter("price")));

            Integer categoryId = Integer.parseInt(request.getParameter("categoryId"));
            Category category = categoryDAO.findById(categoryId);
            d.setCategory(category);

            d.setActive(Boolean.parseBoolean(request.getParameter("active")));

            drinkDAO.update(d);
        }
    }

    // ================= DELETE =================
    private void delete(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));

        // bạn có thể đổi sang deleteSoft nếu muốn
        drinkDAO.delete(id);
    }
}