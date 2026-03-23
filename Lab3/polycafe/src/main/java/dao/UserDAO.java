package dao;

import entity.User;
import utils.XJDBC;

import java.sql.ResultSet;

public class UserDAO {

    public User login(String email, String password) {
        String sql = "SELECT * FROM [User] WHERE email=? AND password=? AND active=1";

        try {
            ResultSet rs = XJDBC.query(sql, email, password);

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getInt("role"),
                        rs.getBoolean("active")
                );
            }

            rs.getStatement().getConnection().close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}