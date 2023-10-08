package org.example.Service;

import org.example.Entity.Operation;

public interface TransactionsType {

    boolean replenishment(Operation operation, double quantity);

    boolean withdrawal(Operation operation, double quantity);

    boolean creditAdd(Operation operation, double quantity);

}
