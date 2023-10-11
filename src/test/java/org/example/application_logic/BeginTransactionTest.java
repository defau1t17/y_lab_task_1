package org.example.application_logic;

import org.example.application_entity.Client.Client;
import org.example.application_entity.Client.SessionClient;
import org.example.application_entity.Transaction.Operation;
import org.example.application_entity.Transaction.Transaction;
import org.example.application_logic.Service.ClientServices.AccountManager;
import org.example.application_logic.Service.ClientServices.BeginTransaction;
import org.example.application_logic.Service.GenerateID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class BeginTransactionTest {

    private Client client;

    private Transaction transaction;


    @BeforeEach
    void newClient() {
        client = new Client(GenerateID.generateID(), "test", "test", "test");
        AccountManager.createClient(client.getNick_name(), client.getUsername(), client.getPassword());
        SessionClient.UpdateSessionClient(client);
    }

    @Test
    void replenishmentTest() {
        Assertions.assertTrue(new BeginTransaction().replenishment(Operation.REPLENISHMENT, 56.3));
    }

    @Test
    void withdrawalTest() {

        Assertions.assertFalse(new BeginTransaction().withdrawal(Operation.WITHDRAWAL, 32));
    }

    @Test
    void creditTest() {
        Assertions.assertTrue(new BeginTransaction().creditAdd(Operation.CREDIT, 56.3));
    }


}
