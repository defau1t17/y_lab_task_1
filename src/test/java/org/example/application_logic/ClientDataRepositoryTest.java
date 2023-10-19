package org.example.application_logic;

import org.example.application_entity.Client.Client;
import org.example.application_entity.Client.SessionClient;
import org.example.application_logic.Connector.DBConnectorConfig;
import org.example.application_logic.Repository.ClientsDataRepository;
import org.example.application_logic.Service.ClientServices.AccountManager;
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

public class ClientDataRepositoryTest {

    private Client client;

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
    void doesClientExistsTest() {
        Assertions.assertFalse(new ClientsDataRepository().doesClientExists("y_lab"));
        Assertions.assertTrue(new ClientsDataRepository().doesClientExists("test"));
    }

    @Test
    void findClientByUserNameTest() {
        Assertions.assertNotNull(new ClientsDataRepository().findClientByUserName("test"));
        Assertions.assertNull(new ClientsDataRepository().findClientByUserName("3"));
        Assertions.assertNull(new ClientsDataRepository().findClientByUserName("y_lab"));
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
