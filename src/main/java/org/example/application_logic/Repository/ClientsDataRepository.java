package org.example.application_logic.Repository;

import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import org.example.application_entity.Client.Client;
import org.example.application_entity.Transaction.Operation;
import org.example.application_entity.Client.SessionClient;

import java.sql.*;
import java.util.ArrayList;

public class ClientsDataRepository {
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
     * Функция добавления нового клиента в базу данных
     *
     * @param client новый клиент
     */
    public void addClient(Client client) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement("INSERT INTO clients.wallet (nickname, username, password,balance) VALUES (?, ?, ?,?)");
            statement.setString(1, client.getNick_name());
            statement.setString(2, client.getUsername());
            statement.setString(3, client.getPassword());
            statement.setDouble(4, client.getBalance().getValue());

            statement.executeUpdate();

            connection.commit();
            statement.close();
            connection.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Функция предназначена для проверки на существования клиента в базе данных
     *
     * @param username клиент для проверки
     * @return возвращает true если клиент существует, иначе вернет false
     */
    public boolean doesClientExists(String username) {

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clients.wallet WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * Функция предназначена для поиска клиента по username в базе данных
     *
     * @param username username клиента
     * @return вернет слиента если он существует в базе, иначе вернет null
     */
    public Client findClientByUserName(String username) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clients.wallet WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            Client client = null;
            if (resultSet.next()) {
                client = new Client(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                client.getBalance().setValue(resultSet.getDouble(5));
                return client;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Функция позволяет обновлять данные клиента в базе данных, а так же глобального клиента
     *
     * @param id        id клиента
     * @param quantity  денежное значение
     * @param operation тип операции
     */
    public static void updateClientBalanceById(String id, double quantity, Operation operation) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clients.wallet WHERE id = ?");
            statement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = statement.executeQuery();
            Client client = null;

            if (resultSet.next()) {
                client = new Client(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                client.getBalance().setValue(resultSet.getDouble(5));
                client.getBalance().setValue(operation(client, quantity, operation).getBalance().getValue());

                PreparedStatement updateStatement = connection.prepareStatement("UPDATE clients.wallet SET balance = ? WHERE id = ?");
                updateStatement.setDouble(1, client.getBalance().getValue());
                updateStatement.setInt(2, Integer.parseInt(id));
                updateStatement.executeUpdate();

                updateStatement.close();

                SessionClient.UpdateSessionClient(client);
            }

            statement.close();

            connection.commit();

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Фунекция нужна для уменьшения вложинности кода
     * Выполняет операции с балансом пользователя исходя из типа операции
     *
     * @param client    обновляемый клиент
     * @param quantity  денежное значение
     * @param operation тип операции
     */

    public static Client operation(Client client, double quantity, Operation operation) {
        switch (operation) {
            case REPLENISHMENT -> client.getBalance().setValue(client.getBalance().getValue() + quantity);
            case CREDIT -> client.getBalance().setValue(client.getBalance().getValue() + quantity);
            case WITHDRAWAL -> client.getBalance().setValue(client.getBalance().getValue() - quantity);
        }
        return client;
    }

}
