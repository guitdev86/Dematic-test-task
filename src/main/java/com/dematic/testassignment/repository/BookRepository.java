package com.dematic.testassignment.repository;
import com.dematic.testassignment.helpers.BookParser;
import com.dematic.testassignment.helpers.FileIO;
import com.dematic.testassignment.model.Book;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BookRepository{

    private ArrayList<Book> bookStorage;
    private final String path;
    private final FileIO fileIO;
    private final BookParser bookParser;

    BookRepository() {
        this.path = "src\\main\\resources\\books.json";
        this.fileIO = new FileIO();
        this.bookParser = new BookParser();

        updateStateFromFile(path);
    }

    private void updateStateFromFile(String path) {
        bookStorage = new ArrayList<>();
        JSONArray jsonArray = fileIO.readFile(path);
        jsonArray.forEach(book -> bookStorage.add(bookParser.parseBookObject((JSONObject) book)));
    }

    private int checkIfBookIsOnTheList(Book book) {
        return (int) bookStorage.stream()
                .filter(b -> b.getBarcode().equals(book.getBarcode())).count();
    }

    public ArrayList<Book> getBooks() {
        updateStateFromFile(path);
        return this.bookStorage;
    }

    public Optional<List<Book>> getBookById(String id) {
            updateStateFromFile(path);
            UUID barcode = UUID.fromString(id);
            return Optional.of(bookStorage.stream()
                    .filter(book -> book.getBarcode().equals(barcode))
                    .collect(Collectors.toList()));
    }

    public void updateBookById(Book book) {
        UUID barcode = book.getBarcode();
        bookStorage = (ArrayList<Book>) bookStorage.stream()
                .filter(b -> !b.getBarcode().equals(barcode))
                .collect(Collectors.toList());
        bookStorage.add(book);
        fileIO.writeToFile(bookParser.parseArrayListToJSONArray(bookStorage),
                this.path);
    }

    public ArrayList<Book> saveNewBook(String newBook) throws ParseException {
        JSONParser jsonParser = new JSONParser();

        JSONArray newBookArray = (JSONArray) jsonParser.parse(newBook);
        JSONObject newBookObj = (JSONObject) newBookArray.get(0);
        Book newBookInstance = bookParser.parseBookObject(newBookObj);

        if(checkIfBookIsOnTheList(newBookInstance) == 0) {
            bookStorage.add(newBookInstance);
            fileIO.writeToFile(bookParser.parseArrayListToJSONArray(bookStorage),
                    this.path);
            ArrayList<Book> listToReturn = new ArrayList<>();
            listToReturn.add(newBookInstance);
            return listToReturn;
        } else {
            return new ArrayList<>();
        }
    }

    public void deleteBookById(String id) {
        bookStorage = (ArrayList<Book>) bookStorage.stream()
                .filter(b -> !b.getBarcode().equals(UUID.fromString(id)))
                .collect(Collectors.toList());
        fileIO.writeToFile(bookParser.parseArrayListToJSONArray(bookStorage),
                this.path);
    }

}
