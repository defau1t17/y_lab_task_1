package org.example.application_entity.Entity.Client;

public class SessionClient {

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
