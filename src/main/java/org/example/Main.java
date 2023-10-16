package org.example;

import org.example.application_entity.Client.SessionClient;
import org.example.application_logic.In.InputReader;
import org.example.application_logic.Service.GlobalService.LiquibaseService;

import java.io.IOException;

public class Main {


    /**
     * Функция предназначена для общения клиента с приложением. Клиент вводит значения в консоль, вызывая разные действия приложения
     * Так же при запуске приложения создаются схемы и таблицы для бд, если до этого они не были созданы
     * @param args
     */
    public static void main(String[] args) throws IOException {

        new LiquibaseService().createSchemasAndTables();

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