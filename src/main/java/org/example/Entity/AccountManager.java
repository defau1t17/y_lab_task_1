package org.example.Entity;

import org.example.Repository.ClientsData;
import org.example.Service.GenerateID;

public class AccountManager {

    public static boolean login(String username, String password) {
        if (new ClientsData().doesClientExists(username)) {
            if (new ClientsData().findClientByUserName(username).getPassword().equals(password)) {
                SessionClient.session_client = new ClientsData().findClientByUserName(username);
                return true;
            } else return false;

        } else return false;

    }

    public static boolean createClient(String nick, String username, String password) {
        if (!new ClientsData().doesClientExists(username)) {
            Client newClient = new Client(GenerateID.generateID(), nick, username, password);
            new ClientsData().addClient(newClient);
            SessionClient.session_client = newClient;
            return true;
        }
        return false;
    }


}
