package com.dematic.testassignment.model;

import java.util.UUID;

public class Book extends BookModel{

    public Book(String title, String author, UUID barcode, int quantity, double pricePerUnit) {
        this.title = title;
        this.author = author;
        this.barcode = barcode;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public double calculateTotalPrice() {
        return this.quantity * this.pricePerUnit;
    }
}
