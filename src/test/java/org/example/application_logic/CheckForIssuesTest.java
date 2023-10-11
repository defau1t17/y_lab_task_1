package org.example.application_logic;

import org.example.application_entity.Client.SessionClient;
import org.example.application_logic.Handler.CheckForIssues;
import org.example.application_logic.Service.GenerateID;
import org.example.application_entity.Client.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckForIssuesTest {

    private Client client;

    @BeforeEach
    void createClient() {
        client = new Client(GenerateID.generateID(), "test", "test", "test");
        client.getBalance().setValue(20);
    }

    @Test
    void enoughMoney() {
        SessionClient.UpdateSessionClient(client);
        boolean enought = CheckForIssues.enoughMoney(10);
        Assertions.assertTrue(enought);
    }

    @Test
    void notEnoughMoney() {
        SessionClient.UpdateSessionClient(client);
        boolean not_enought = CheckForIssues.enoughMoney(100);
        Assertions.assertFalse(not_enought);
    }

}
