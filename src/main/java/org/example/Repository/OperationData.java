package org.example.Repository;

import org.example.DAO.TransactionDAO;
import org.example.Entity.Client;
import org.example.Entity.SessionClient;
import org.example.Entity.Transaction;

import java.util.ArrayList;
import java.util.HashMap;

public class OperationData {

    private static final ArrayList<TransactionDAO> transactionHistory = new ArrayList<>();

    public static ArrayList<TransactionDAO> getTransactionHistory() {
        return transactionHistory;
    }

    public static boolean doesClientExistsInHistory(Client client) {
//        System.out.println("incoming client " + client.getUsername());
        for (TransactionDAO dao : transactionHistory) {
            if (dao.getClient().equals(client))
                return true;

        }
        return false;
    }

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
        System.out.println("something wrong?");
        return false;
    }

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
