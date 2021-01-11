package com.dematic.testassignment.repository;

import com.dematic.testassignment.helpers.AntiqueBookParser;
import com.dematic.testassignment.helpers.FileIO;
import com.dematic.testassignment.model.AntiqueBook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class AntiqueBookRepository{

    private ArrayList<AntiqueBook> bookStorage;
    private final String path;
    private final FileIO fileIO;
    private final AntiqueBookParser antiqueBookParser;

    AntiqueBookRepository() {
        path = "src\\main\\resources\\antiqueBooks.json";
        this.fileIO = new FileIO();
        this.antiqueBookParser = new AntiqueBookParser();

        updateStateFromFile(path);
    }

    private void updateStateFromFile(String path) {
        bookStorage = new ArrayList<>();
        JSONArray jsonArray = fileIO.readFile(path);
        if(!jsonArray.isEmpty())
        jsonArray.forEach(book -> bookStorage.add(antiqueBookParser.parseBookObject((JSONObject) book)));
    }

    private int checkIfBookIsOnTheList(AntiqueBook book) {
        return (int) bookStorage.stream()
                .filter(b -> b.getBarcode().equals(book.getBarcode())).count();
    }

    public ArrayList<AntiqueBook> getBooks() {
        updateStateFromFile(path);
        return this.bookStorage;
    }

    public Optional<List<AntiqueBook>> getBookById(String id) {
        updateStateFromFile(path);
        UUID barcode = UUID.fromString(id);
        return Optional.of(bookStorage.stream()
                .filter(book -> book.getBarcode().equals(barcode))
                .collect(Collectors.toList()));
    }

    public void updateBookById(AntiqueBook book) {
        UUID barcode = book.getBarcode();
        bookStorage = (ArrayList<AntiqueBook>) bookStorage.stream()
                .filter(b -> !b.getBarcode().equals(barcode))
                .collect(Collectors.toList());
        bookStorage.add(book);
        fileIO.writeToFile(antiqueBookParser.parseArrayListToJSONArray(bookStorage),
                this.path);
    }

    public ArrayList<AntiqueBook> saveNewBook(String newBook) throws ParseException {
        JSONParser jsonParser = new JSONParser();

        JSONArray newBookArray = (JSONArray) jsonParser.parse(newBook);
        JSONObject newBookObj = (JSONObject) newBookArray.get(0);
        AntiqueBook newBookInstance = antiqueBookParser.parseBookObject(newBookObj);

        if(checkIfBookIsOnTheList(newBookInstance) == 0 && newBookInstance.getReleaseYear() < AntiqueBook.AGE_MAX_VALUE) {
            bookStorage.add(newBookInstance);
            fileIO.writeToFile(antiqueBookParser.parseArrayListToJSONArray(bookStorage),
                    this.path);
            ArrayList<AntiqueBook> listToReturn = new ArrayList<>();
            listToReturn.add(newBookInstance);
            return listToReturn;
        } else {
            return new ArrayList<>();
        }
    }

    public void deleteBookById(String id) {
        bookStorage = (ArrayList<AntiqueBook>) bookStorage.stream()
                .filter(b -> !b.getBarcode().equals(UUID.fromString(id)))
                .collect(Collectors.toList());
        fileIO.writeToFile(antiqueBookParser.parseArrayListToJSONArray(bookStorage),
                this.path);
    }
}
