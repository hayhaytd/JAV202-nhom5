package dao;

import entity.Drink;
import utils.XJDBC;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DrinkDAO {

    public List<Drink> findAll() {
        List<Drink> list = new ArrayList<>();
        String sql = "SELECT * FROM Drink";

        try {
            ResultSet rs = XJDBC.query(sql);
            while (rs.next()) {
                list.add(new Drink(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("image"),
                        rs.getString("description"),
                        rs.getBoolean("active"),
                        rs.getInt("category_id")
                ));
            }
            rs.getStatement().getConnection().close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(Drink d) {
        String sql = "INSERT INTO Drink(name, price, image, description, active, category_id) VALUES(?,?,?,?,?,?)";
        XJDBC.update(sql,
                d.getName(),
                d.getPrice(),
                d.getImage(),
                d.getDescription(),
                d.isActive(),
                d.getCategoryId()
        );
    }

    public void update(Drink d) {
        String sql = "UPDATE Drink SET name=?, price=?, image=?, description=?, active=?, category_id=? WHERE id=?";
        XJDBC.update(sql,
                d.getName(),
                d.getPrice(),
                d.getImage(),
                d.getDescription(),
                d.isActive(),
                d.getCategoryId(),
                d.getId()
        );
    }

    public void delete(int id) {
        XJDBC.update("DELETE FROM Drink WHERE id=?", id);
    }
}