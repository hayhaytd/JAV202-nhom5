package dao;

import entity.Category;
import utils.XJDBC;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    String SELECT_ALL_SQL = "SELECT * FROM Category";
    String SELECT_BY_ID_SQL = "SELECT * FROM Category WHERE id=?";
    String INSERT_SQL = "INSERT INTO Category(name, active) VALUES(?,?)";
    String UPDATE_SQL = "UPDATE Category SET name=?, active=? WHERE id=?";
    String DELETE_SQL = "DELETE FROM Category WHERE id=?";

    // ===== LẤY TẤT CẢ =====
    public List<Category> findAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    // ===== LẤY THEO ID (QUAN TRỌNG) =====
    public Category findById(int id) {
        List<Category> list = selectBySql(SELECT_BY_ID_SQL, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    // ===== THÊM =====
    public void insert(Category c) {
        XJDBC.update(INSERT_SQL, c.getName(), c.isActive());
    }

    // ===== SỬA =====
    public void update(Category c) {
        XJDBC.update(UPDATE_SQL, c.getName(), c.isActive(), c.getId());
    }

    // ===== XÓA =====
    public void delete(int id) {
        XJDBC.update(DELETE_SQL, id);
    }

    // ===== CORE =====
    protected List<Category> selectBySql(String sql, Object... args) {
        List<Category> list = new ArrayList<>();

        try {
            ResultSet rs = XJDBC.query(sql, args);
            while (rs.next()) {
                Category c = new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("active")
                );
                list.add(c);
            }
            rs.getStatement().getConnection().close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}