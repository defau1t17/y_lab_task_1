package org.example.application_logic.Service.ClientServices;

import org.example.application_logic.Handler.CheckForIssues;
import org.example.application_logic.Repository.ClientsDataRepository;
import org.example.application_logic.Repository.OperationData;
import org.example.application_logic.Service.GenerateID;
import org.example.application_entity.Transaction.Operation;
import org.example.application_entity.Client.SessionClient;
import org.example.application_entity.Transaction.Transaction;

import java.time.LocalDate;

public class BeginTransaction implements TransactionsType {


    /**
     * Функция предназначена для начала транзакции пополнения
     *
     * @param operation тип операции
     * @param quantity  значение
     * @return вернет true если транзакция была успешно проведена, иначе вернет false
     */
    @Override
    public boolean replenishment(Operation operation, double quantity) {
        Transaction transaction = new Transaction(GenerateID.generateID(), operation, quantity, LocalDate.now());
        if (new OperationData().addNewOperationForClient(SessionClient.session_client, transaction)) {
            ClientsDataRepository.updateClientBalanceById(SessionClient.session_client.getId(), quantity, operation);
            return true;
        }
        return false;
    }

    /**
     * Функция предназначена для начала транзакции дебета
     *
     * @param operation тип операции
     * @param quantity  значение
     * @return вернет true если транзакция была успешно проведена, иначе вернет false
     */
    @Override
    public boolean withdrawal(Operation operation, double quantity) {
        Transaction transaction = new Transaction(GenerateID.generateID(), operation, quantity, LocalDate.now());
        if (CheckForIssues.enoughMoney(quantity)) {
            if (new OperationData().addNewOperationForClient(SessionClient.session_client, transaction)) {
                ClientsDataRepository.updateClientBalanceById(SessionClient.session_client.getId(), quantity, operation);
                return true;
            } else return false;
        } else return false;
    }

    /**
     * Функция предназначена для начала транзакции кредитного пополнения
     *
     * @param operation тип операции
     * @param quantity  значение
     * @return вернет true если транзакция была успешно проведена, иначе вернет false
     */
    @Override
    public boolean creditAdd(Operation operation, double quantity) {
        Transaction transaction = new Transaction(GenerateID.generateID(), operation, quantity, LocalDate.now());
        if (new OperationData().addNewOperationForClient(SessionClient.session_client, transaction)) {
            ClientsDataRepository.updateClientBalanceById(SessionClient.session_client.getId(), quantity, operation);
            return true;
        }
        return false;
    }
}
