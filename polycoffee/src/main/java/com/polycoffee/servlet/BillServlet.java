package com.polycoffee.servlet;

import com.polycoffee.dao.BillDAO;
import com.polycoffee.dao.BillDetailDAO;
import com.polycoffee.entity.Bill;
import com.polycoffee.entity.BillDetail;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/bill")
public class BillServlet extends HttpServlet {

    private BillDAO billDAO;
    private BillDetailDAO billDetailDAO;

    @Override
    public void init() {
        billDAO = new BillDAO();
        billDetailDAO = new BillDetailDAO();
    }

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("detail".equals(action)) {
                // ===== XEM CHI TIẾT =====
                Integer id = Integer.parseInt(request.getParameter("id"));
                Bill bill = billDAO.findById(id);
                List<BillDetail> details = billDetailDAO.findByBill(id);

                request.setAttribute("bill", bill);
                request.setAttribute("details", details);
                request.setAttribute("view", "/views/billDetail.jsp");

            } else {
                // ===== DANH SÁCH (CÓ PHÂN TRANG) =====
                String pageStr = request.getParameter("page");
                int page = (pageStr != null && !pageStr.isEmpty())
                        ? Integer.parseInt(pageStr) : 1;
                int size = 10;

                List<Bill> bills = billDAO.findAllPaged(page, size);
                long total = billDAO.countAll();
                int totalPages = (int) Math.ceil((double) total / size);

                request.setAttribute("bills", bills);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("view", "/views/billManager.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/views/index.jsp").forward(request, response);
    }

    // ================= POST =================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("cancel".equals(action)) {
                // ===== HUỶ ĐƠN HÀNG =====
                Integer id = Integer.parseInt(request.getParameter("id"));
                Bill bill = billDAO.findById(id);

                if (bill != null) {
                    bill.setStatus(2); // 0: chờ, 1: hoàn thành, 2: đã huỷ
                    billDAO.update(bill);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("bill");
    }
}