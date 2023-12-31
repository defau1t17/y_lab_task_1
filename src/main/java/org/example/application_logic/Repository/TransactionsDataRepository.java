package org.example.application_logic.Repository;

import org.example.application_entity.Client.Client;
import org.example.application_entity.Transaction.Operation;
import org.example.application_entity.Transaction.Transaction;
import org.example.application_logic.Connector.DBConnectorConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TransactionsDataRepository {

    /**
     * функция проверяет существует ли клиент в истории транзакций базы данных
     *
     * @param client авторизованный клиент
     * @return возвращает true если пользователь найден в истории, иначе вернет false
     */
    public static boolean doesClientExistsInHistory(Client client) {
        try {
            Connection connection = DBConnectorConfig.getConnection();

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
     * Функция предназначена для добавления новой транзакции в базу данных
     *
     * @param client      авторизированный клиент
     * @param transaction транзакция
     * @return вернет true если транзакция была добавлена успешно, иначе вернет false
     */
    public boolean addNewOperationForClient(Client client, Transaction transaction) {

        try {

            Connection connection = DBConnectorConfig.getConnection();

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
        ArrayList<Transaction> transactions = null;

        if (doesClientExistsInHistory(client)) {
            try {
                Connection connection = DBConnectorConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM transactions.transaction WHERE client_id = ?");
                preparedStatement.setInt(1, Integer.parseInt(client.getId()));

                ResultSet resultSet = preparedStatement.executeQuery();
                transactions = new ArrayList<>();
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
