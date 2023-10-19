package org.example.application_logic;

import liquibase.exception.LiquibaseException;
import org.example.application_entity.Client.Client;
import org.example.application_entity.Client.SessionClient;
import org.example.application_logic.Connector.DBConnectorConfig;
import org.example.application_logic.Service.ClientServices.AccountManager;
import org.example.application_logic.Service.GlobalService.LiquibaseService;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.bouncycastle.asn1.dvcs.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;


@Testcontainers
public class AccountManagerTest {

    @Container
    private static final PostgreSQLContainer<?> databaseContainer = new PostgreSQLContainer<>("postgres:16.0");

    private static Connection connection = DBConnectorConfig.getConnection();

    private static PreparedStatement preparedStatement;


    @BeforeAll
    static void init() throws SQLException {
        new LiquibaseService().createSchemasAndTables();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO clients.wallet(nickname, username, password, balance) VALUES (?,?,?,?)");
        preparedStatement.setString(1, "test");
        preparedStatement.setString(2, "test");
        preparedStatement.setString(3, "test");
        preparedStatement.setDouble(4, 0.3);
        preparedStatement.executeUpdate();
    }

    @Test
    void createClient() {
        boolean success = AccountManager.createClient("3", "3", "3");
        Assertions.assertTrue(success);
        boolean deny = AccountManager.createClient("test", "test", "test");
        Assertions.assertFalse(deny);
    }

    @Test
    void loginTest() {
        boolean success = AccountManager.login("test", "test");
        boolean deny = AccountManager.login("5", "5");
        Assertions.assertFalse(deny);
        Assertions.assertTrue(success);

    }

    @AfterAll
    static void remove() {
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("DELETE FROM clients.wallet WHERE username = 'test' ");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM clients.wallet WHERE username = '3' ");
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

