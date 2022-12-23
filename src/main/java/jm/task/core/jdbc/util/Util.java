package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    public  final String URL = "jdbc:mysql://localhost:3306/usersdb";
    public  final String USERNAME = "Kosmos";
    public  final String PASSWORD = "Denis@4491";
    private Connection conn;

    public Util() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public Connection getConn(){
        return conn;
    }
}
