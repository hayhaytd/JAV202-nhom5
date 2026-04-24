package com.polycoffee.servlet;

import java.io.IOException;
import java.util.List;

import com.polycoffee.dao.BillDAO;
import com.polycoffee.dao.BillDetailDAO;
import com.polycoffee.dao.DrinkDAO;
import com.polycoffee.entity.Bill;
import com.polycoffee.entity.BillDetail;
import com.polycoffee.entity.Drink;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/sale")
public class SaleServlet extends HttpServlet {

    BillDAO bdao = new BillDAO();
    BillDetailDAO bddao = new BillDetailDAO();
    DrinkDAO ddao = new DrinkDAO();

    // ================= LOAD DATA =================

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        // ================= CREATE BILL =================
        if ("create".equals(action)) {
            createBill(req, resp);
            return;
        }

        // ================= LOAD BILL =================
        String billId = req.getParameter("billId");

        if (billId != null) {
            Bill bill = bdao.findById(Integer.parseInt(billId));

            if (bill != null) {
                req.setAttribute("bill", bill);

                List<BillDetail> details = bddao.findByBill(bill.getId());
                req.setAttribute("details", details);
            }
        }

        req.setAttribute("drinks", ddao.findAll());
        req.setAttribute("view", "/views/order.jsp");
        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }

    // ================= HANDLE ACTION =================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try {

            if ("add".equals(action)) {
                addDrink(req);
            }

            else if ("update".equals(action)) {
                updateQuantity(req);
            }

            else if ("remove".equals(action)) {
                removeItem(req);
            }

            else if ("pay".equals(action)) {
                payBill(req);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // luôn redirect về lại billDetail
        resp.sendRedirect("bill?action=detail&id=" + req.getParameter("billId"));
    }

    private void createBill(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        var user = (com.polycoffee.entity.User) session.getAttribute("user");

        Bill bill = new Bill();
        bill.setCode(bdao.generateBillCode());
        bill.setCreated_at(new java.util.Date());
        bill.setStatus(0); // chưa thanh toán
        bill.setTotal(0.0);

        if (user != null) {
            bill.setUser(user);
        }

        bdao.create(bill);

        // 🔥 redirect sang bill vừa tạo
        resp.sendRedirect("sale?billId=" + bill.getId());
    }

    // ================= ADD DRINK =================
    private void addDrink(HttpServletRequest req) {
        int billId = Integer.parseInt(req.getParameter("billId"));
        int drinkId = Integer.parseInt(req.getParameter("drinkId"));

        Bill bill = bdao.findById(billId);
        Drink drink = ddao.findById(drinkId);

        if (bill == null || drink == null)
            return;

        if (bill.getStatus() != 0)
            return;

        bddao.addDrinkToBill(bill, drink);

        updateTotal(billId);
    }

    // ================= UPDATE QUANTITY =================
    private void updateQuantity(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        int billId = Integer.parseInt(req.getParameter("billId"));

        bddao.updateQuantity(id, quantity);

        updateTotal(billId);
    }

    // ================= REMOVE =================
    private void removeItem(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        int billId = Integer.parseInt(req.getParameter("billId"));

        bddao.delete(id);

        updateTotal(billId);
    }

    // ================= PAY =================
    private void payBill(HttpServletRequest req) {
        int billId = Integer.parseInt(req.getParameter("billId"));

        Bill bill = bdao.findById(billId);
        if (bill == null)
            return;

        bill.setStatus(1);
        bdao.update(bill);
    }

    // ================= UPDATE TOTAL =================
    private void updateTotal(int billId) {
        Bill bill = bdao.findById(billId);
        double total = bddao.getTotalByBill(billId);
        bill.setTotal(total);
        bdao.update(bill);
    }
}