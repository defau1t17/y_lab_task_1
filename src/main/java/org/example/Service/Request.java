package org.example.Service;

import org.example.Entity.SessionClient;

public class Request {

    public static double getBalance() {
        return SessionClient.session_client.getBalance().getValue();
    }


}
