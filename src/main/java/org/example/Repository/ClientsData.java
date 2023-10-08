package org.example.Repository;

import org.example.Entity.Client;
import org.example.Entity.Operation;
import org.example.Entity.SessionClient;

import java.util.ArrayList;
import java.util.Optional;

public class ClientsData {

    private static final ArrayList<Client> clients = new ArrayList<>();

    public void addClient(Client client) {
        clients.add(client);
        System.out.println(client.getId());
    }

    public boolean doesClientExists(String username) {
        for (Client find : clients) {
            if (find.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public Client findClientByUserName(String username) {
        for (Client find : clients) {
            if (find.getUsername().equals(username)) {
                return find;
            }
        }
        return null;
    }

    public static ArrayList<Client> getClients() {
        return clients;
    }

    public static void updateClientBalanceById(String id, double quantity, Operation operation) {
        for (Client client : clients) {
            if (client.getId().equals(id)) {
                switch (operation) {
                    case REPLENISHMENT -> client.getBalance().setValue(client.getBalance().getValue() + quantity);
                    case CREDIT -> client.getBalance().setValue(client.getBalance().getValue() + quantity);
                    case WITHDRAWAL -> client.getBalance().setValue(client.getBalance().getValue() - quantity);
                }
                SessionClient.UpdateSessionClient(client);
            }
        }
    }
}
