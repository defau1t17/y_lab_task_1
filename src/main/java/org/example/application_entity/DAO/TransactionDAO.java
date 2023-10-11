package org.example.application_entity.DAO;

import org.example.application_entity.Client.Client;
import org.example.application_entity.Transaction.Transaction;

import java.util.ArrayList;

public class TransactionDAO {

    private Client client;

    private ArrayList<Transaction> transactions;


    public TransactionDAO(Client client, ArrayList<Transaction> transactions) {
        this.transactions = transactions;
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }

}
