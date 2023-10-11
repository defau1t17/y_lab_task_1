package org.example;

import org.example.application_entity.Client.SessionClient;
import org.example.application_logic.In.InputReader;

import java.io.IOException;

public class Main {


    /**
     * Функция предназначена для общения клиента с приложением. Клиент вводит значения в консоль, вызывая разные действия приложения
     * @param args
     */
    public static void main(String[] args) throws IOException {
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