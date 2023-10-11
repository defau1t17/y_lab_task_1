package org.example.application_logic;

import org.example.application_entity.Client.Client;
import org.example.application_entity.Client.SessionClient;
import org.example.application_logic.Repository.ClientsData;
import org.example.application_logic.Service.ClientServices.AccountManager;
import org.example.application_logic.Service.GenerateID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClientDataTest {

    private Client client;


    @BeforeEach
    void newClient() {
        client = new Client(GenerateID.generateID(), "test", "test", "test");
        AccountManager.createClient(client.getNick_name(), client.getUsername(), client.getPassword());
        SessionClient.UpdateSessionClient(client);
    }

    @Test
    void doesClientExistsTest() {
        Assertions.assertTrue(new ClientsData().doesClientExists(SessionClient.session_client.getUsername()));
    }

    @Test
    void findClientByUserNameTest(){
        Assertions.assertNotNull(new ClientsData().findClientByUserName(SessionClient.session_client.getUsername()));
    }


}
