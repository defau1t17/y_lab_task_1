package org.example.application_logic.Repository;

import org.example.application_entity.DAO.TransactionDAO;
import org.example.application_entity.Client.Client;
import org.example.application_entity.Transaction.Transaction;

import java.util.ArrayList;

public class OperationData {

    private static final ArrayList<TransactionDAO> transactionHistory = new ArrayList<>();

    /**
     * функция проверяет существует ли клиент в истории транзакций
     * @param client авторизованный клиент
     * @return возвращает true если пользователь найден в истории, иначе вернет false
     */
    public static boolean doesClientExistsInHistory(Client client) {
        for (TransactionDAO dao : transactionHistory) {
            if (dao.getClient().equals(client))
                return true;

        }
        return false;
    }

    /**
     * Функция предназначена для добавления новой транзакции в историю клиента, если же операций нет, то создается новый лист с операциями
     * @param client      авторизированный клиент
     * @param transaction транзакция
     * @return вернет true если транзакция была добавлена успешно, иначе вернет false
     */
    public boolean addNewOperationForClient(Client client, Transaction transaction) {
        if (doesClientExistsInHistory(client)) {
            for (TransactionDAO dao : transactionHistory) {
                if (dao.getClient().equals(client)) {
                    dao.getTransactions().add(transaction);
                    return true;
                }
            }

        } else {
            ArrayList<Transaction> newTransactionList = new ArrayList<>();
            newTransactionList.add(transaction);
            TransactionDAO transactionDAO = new TransactionDAO(client, newTransactionList);
            transactionHistory.add(transactionDAO);
            return true;
        }
        return false;
    }


    /**
     * Функция предназначена для отображения транзакций клиента, если же лист с транзакциями пуст, то вернется null
     * @param client авторизированный клиент
     * @return возвравт листра с транзакциями, либо null
     */
    public ArrayList<Transaction> displayAccountTransaction(Client client) {
        if (doesClientExistsInHistory(client)) {
            for (TransactionDAO dao : transactionHistory) {
                if (dao.getClient().equals(client)) {
                    return dao.getTransactions();
                }
            }
        }
        return null;
    }


}
