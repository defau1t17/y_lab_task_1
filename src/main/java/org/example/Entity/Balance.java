package org.example.Entity;

import java.util.ArrayList;

public class Balance {

    private double value = 0;


    public double getValue() {
        return this.value;
    }


    public void setValue(double value) {
        this.value = value;
    }

    public void replenishment(double quantity) {
        this.value += quantity;
    }

    public void withdrawal(double quantity) {
        this.value -= quantity;
    }


}


