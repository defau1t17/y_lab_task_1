package org.example.application_entity.Entity.Transaction;


import java.time.LocalDate;

public class Transaction {

    private String id;

    private Operation operation_type;

    private double magnitude;

    private LocalDate localDate;

    public Transaction(String id, Operation operation, double magnitude, LocalDate localDate) {
        this.id = id;
        this.operation_type = operation;
        this.magnitude = magnitude;
        this.localDate = localDate;
    }

    public String getId() {
        return id;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public Operation getOperation_type() {
        return operation_type;
    }
}
