package com.dematic.testassignment.helpers;

import com.dematic.testassignment.model.AntiqueBook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class AntiqueBookParser {
    public AntiqueBookParser() {}

    public AntiqueBook parseBookObject(JSONObject book) {
        String title = (String) book.get("title");
        String author = (String) book.get("author");
        UUID barcode = UUID.fromString((String) book.get("barcode"));
        Long quantity = (Long) book.get("quantity");
        Double pricePerUnit = (Double) book.get("pricePerUnit");
        Long releaseYear = (Long) book.get("releaseYear");

        return new AntiqueBook(title, author, barcode, quantity.intValue(), pricePerUnit, releaseYear.intValue());
    }

    public JSONArray parseArrayListToJSONArray(ArrayList<AntiqueBook> books) {
        JSONArray bookStorageJson = new JSONArray();
        books.forEach(book -> {
            JSONObject bookDetails = new JSONObject();
            bookDetails.put("title", book.getTitle());
            bookDetails.put("author", book.getAuthor());
            bookDetails.put("barcode", book.getBarcode().toString());
            bookDetails.put("quantity", book.getQuantity());
            bookDetails.put("pricePerUnit", book.getPricePerUnit());
            bookDetails.put("releaseYear", book.getReleaseYear());

            bookStorageJson.add(bookDetails);
        });
        return bookStorageJson;
    }
}
