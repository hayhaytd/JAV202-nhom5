package dao;


import entity.Category;
import utils.XJDBC;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category";

        try {
            ResultSet rs = XJDBC.query(sql);
            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("active")
                ));
            }
            rs.getStatement().getConnection().close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(Category c) {
        String sql = "INSERT INTO Category(name, active) VALUES(?,?)";
        XJDBC.update(sql, c.getName(), c.isActive());
    }

    public void update(Category c) {
        String sql = "UPDATE Category SET name=?, active=? WHERE id=?";
        XJDBC.update(sql, c.getName(), c.isActive(), c.getId());
    }

    public void delete(int id) {
        XJDBC.update("DELETE FROM Category WHERE id=?", id);
    }
}
