package org.example.application_logic.Repository;

import org.example.application_entity.Client.Client;
import org.example.application_entity.DAO.TransactionDAO;
import org.example.application_entity.Transaction.Operation;
import org.example.application_entity.Transaction.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class OperationData {

    private static final ArrayList<TransactionDAO> transactionHistory = new ArrayList<>();

    private static final String URL = "jdbc:postgresql://localhost:5432/y_lab_db";

    private static final String USER = "y_lab";

    private static final String PASSWORD = "y_lab";

    /**
     * функция проверяет существует ли клиент в истории транзакций
     *
     * @param client авторизованный клиент
     * @return возвращает true если пользователь найден в истории, иначе вернет false
     */
    public static boolean doesClientExistsInHistory(Client client) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions.transaction  WHERE client_id = ?");
            statement.setInt(1, Integer.parseInt(client.getId()));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                statement.close();

                connection.close();
                return true;
            }

            statement.close();

            connection.close();


        } catch (Exception e) {
        }


        return false;
    }

    /**
     * Функция предназначена для добавления новой транзакции в историю клиента, если же операций нет, то создается новый лист с операциями
     *
     * @param client      авторизированный клиент
     * @param transaction транзакция
     * @return вернет true если транзакция была добавлена успешно, иначе вернет false
     */
    public boolean addNewOperationForClient(Client client, Transaction transaction) {

        try {

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transactions.transaction (client_id, operation_type, magnitude, transdate) VALUES (?,?,?,?)");
            preparedStatement.setInt(1, Integer.parseInt(client.getId()));
            preparedStatement.setString(2, transaction.getOperation_type().toString());
            preparedStatement.setDouble(3, transaction.getMagnitude());
            preparedStatement.setString(4, transaction.getLocalDate());

            preparedStatement.executeUpdate();

            preparedStatement.close();

            connection.commit();

            connection.close();

            return true;

        } catch (Exception e) {

        }
        return false;
    }


    /**
     * Функция предназначена для отображения транзакций клиента, если же лист с транзакциями пуст, то вернется null
     *
     * @param client авторизированный клиент
     * @return возвравт листра с транзакциями, либо null
     */
    public ArrayList<Transaction> displayAccountTransaction(Client client) {
        Transaction transaction = null;
        ArrayList<Transaction> transactions = new ArrayList<>();

        if (doesClientExistsInHistory(client)) {
            try {
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM transactions.transaction WHERE client_id = ?");
                preparedStatement.setInt(1, Integer.parseInt(client.getId()));

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    transaction = new Transaction(resultSet.getString(1), Operation.valueOf(resultSet.getString(3)), resultSet.getDouble(4), resultSet.getString(5));
                    transactions.add(transaction);
                }
                preparedStatement.close();

                connection.close();

            } catch (Exception e) {
            }
        }

        return transactions;
    }
}
