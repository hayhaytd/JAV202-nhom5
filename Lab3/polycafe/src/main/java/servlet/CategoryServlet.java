package servlet;

import dao.CategoryDAO;
import entity.Category;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/manager/category")
public class CategoryServlet extends HttpServlet {

    CategoryDAO dao = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            req.setAttribute("list", dao.findAll());
            req.getRequestDispatcher("/views/category/list.jsp").forward(req, resp);

        } else if (action.equals("edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("c", dao.findById(id));
            req.getRequestDispatcher("/views/category/form.jsp").forward(req, resp);

        } else if (action.equals("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.delete(id);
            resp.sendRedirect("category");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        boolean active = req.getParameter("active") != null;

        Category c = new Category();
        c.setName(name);
        c.setActive(active);

        if (id == null || id.isEmpty()) {
            dao.insert(c);
        } else {
            c.setId(Integer.parseInt(id));
            dao.update(c);
        }

        resp.sendRedirect("category");
    }
}
