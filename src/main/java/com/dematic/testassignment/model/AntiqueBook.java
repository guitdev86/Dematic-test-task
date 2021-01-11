package com.dematic.testassignment.model;

import java.util.Calendar;
import java.util.UUID;

public class AntiqueBook extends BookModel {

    public static final int AGE_MAX_VALUE = 0x076C;
    private int releaseYear;

    public AntiqueBook(String title, String author, UUID barcode, int quantity, double pricePerUnit, int releaseYear) {
        this.title = title;
        this.author = author;
        this.barcode = barcode;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.releaseYear = releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double calculateTotalPrice() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return this.getQuantity() * this.getPricePerUnit() * (currentYear - this.releaseYear) / 10;
    }
}
