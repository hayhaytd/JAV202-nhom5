package utils;

import java.sql.*;

public class XJDBC {

    private static final String URL =
        "jdbc:sqlserver://localhost:1433;databaseName=PolyCafe_Jav202;encrypt=true;trustServerCertificate=true";

    private static final String USER = "sa";
    private static final String PASS = "123456";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // INSERT, UPDATE, DELETE
    public static int update(String sql, Object... args) {
        try (
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            return ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // SELECT
    public static ResultSet query(String sql, Object... args) {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            return ps.executeQuery();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}