package org.example.application_logic.Handler;

import org.example.application_entity.Entity.Client.SessionClient;

public class CheckForIssues {


    /**
     * Функция проверяет достоточно ли у клиента денег на балансе, чтобы произвести дебет средств
     *
     * @param value дебетовоое значение
     * @return да если, у клиента достаточно средств на балансе иначе вернет нет
     */
    public static boolean enoughMoney(double value) {
        if ((SessionClient.session_client.getBalance().getValue() - value) >= 0) {
            return true;
        } else
            return false;
    }

}
