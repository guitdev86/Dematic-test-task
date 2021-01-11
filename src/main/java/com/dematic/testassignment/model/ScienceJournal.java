package com.dematic.testassignment.model;

import java.lang.annotation.Native;
import java.util.UUID;

public class ScienceJournal extends BookModel {

    int scienceIndex;

    public static final int INDEX_MIN_VALUE = 0x0001;
    public static final int INDEX_MAX_VALUE = 0x000A;

    public ScienceJournal(String title, String author, UUID barcode, int quantity, double pricePerUnit, int scienceIndex) {
        this.title = title;
        this.author = author;
        this.barcode = barcode;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.scienceIndex = scienceIndex;
    }

    public int getScienceIndex() {
        return scienceIndex;
    }

    public void setScienceIndex(int scienceIndex) {
        this.scienceIndex = scienceIndex;
    }

    public double calculateTotalPrice() {
        return this.getQuantity() * this.getPricePerUnit() * this.scienceIndex;
    }
}
