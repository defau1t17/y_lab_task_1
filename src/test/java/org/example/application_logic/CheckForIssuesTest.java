package org.example.application_logic;

import org.example.application_entity.Client.SessionClient;
import org.example.application_logic.Connector.DBConnectorConfig;
import org.example.application_logic.Handler.CheckForIssues;
import org.example.application_logic.Service.ClientServices.AccountManager;
import org.example.application_logic.Service.GenerateID;
import org.example.application_entity.Client.Client;
import org.example.application_logic.Service.GlobalService.LiquibaseService;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Testcontainers

public class CheckForIssuesTest {


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

        SessionClient.session_client.getBalance().setValue(15);

    }

    @Test
    void enoughMoney() {
        boolean enought = CheckForIssues.enoughMoney(10);
        Assertions.assertTrue(enought);
    }

    @Test
    void notEnoughMoney() {
        boolean not_enought = CheckForIssues.enoughMoney(100);
        Assertions.assertFalse(not_enought);
    }

    @AfterAll
    static void remove() {
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("DELETE FROM clients.wallet WHERE username = 'test' ");
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
