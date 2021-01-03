package com.dematic.testassignment.model;

import java.util.UUID;

public class Book {
    private String title;
    private String author;
    final UUID barcode;
    private int quantity;
    private double pricePerUnit;

    public Book(String title, String author, UUID barcode, int quantity, double pricePerUnit) {
        this.title = title;
        this.author = author;
        this.barcode = barcode;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public UUID getBarcode() {
        return barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double calculateTotalPrice() {
        return this.quantity * this.pricePerUnit;
    }
}
