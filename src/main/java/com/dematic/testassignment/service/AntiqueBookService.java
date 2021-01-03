package com.dematic.testassignment.service;

import com.dematic.testassignment.model.AntiqueBook;
import com.dematic.testassignment.repository.AntiqueBookRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AntiqueBookService {

    @Autowired
    AntiqueBookRepository antiqueBookRepository;

    public ArrayList<AntiqueBook> getBooks() {
        return antiqueBookRepository.getBooks();
    }

    public Optional<List<AntiqueBook>> getBookById(String id) {
        return antiqueBookRepository.getBookById(id);
    }

    public ArrayList<AntiqueBook> saveNewBook(String book) throws ParseException {
        return antiqueBookRepository.saveNewBook(book);
    }

    public void updateBookById(AntiqueBook book) {
        antiqueBookRepository.updateBookById(book);
    }

    public void deleteBookById(String id) {
        antiqueBookRepository.deleteBookById(id);
    }
}
