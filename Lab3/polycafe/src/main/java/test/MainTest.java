package test;

import dao.CategoryDAO;
import entity.Category;

public class MainTest {
    public static void main(String[] args) {

        CategoryDAO dao = new CategoryDAO();

        // INSERT
        dao.insert(new Category(0, "Coffee", true));

        // SELECT
        dao.findAll().forEach(c ->
                System.out.println(c.getName())
        );

        System.out.println("DONE");
    }
}