package org.example.Handler;

import org.example.Entity.SessionClient;

public class CheckForIssues {

    public static boolean enoughMoney(double value) {
        if ((SessionClient.session_client.getBalance().getValue() - value) >= 0 ) {
            return true;
        } else
            return false;
    }

}
