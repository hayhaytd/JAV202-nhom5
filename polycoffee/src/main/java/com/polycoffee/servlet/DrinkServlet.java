package com.polycoffee.servlet;

import com.polycoffee.dao.DrinkDAO;
import com.polycoffee.entity.Drink;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/drink")
public class DrinkServlet extends HttpServlet {

    private DrinkDAO drinkDAO;

    @Override
    public void init() throws ServletException {
        drinkDAO = new DrinkDAO(); // khởi tạo DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Lấy danh sách đồ uống
            List<Drink> list = drinkDAO.findAll();

            System.out.println("👉 Servlet size = " + list.size()); // DEBUG

            // Gửi dữ liệu sang JSP
            request.setAttribute("drinks", list);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải danh sách đồ uống!");
        }

        // Forward sang JSP
        request.setAttribute("view", "/views/drink.jsp");
        request.getRequestDispatcher("/views/index.jsp").forward(request, response);
    }
}