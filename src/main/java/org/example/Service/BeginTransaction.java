package org.example.Service;

import org.example.Entity.Operation;
import org.example.Entity.SessionClient;
import org.example.Entity.Transaction;
import org.example.Handler.CheckForIssues;
import org.example.Repository.ClientsData;
import org.example.Repository.OperationData;

import java.time.LocalDate;

public class BeginTransaction implements TransactionsType {

    @Override
    public boolean replenishment(Operation operation, double quantity) {
        Transaction transaction = new Transaction(GenerateID.generateID(), operation, quantity, LocalDate.now());
        if (new OperationData().addNewOperationForClient(SessionClient.session_client, transaction)) {
            ClientsData.updateClientBalanceById(SessionClient.session_client.getId(), quantity, operation);
            return true;
        }
        return false;
    }

    @Override
    public boolean withdrawal(Operation operation, double quantity) {
        Transaction transaction = new Transaction(GenerateID.generateID(), operation, quantity, LocalDate.now());
        if (CheckForIssues.enoughMoney(quantity)) {
            if (new OperationData().addNewOperationForClient(SessionClient.session_client, transaction)) {
                ClientsData.updateClientBalanceById(SessionClient.session_client.getId(), quantity, operation);
                return true;
            } else return false;
        } else return false;
    }

    @Override
    public boolean creditAdd(Operation operation, double quantity) {
        Transaction transaction = new Transaction(GenerateID.generateID(), operation, quantity, LocalDate.now());
        if (new OperationData().addNewOperationForClient(SessionClient.session_client, transaction)) {
            ClientsData.updateClientBalanceById(SessionClient.session_client.getId(), quantity, operation);
            return true;
        }
        return false;
    }
}
