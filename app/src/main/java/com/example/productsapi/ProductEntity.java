package com.example.productsapi;

import org.jetbrains.annotations.NotNull;

public class ProductEntity {
    public int id;
    public static String name;
    public double value, quantity;

    @Override
    public @NotNull String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", value=" + value +
                ", quantity=" + quantity +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        ProductEntity.name = name;
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
