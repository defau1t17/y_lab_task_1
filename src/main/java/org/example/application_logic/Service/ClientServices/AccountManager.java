package org.example.application_logic.Service.ClientServices;

import org.example.application_logic.Repository.ClientsDataRepository;
import org.example.application_logic.Service.GenerateID;
import org.example.application_entity.Client.Client;
import org.example.application_entity.Client.SessionClient;

public class AccountManager {

    /**
     * Функция авторизации клиента, если он находится в базе данных и введенный пароль совпадает, то клиента авторизирует
     *
     * @param username username клиентиа
     * @param password password клиента
     * @return true если клиент найден и процесс авторизации произошел успешно, иначе вернется false
     */
    public static boolean login(String username, String password) {
        if (new ClientsDataRepository().doesClientExists(username)) {
            if (new ClientsDataRepository().findClientByUserName(username).getPassword().equals(password)) {
                SessionClient.session_client = new ClientsDataRepository().findClientByUserName(username);
                return true;
            } else return false;

        } else return false;

    }

    /**
     * Фунция по созданию нового клиента в базе данных, если клиента с таким username не существует в базе, то клиент успешно сохраняется
     * Так же устанавливается глобальныый клиент
     * @param nick     ник-нейм клиента.
     * @param username username клиента
     * @param password password клиента
     * @return вернется true если клиент не обнаружен в листе и успешно создан, иначе вернется ошибка
     */
    public static boolean createClient(String nick, String username, String password) {
        if (!new ClientsDataRepository().doesClientExists(username)) {
            Client newClient = new Client("", nick, username, password);
            new ClientsDataRepository().addClient(newClient);
            SessionClient.session_client = new ClientsDataRepository().findClientByUserName(username);
            return true;
        }
        return false;
    }


}
