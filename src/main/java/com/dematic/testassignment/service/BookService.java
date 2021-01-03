package com.dematic.testassignment.service;

import com.dematic.testassignment.model.Book;
import com.dematic.testassignment.repository.BookRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public ArrayList<Book> getBooks() {
        return bookRepository.getBooks();
    }

    public Optional<List<Book>> getBookById(String id) {
        return bookRepository.getBookById(id);
    }

    public ArrayList<Book> saveNewBook(String book) throws ParseException {
        return bookRepository.saveNewBook(book);
    }

    public void updateBookById(Book book) {
        bookRepository.updateBookById(book);
    }

    public void deleteBookById(String id) {
        bookRepository.deleteBookById(id);
    }
}
