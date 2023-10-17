package org.example.application_logic;

import org.example.application_entity.Client.Client;
import org.example.application_entity.Client.SessionClient;
import org.example.application_entity.Transaction.Operation;
import org.example.application_entity.Transaction.Transaction;
import org.example.application_logic.Connector.DBConnectorConfig;
import org.example.application_logic.Repository.TransactionsDataRepository;
import org.example.application_logic.Service.ClientServices.AccountManager;
import org.example.application_logic.Service.ClientServices.BeginTransaction;
import org.example.application_logic.Service.GenerateID;
import org.example.application_logic.Service.GlobalService.LiquibaseService;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

@Testcontainers

public class TransactionDataRepository {
    @Container
    private static final PostgreSQLContainer<?> databaseContainer = new PostgreSQLContainer<>("postgres:16.0");

    private static Connection connection = DBConnectorConfig.getConnection();

    private static PreparedStatement preparedStatement;

    static Client test_1 = null;

    static Client test_2 = null;

    @BeforeAll
    static void init() throws SQLException {
        new LiquibaseService().createSchemasAndTables();
        Client client = new Client("", "test", "test", "test");
        AccountManager.createClient(client.getNick_name(), client.getUsername(), client.getPassword());
        new BeginTransaction().replenishment(Operation.REPLENISHMENT, 56.3);
        test_1 = SessionClient.session_client;
        System.out.println(test_1.getUsername());
        AccountManager.createClient("test", "admin", "admin");
        test_2 = SessionClient.session_client;
        AccountManager.createClient("test", "main", "main");


    }


    @Test
    void addNewOperationForClientTest() {
        boolean success = new TransactionsDataRepository().addNewOperationForClient(SessionClient.session_client, new Transaction("", Operation.REPLENISHMENT, 25.4, LocalDate.now().toString()));
        Assertions.assertTrue(success);
    }


    @Test
    void displayAccountTransactionTest() {
        Assertions.assertNotNull(new TransactionsDataRepository().displayAccountTransaction(SessionClient.session_client));
        Assertions.assertNotNull(new TransactionsDataRepository().displayAccountTransaction(test_1));
        Assertions.assertNull(new TransactionsDataRepository().displayAccountTransaction(test_2));


    }

    @Test
    void doesClientExistsInHistoryTest() {
        Assertions.assertTrue(TransactionsDataRepository.doesClientExistsInHistory(SessionClient.session_client));
        Assertions.assertTrue(TransactionsDataRepository.doesClientExistsInHistory(test_1));
        Assertions.assertFalse(TransactionsDataRepository.doesClientExistsInHistory(test_2));

    }

    @AfterAll
    static void remove() {
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("DELETE FROM clients.wallet WHERE username = 'test' ");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM clients.wallet WHERE username = 'admin' ");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE  FROM transactions.transaction WHERE client_id = ?");
            preparedStatement.setInt(1, Integer.parseInt(SessionClient.session_client.getId()));
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.commit();
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
