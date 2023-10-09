package org.example.application_logic.Service;


import java.util.UUID;

public class GenerateID {

    /**
     * Функция нужна для генерации уникального Id для транзакции и клиента
     * @return уникальное id
     */
    public static String generateID() {
        return UUID.randomUUID().toString();
    }

}
