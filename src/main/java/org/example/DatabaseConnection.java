package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final String url = "jdbc:postgresql://localhost:5432/Records";
    private final String user = "postgres";
    private final String password = "sochima";

    private static DatabaseConnection instance;

    public static DatabaseConnection getInstance(){
        if(instance ==  null){
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
