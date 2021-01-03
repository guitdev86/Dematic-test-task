package com.dematic.testassignment.model;

import java.util.UUID;

public class ScienceJournal extends Book {

    int scienceIndex;

    public ScienceJournal(String title, String author, UUID barcode, int quantity, double pricePerUnit, int scienceIndex) {
        super(title, author, barcode, quantity, pricePerUnit);
        this.scienceIndex = scienceIndex;
    }

    public int getScienceIndex() {
        return scienceIndex;
    }

    public void setScienceIndex(int scienceIndex) {
        this.scienceIndex = scienceIndex;
    }

    @Override
    public double calculateTotalPrice() {
        return super.getQuantity() * super.getPricePerUnit() * this.scienceIndex;
    }
}
