package org.example.application_logic;

import org.example.application_entity.Client.Client;
import org.example.application_entity.Client.SessionClient;
import org.example.application_logic.Service.ClientServices.AccountManager;
import org.example.application_logic.Service.GenerateID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountManagerTest {

    private Client client;

    @BeforeEach
    void newClient() {
        client = new Client(GenerateID.generateID(), "test", "test", "test");
        AccountManager.createClient(client.getNick_name(), client.getUsername(), client.getPassword());
        SessionClient.UpdateSessionClient(client);
    }

    @Test
    void login() {
        boolean success = AccountManager.login(client.getUsername(), client.getPassword());
        Assertions.assertTrue(success);
        boolean deny = AccountManager.login("hi", "test");
        Assertions.assertFalse(deny);
    }

    @Test
    void createClient() {
        boolean success = AccountManager.createClient("hello", "hello", "hello");
        Assertions.assertTrue(success);
        boolean deny = AccountManager.createClient("test", "test", "test");
        Assertions.assertFalse(deny);
    }


}
