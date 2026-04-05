package com.polycoffee.servlet;

import com.polycoffee.dao.DrinkDAO;
import com.polycoffee.dao.CategoryDAO;
import com.polycoffee.entity.Drink;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            String cidStr = request.getParameter("categoryId");
            String activeStr = request.getParameter("active");
            String pageStr = request.getParameter("page");

            Integer categoryId = (cidStr != null && !cidStr.isEmpty()) ? Integer.parseInt(cidStr) : null;
            Boolean active = (activeStr != null && !activeStr.isEmpty()) ? Boolean.parseBoolean(activeStr) : null;

            int page = (pageStr != null) ? Integer.parseInt(pageStr) : 0;
            int size = 10;

            List<Drink> list = drinkDAO.search(name, categoryId, active, page, size);
            Long total = drinkDAO.count(name, categoryId, active);

            int totalPages = (int) Math.ceil(total * 1.0 / size);

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

<<<<<<< HEAD
        // Forward sang JSP
        request.setAttribute("view", "/views/drink.jsp");
        request.getRequestDispatcher("/views/index.jsp").forward(request, response);
=======
        request.getRequestDispatcher("/views/drink.jsp").forward(request, response);
>>>>>>> 0246108772daa96ea05e7d6346a833d098d538d0
    }
}