package org.example.DAO;

import org.example.Entity.Client;
import org.example.Entity.Transaction;

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
