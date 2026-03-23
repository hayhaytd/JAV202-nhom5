package dao;

import entity.Bill;
import utils.XJDBC;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    public List<Bill> findAll() {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT * FROM Bill";

        try {
            ResultSet rs = XJDBC.query(sql);
            while (rs.next()) {
                list.add(new Bill(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getTimestamp("created_at"),
                        rs.getDouble("total"),
                        rs.getInt("status"),
                        rs.getInt("user_id")
                ));
            }
            rs.getStatement().getConnection().close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(Bill b) {
        String sql = "INSERT INTO Bill(code, created_at, total, status, user_id) VALUES(?,?,?,?,?)";

        XJDBC.update(sql,
                b.getCode(),
                new Timestamp(b.getCreatedAt().getTime()),
                b.getTotal(),
                b.getStatus(),
                b.getUserId()
        );
    }

    public void updateStatus(int id, int status) {
        XJDBC.update("UPDATE Bill SET status=? WHERE id=?", status, id);
    }

    public void updateTotal(int id, double total) {
        XJDBC.update("UPDATE Bill SET total=? WHERE id=?", total, id);
    }
}