package org.example;

import org.example.application_entity.Entity.Client.SessionClient;
import org.example.application_logic.In.InputReader;

public class Main {


    /**
     * Функция предназначена для общения клиента с приложением. Клиент вводит значения в консоль, вызывая разные действия приложения
     * @param args
     */
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