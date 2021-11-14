package service;

import java.sql.*;

public class Utils {
    private static final String URL = "jdbc:mysql://localhost:3306/maven_test";
    private static final String USER = "root";
    private static final String PASSWORD = "root";


    public PreparedStatement getStatement(String sql){
        try {
            return getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw  new RuntimeException();
    }

    private Connection getConnection() {
        try {
            return  DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
