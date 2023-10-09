package org.example.application_logic.Repository;

import org.example.application_entity.Entity.Client.Client;
import org.example.application_entity.Entity.Transaction.Operation;
import org.example.application_entity.Entity.Client.SessionClient;

import java.util.ArrayList;

public class ClientsData {

    private static final ArrayList<Client> clients = new ArrayList<>();

    /**
     * Функция добавления нового клиента в лист клиентов
     *
     * @param client новый клиент
     */
    public void addClient(Client client) {
        clients.add(client);
    }


    /**
     * Функция предназначена для проверки на существования клиента в бд
     *
     * @param username клиент для проверки
     * @return возвращает true если клиент существует, иначе вернет false
     */
    public boolean doesClientExists(String username) {
        for (Client find : clients) {
            if (find.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Функция предназначена для поиска клиента по username
     *
     * @param username username клиента
     * @return вернет слиента если он существует в базе, иначе вернет null
     */
    public Client findClientByUserName(String username) {
        for (Client find : clients) {
            if (find.getUsername().equals(username)) {
                return find;
            }
        }
        return null;
    }

    /**
     * Функция позволяет обновлять данные клиент
     * @param id id клиента
     * @param quantity денежное значение
     * @param operation тип операции
     */
    public static void updateClientBalanceById(String id, double quantity, Operation operation) {
        for (Client client : clients) {
            if (client.getId().equals(id)) {
                operation(client, quantity, operation);
            }
        }
    }

    /**
     * Фунекция нужна для уменьшения вложинности кода
     * @param client обновляемый клиент
     * @param quantity денежное значение
     * @param operation тип операции
     */

    public static void operation(Client client, double quantity, Operation operation) {
        switch (operation) {
            case REPLENISHMENT -> client.getBalance().setValue(client.getBalance().getValue() + quantity);
            case CREDIT -> client.getBalance().setValue(client.getBalance().getValue() + quantity);
            case WITHDRAWAL -> client.getBalance().setValue(client.getBalance().getValue() - quantity);
        }
        SessionClient.UpdateSessionClient(client);

    }

}
