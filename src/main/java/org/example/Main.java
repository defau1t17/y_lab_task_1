package org.example;

import org.example.Entity.AccountManager;
import org.example.Entity.Client;
import org.example.Entity.SessionClient;
import org.example.In.InputReader;
import org.example.Repository.ClientsData;

public class Main {

    public static void main(String[] args) {
        while (true) {
            if (SessionClient.session_client == null) {
                while (!InputReader.beginDialog()) {

                }
            } else {
                while (!InputReader.chooseMove()) {

                }
            }


        }


    }

}