package org.example.application_logic.Service.ClientServices;

import org.example.application_entity.Entity.Transaction.Operation;

public interface TransactionsType {

    boolean replenishment(Operation operation, double quantity);

    boolean withdrawal(Operation operation, double quantity);

    boolean creditAdd(Operation operation, double quantity);

}
