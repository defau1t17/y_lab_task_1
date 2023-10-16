package org.example.application_entity.Client;

public class SessionClient {

    /**
     * Сессионный клиент, взимодействующий с проиложением
     */
    public static Client session_client = null;


    /**
     * Функция предназначена для обновления сессионного клиента
     *
     * @param client обновленный клиент
     */
    public static void UpdateSessionClient(Client client) {
        session_client = client;
    }


}
