package org.example.application_logic;

import org.example.application_entity.Client.Client;
import org.example.application_entity.Client.SessionClient;
import org.example.application_entity.Transaction.Operation;
import org.example.application_entity.Transaction.Transaction;
import org.example.application_logic.Connector.DBConnectorConfig;
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


@Testcontainers
public class BeginTransactionTest {

    @Container
    private static final PostgreSQLContainer<?> databaseContainer = new PostgreSQLContainer<>("postgres:16.0");

    private static Connection connection = DBConnectorConfig.getConnection();

    private static PreparedStatement preparedStatement;

    @BeforeAll
    static void init() throws SQLException {
        new LiquibaseService().createSchemasAndTables();
        Client client = new Client("", "test", "test", "test");
        AccountManager.createClient(client.getNick_name(), client.getUsername(), client.getPassword());
        System.out.println(SessionClient.session_client.getId());

    }

    @Test
    void replenishmentTest() {
        Assertions.assertTrue(new BeginTransaction().replenishment(Operation.REPLENISHMENT, 56.3));
    }

    @Test
    void withdrawalTest() {
        Assertions.assertTrue(new BeginTransaction().withdrawal(Operation.WITHDRAWAL, 32));
        Assertions.assertFalse(new BeginTransaction().withdrawal(Operation.WITHDRAWAL, 300));

    }

    @Test
    void creditTest() {
        Assertions.assertTrue(new BeginTransaction().creditAdd(Operation.CREDIT, 56.3));
    }

    @AfterAll
    static void remove() {
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("DELETE FROM clients.wallet WHERE username = 'test' ");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM clients.wallet WHERE username = '3' ");
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
