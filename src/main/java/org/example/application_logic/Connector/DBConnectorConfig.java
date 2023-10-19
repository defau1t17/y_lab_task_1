package org.example.application_logic.Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectorConfig {

    /**
     * URL для подключения к базе данных
     */
    private static final String URL = "jdbc:postgresql://localhost:5432/y_lab_db";
    /**
     * Username для подключения к базе данных
     */
    private static final String USER = "y_lab";
    /**
     * Password для подключения к базе данных
     */
    private static final String PASSWORD = "y_lab";


    /**
     * Функция-конфиг для получения подключения к базе данных
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
