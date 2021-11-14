package service;

import java.sql.*;

public class Utils {
    private static final String URL = "jdbc:mysql://localhost:3306/maven_test";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection getConnection() {
        try {
            return  DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    public PreparedStatement getStatement(String sql){
        try {
            return getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw  new RuntimeException();
    }
}
