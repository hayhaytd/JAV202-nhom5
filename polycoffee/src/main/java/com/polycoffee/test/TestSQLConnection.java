package com.polycoffee.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestSQLConnection {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=PolyCoffee";
        String user = "sa";
        String password = "123456";

        System.out.println("=== Bắt đầu kiểm tra kết nối SQL Server ===");

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            System.out.println("✅ Kết nối thành công!");
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("Database Product: " + meta.getDatabaseProductName());
            System.out.println("Database Version: " + meta.getDatabaseProductVersion());
            System.out.println("Driver Name: " + meta.getDriverName());
            System.out.println("Driver Version: " + meta.getDriverVersion());

        } catch (SQLException e) {
            System.out.println("❌ Kết nối thất bại!");
            System.out.println("Chi tiết lỗi: " + e.getMessage());
            e.printStackTrace(System.out); // in full stack trace ra console
        }

        System.out.println("=== Kết thúc kiểm tra kết nối SQL Server ===");
    }
}