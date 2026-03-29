package dao;

import entity.BillDetail;
import utils.XJDBC;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BillDetailDAO {

    public List<BillDetail> findByBillId(int billId) {
        List<BillDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM Bill_Details WHERE bill_id=?";

        try {
            ResultSet rs = XJDBC.query(sql, billId);
            while (rs.next()) {
                list.add(new BillDetail(
                        rs.getInt("id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getInt("bill_id"),
                        rs.getInt("drink_id")
                ));
            }
            rs.getStatement().getConnection().close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(BillDetail bd) {
        String sql = "INSERT INTO Bill_Details(quantity, price, bill_id, drink_id) VALUES(?,?,?,?)";
        XJDBC.update(sql,
                bd.getQuantity(),
                bd.getPrice(),
                bd.getBillId(),
                bd.getDrinkId()
        );
    }

    public void deleteByBillId(int billId) {
        XJDBC.update("DELETE FROM Bill_Details WHERE bill_id=?", billId);
    }
}