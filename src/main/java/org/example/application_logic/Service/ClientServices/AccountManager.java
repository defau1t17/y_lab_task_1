package org.example.application_logic.Service.ClientServices;

import org.example.application_entity.Entity.Client.Client;
import org.example.application_entity.Entity.Client.SessionClient;
import org.example.application_logic.Repository.ClientsData;
import org.example.application_logic.Service.GenerateID;

public class AccountManager {

    /**
     * Функция авторизации клиента, если он находится в базе данных и введенный пароль совпадает, то клиента авторизирует
     *
     * @param username username клиентиа
     * @param password password клиента
     * @return true если клиент найден и процесс авторизации произошел успешно, иначе вернется false
     */
    public static boolean login(String username, String password) {
        if (new ClientsData().doesClientExists(username)) {
            if (new ClientsData().findClientByUserName(username).getPassword().equals(password)) {
                SessionClient.session_client = new ClientsData().findClientByUserName(username);
                return true;
            } else return false;

        } else return false;

    }

    /**
     * Фунция по созданию нового клиента  в листе.
     *
     * @param nick     ник-нейм клиента.
     * @param username username клиента
     * @param password password клиента
     * @return вернется true если клиент не обнаружен в листе и успешно создан, иначе вернется ошибка
     */
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
