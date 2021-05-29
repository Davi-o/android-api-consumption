package com.example.productsapi;

import org.jetbrains.annotations.NotNull;

public class ProductEntity {
    private int id;
    private String name;
    private double value, quantity;

    @Override
    public @NotNull String toString() {
        return "id=" + id +
                ", name=" + name +
                ", value=" + value +
                ", quantity=" + quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
