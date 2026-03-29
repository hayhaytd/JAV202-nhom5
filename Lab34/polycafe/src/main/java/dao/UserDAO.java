package dao;

import entity.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.XJDBC;

public class UserDAO {

    String INSERT_SQL = "INSERT INTO [User](fullname,email,password,phone,role,active) VALUES(?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE [User] SET fullname=?, email=?, password=?, phone=?, role=?, active=? WHERE id=?";
    String DELETE_SQL = "DELETE FROM [User] WHERE id=?";
    String SELECT_ALL_SQL = "SELECT * FROM [User]";
    String SELECT_BY_ID_SQL = "SELECT * FROM [User] WHERE id=?";
    String SELECT_BY_EMAIL_SQL = "SELECT * FROM [User] WHERE email=?";

    // ===== SELECT BY EMAIL (LOGIN) =====
    public User findByEmail(String email) {
        List<User> list = selectBySql(SELECT_BY_EMAIL_SQL, email);
        return list.size() > 0 ? list.get(0) : null;
    }

    // ===== INSERT =====
    public void insert(User entity) {
        XJDBC.update(INSERT_SQL,
                entity.getFullname(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getPhone(),
                entity.getRole(),
                entity.isActive()
        );
    }

    // ===== UPDATE =====
    public void update(User entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getFullname(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getPhone(),
                entity.getRole(),
                entity.isActive(),
                entity.getId()
        );
    }

    // ===== DELETE =====
    public void delete(int id) {
        XJDBC.update(DELETE_SQL, id);
    }

    // ===== SELECT ALL =====
    public List<User> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    // ===== SELECT BY ID =====
    public User selectById(int id) {
        List<User> list = selectBySql(SELECT_BY_ID_SQL, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    // ===== CORE =====
    protected List<User> selectBySql(String sql, Object... args) {
        List<User> list = new ArrayList<>();

        try {
            ResultSet rs = XJDBC.query(sql, args);
            while (rs.next()) {
                User entity = new User();
                entity.setId(rs.getInt("id"));
                entity.setFullname(rs.getString("fullname"));
                entity.setEmail(rs.getString("email"));
                entity.setPassword(rs.getString("password"));
                entity.setPhone(rs.getString("phone"));
                entity.setRole(rs.getInt("role"));
                entity.setActive(rs.getBoolean("active"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}