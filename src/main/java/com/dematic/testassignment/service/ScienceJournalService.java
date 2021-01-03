package com.dematic.testassignment.service;

import com.dematic.testassignment.model.ScienceJournal;
import com.dematic.testassignment.repository.ScienceJournalRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScienceJournalService {
    @Autowired
    ScienceJournalRepository scienceJournalRepository;

    public ArrayList<ScienceJournal> getBooks() {
        return scienceJournalRepository.getBooks();
    }

    public Optional<List<ScienceJournal>> getBookById(String id) {
        return scienceJournalRepository.getBookById(id);
    }

    public ArrayList<ScienceJournal> saveNewBook(String book) throws ParseException {
        return scienceJournalRepository.saveNewBook(book);
    }

    public void updateBookById(ScienceJournal book) {
        scienceJournalRepository.updateBookById(book);
    }

    public void deleteBookById(String id) {
        scienceJournalRepository.deleteBookById(id);
    }
}
