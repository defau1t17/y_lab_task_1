package org.example.Entity;

public class SessionClient {

    public static Client session_client = null;

    public static void UpdateSessionClient(Client client) {
        session_client = client;
    }


}
