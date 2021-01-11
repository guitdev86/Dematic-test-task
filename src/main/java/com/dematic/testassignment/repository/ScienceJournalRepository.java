package com.dematic.testassignment.repository;

import com.dematic.testassignment.helpers.FileIO;
import com.dematic.testassignment.helpers.ScienceJournalParser;
import com.dematic.testassignment.model.AntiqueBook;
import com.dematic.testassignment.model.ScienceJournal;
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
public class ScienceJournalRepository {

    private ArrayList<ScienceJournal> bookStorage;
    private final String path;
    private final FileIO fileIO;
    private final ScienceJournalParser scienceJournalParser;

    ScienceJournalRepository() {
        path = "src\\main\\resources\\scienceJournals.json";
        this.fileIO = new FileIO();
        this.scienceJournalParser = new ScienceJournalParser();

        updateStateFromFile(path);
    }

    private void updateStateFromFile(String path) {
        bookStorage = new ArrayList<>();
        JSONArray jsonArray = fileIO.readFile(path);
        if(!jsonArray.isEmpty())
            jsonArray.forEach(book -> bookStorage.add(scienceJournalParser.parseBookObject((JSONObject) book)));
    }

    private int checkIfBookIsOnTheList(ScienceJournal book) {
        return (int) bookStorage.stream()
                .filter(b -> b.getBarcode().equals(book.getBarcode())).count();
    }

    public ArrayList<ScienceJournal> getBooks() {
        updateStateFromFile(path);
        return this.bookStorage;
    }

    public Optional<List<ScienceJournal>> getBookById(String id) {
        updateStateFromFile(path);
        UUID barcode = UUID.fromString(id);
        return Optional.of(bookStorage.stream()
                .filter(book -> book.getBarcode().equals(barcode))
                .collect(Collectors.toList()));
    }

    public void updateBookById(ScienceJournal book) {
        UUID barcode = book.getBarcode();
        bookStorage = (ArrayList<ScienceJournal>) bookStorage.stream()
                .filter(b -> !b.getBarcode().equals(barcode))
                .collect(Collectors.toList());
        bookStorage.add(book);
        fileIO.writeToFile(scienceJournalParser.parseArrayListToJSONArray(bookStorage),
                this.path);
    }

    public ArrayList<ScienceJournal> saveNewBook(String newBook) throws ParseException {
        JSONParser jsonParser = new JSONParser();

        JSONArray newBookArray = (JSONArray) jsonParser.parse(newBook);
        JSONObject newBookObj = (JSONObject) newBookArray.get(0);
        ScienceJournal newBookInstance = scienceJournalParser.parseBookObject(newBookObj);

        if(checkIfBookIsOnTheList(newBookInstance) == 0 &&
                (newBookInstance.getScienceIndex() > ScienceJournal.INDEX_MIN_VALUE &&
                        newBookInstance.getScienceIndex() < ScienceJournal.INDEX_MAX_VALUE)) {
            bookStorage.add(newBookInstance);
            fileIO.writeToFile(scienceJournalParser.parseArrayListToJSONArray(bookStorage),
                    this.path);
            ArrayList<ScienceJournal> listToReturn = new ArrayList<>();
            listToReturn.add(newBookInstance);
            return listToReturn;
        } else {
            return new ArrayList<>();
        }
    }

    public void deleteBookById(String id) {
        bookStorage = (ArrayList<ScienceJournal>) bookStorage.stream()
                .filter(b -> !b.getBarcode().equals(UUID.fromString(id)))
                .collect(Collectors.toList());
        fileIO.writeToFile(scienceJournalParser.parseArrayListToJSONArray(bookStorage),
                this.path);
    }
}
