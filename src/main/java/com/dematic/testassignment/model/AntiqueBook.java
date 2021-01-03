package com.dematic.testassignment.model;

import java.util.Calendar;
import java.util.UUID;

public class AntiqueBook extends Book {

    private int releaseYear;

    public AntiqueBook(String title, String author, UUID barcode, int quantity, double pricePerUnit, int releaseYear) {
        super(title, author, barcode, quantity, pricePerUnit);
        this.releaseYear = releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    @Override
    public double calculateTotalPrice() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return super.getQuantity() * super.getPricePerUnit() * (currentYear - this.releaseYear) / 10;
    }
}
