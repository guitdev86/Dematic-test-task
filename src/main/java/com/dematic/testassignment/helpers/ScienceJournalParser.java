package com.dematic.testassignment.helpers;


import com.dematic.testassignment.model.ScienceJournal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class ScienceJournalParser {
    public ScienceJournalParser() {}

    public ScienceJournal parseBookObject(JSONObject book) {
        String title = (String) book.get("title");
        String author = (String) book.get("author");
        UUID barcode = UUID.fromString((String) book.get("barcode"));
        Long quantity = (Long) book.get("quantity");
        Double pricePerUnit = (Double) book.get("pricePerUnit");
        Long scienceIndex = (Long) book.get("scienceIndex");

        return new ScienceJournal(title, author, barcode, quantity.intValue(), pricePerUnit, scienceIndex.intValue());
    }

    public JSONArray parseArrayListToJSONArray(ArrayList<ScienceJournal> books) {
        JSONArray bookStorageJson = new JSONArray();
        books.forEach(book -> {
            JSONObject bookDetails = new JSONObject();
            bookDetails.put("title", book.getTitle());
            bookDetails.put("author", book.getAuthor());
            bookDetails.put("barcode", book.getBarcode().toString());
            bookDetails.put("quantity", book.getQuantity());
            bookDetails.put("pricePerUnit", book.getPricePerUnit());
            bookDetails.put("scienceIndex", book.getScienceIndex());

            bookStorageJson.add(bookDetails);
        });
        return bookStorageJson;
    }
}
