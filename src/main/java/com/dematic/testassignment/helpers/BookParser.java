package com.dematic.testassignment.helpers;

import com.dematic.testassignment.model.Book;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class BookParser {

    public BookParser() {}

    public Book parseBookObject(JSONObject book) {
        String title = (String) book.get("title");
        String author = (String) book.get("author");
        UUID barcode = UUID.fromString((String) book.get("barcode"));
        Long quantity = (Long) book.get("quantity");
        Double pricePerUnit = (Double) book.get("pricePerUnit");

        return new Book(title, author, barcode, quantity.intValue(), pricePerUnit);
    }

    public JSONArray parseArrayListToJSONArray(ArrayList<Book> books) {
        JSONArray bookStorageJson = new JSONArray();
        books.forEach(book -> {
            JSONObject bookDetails = new JSONObject();
            bookDetails.put("title", book.getTitle());
            bookDetails.put("author", book.getAuthor());
            bookDetails.put("barcode", book.getBarcode().toString());
            bookDetails.put("quantity", book.getQuantity());
            bookDetails.put("pricePerUnit", book.getPricePerUnit());

            bookStorageJson.add(bookDetails);
        });
        return bookStorageJson;
    }
}
