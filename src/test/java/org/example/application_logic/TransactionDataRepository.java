package org.example.application_logic;

import org.example.application_entity.Client.Client;
import org.example.application_entity.Client.SessionClient;
import org.example.application_logic.Repository.TransactionsDataRepository;
import org.example.application_logic.Service.ClientServices.AccountManager;
import org.example.application_logic.Service.GenerateID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionDataRepository {
    private Client client;


    @BeforeEach
    void newClient() {
        client = new Client(GenerateID.generateID(), "test", "test", "test");
        AccountManager.createClient(client.getNick_name(), client.getUsername(), client.getPassword());
        SessionClient.UpdateSessionClient(client);
    }

    @Test
    void displayAccountTransactionTest() {

        Assertions.assertNull(new TransactionsDataRepository().displayAccountTransaction(SessionClient.session_client));
    }

    @Test
    void doesClientExistsInHistoryTest() {
        Assertions.assertFalse(TransactionsDataRepository.doesClientExistsInHistory(SessionClient.session_client));
    }


}