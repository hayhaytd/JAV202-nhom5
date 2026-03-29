package test;

import dao.CategoryDAO;
import entity.Category;
import dao.UserDAO;
import entity.User;

public class MainTest {
    public static void main(String[] args) {

        CategoryDAO dao = new CategoryDAO();
        UserDAO userDAO = new UserDAO();
        // INSERT
        dao.insert(new Category(0, "Coffee", true));

        // SELECT
        dao.findAll().forEach(c ->
                System.out.println(c.getName())
        );

        userDAO.selectAll().forEach(u ->
                System.out.println(u.getFullname())
        );
        System.out.println("DONE");
    }
}