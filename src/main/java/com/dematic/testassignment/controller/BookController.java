package com.dematic.testassignment.controller;

import com.dematic.testassignment.model.AntiqueBook;
import com.dematic.testassignment.model.Book;
import com.dematic.testassignment.model.ScienceJournal;
import com.dematic.testassignment.service.AntiqueBookService;
import com.dematic.testassignment.service.BookService;
import com.dematic.testassignment.service.ScienceJournalService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    AntiqueBookService antiqueBookService;

    @Autowired
    ScienceJournalService scienceJournalService;

    private List<?> findBook(String id) {
        List<Book> bookList = bookService.getBookById(id).get();
        List<AntiqueBook> antiqueBookList = antiqueBookService.getBookById(id).get();
        List<ScienceJournal> scienceJournalList = scienceJournalService.getBookById(id).get();

        if(!bookList.isEmpty()) {
            return bookList;
        }

        if (!antiqueBookList.isEmpty()) {
            return antiqueBookList;
        }

        if (!scienceJournalList.isEmpty()) {
            return scienceJournalList;
        }

        return new ArrayList<>();
    }

    private String getNewBookId(String id) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(id);
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);

        return (String) jsonObject.get("barcode");
    }

    @GetMapping("/books")
    Map<String, ArrayList<?>> getBooks() {
        Map<String, ArrayList<?>> allBooks = new HashMap<>();
        allBooks.put("regular_books", bookService.getBooks());
        allBooks.put("antique_books", antiqueBookService.getBooks());
        allBooks.put("science_journals", scienceJournalService.getBooks());
        return allBooks;
    }

    @GetMapping("/books/{id}")
    ResponseEntity<List<?>> getBookById(@PathVariable String id) {

        if(!findBook(id).isEmpty()) {
            return new ResponseEntity<>(findBook(id), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/books/{id}/total-price")
    ResponseEntity<List<Map<String, Double>>> getTotalPrice(@PathVariable String id) {

        List<Book> bookList = bookService.getBookById(id).get();
        List<AntiqueBook> antiqueBookList = antiqueBookService.getBookById(id).get();
        List<ScienceJournal> scienceJournalList = scienceJournalService.getBookById(id).get();

        Map<String, Double> totalPriceMap = new HashMap<>();
        List<Map<String, Double>> listTotalPriceMap = new ArrayList<>();

        if(!bookList.isEmpty()) {
            totalPriceMap.put("totalPrice", bookList.get(0).calculateTotalPrice());
            listTotalPriceMap.add(totalPriceMap);
            return new ResponseEntity<>(listTotalPriceMap, HttpStatus.OK);
        } else if (!antiqueBookList.isEmpty()) {
            totalPriceMap.put("totalPrice", antiqueBookList.get(0).calculateTotalPrice());
            listTotalPriceMap.add(totalPriceMap);
            return new ResponseEntity<>(listTotalPriceMap, HttpStatus.OK);
        } else if (!scienceJournalList.isEmpty()){
            totalPriceMap.put("totalPrice", scienceJournalList.get(0).calculateTotalPrice());
            listTotalPriceMap.add(totalPriceMap);
            return new ResponseEntity<>(listTotalPriceMap, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books")
    ResponseEntity<ArrayList<Book>> saveNewBook(@RequestBody String book) throws ParseException {
        String newBookBarcode = getNewBookId(book);
        List<?> existingBook = findBook(newBookBarcode);

        if(book.contains("releaseYear") || book.contains("scienceIndex") || !existingBook.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ArrayList<Book> newBookList = bookService.saveNewBook(book);

        if(newBookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity<>(newBookList, HttpStatus.CREATED);
        }
    }

    @PostMapping("/books/antique")
    ResponseEntity<ArrayList<AntiqueBook>> saveNewAntiqueBook(@RequestBody String book) throws ParseException {
        String newBookBarcode = getNewBookId(book);
        List<?> existingBook = findBook(newBookBarcode);

        if(book.contains("scienceIndex") || !book.contains("releaseYear") || !existingBook.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ArrayList<AntiqueBook> newBookList = antiqueBookService.saveNewBook(book);

        if(newBookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity<>(newBookList, HttpStatus.CREATED);
        }
    }

    @PostMapping("/books/science")
    ResponseEntity<ArrayList<ScienceJournal>> saveNewScienceBook(@RequestBody String book) throws ParseException {
        String newBookBarcode = getNewBookId(book);
        List<?> existingBook = findBook(newBookBarcode);

        if(book.contains("releaseYear") || !book.contains("scienceIndex") || !existingBook.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ArrayList<ScienceJournal> newBookList = scienceJournalService.saveNewBook(book);

        if(newBookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity<>(newBookList, HttpStatus.CREATED);
        }
    }

    @PutMapping("/books/{id}")
    ResponseEntity<ArrayList<?>> updateBook(
            @PathVariable String id,
            @RequestParam Optional<String> title,
            @RequestParam Optional<String> author,
            @RequestParam Optional<Integer> quantity,
            @RequestParam Optional<Double> pricePerUnit,
            @RequestParam Optional<Integer> releaseYear,
            @RequestParam Optional<Integer> scienceIndex) {

        if(!findBook(id).isEmpty()) {
            if(findBook(id).get(0).getClass().equals(Book.class)) {
                Book bookToUpdate = bookService.getBookById(id).get().get(0);

                if(releaseYear.isPresent() || scienceIndex.isPresent()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                title.ifPresent(bookToUpdate::setTitle);
                author.ifPresent(bookToUpdate::setAuthor);
                quantity.ifPresent(bookToUpdate::setQuantity);
                pricePerUnit.ifPresent(bookToUpdate::setPricePerUnit);

                bookService.updateBookById(bookToUpdate);

                ArrayList<Book> bookArray = new ArrayList<>();
                bookArray.add(bookToUpdate);

                return new ResponseEntity<>(bookArray, HttpStatus.OK);

            } else if (findBook(id).get(0).getClass().equals(AntiqueBook.class)) {
                AntiqueBook bookToUpdate = antiqueBookService.getBookById(id).get().get(0);

                if(scienceIndex.isPresent()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                if(releaseYear.isPresent() && releaseYear.get() > 1900) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                title.ifPresent(bookToUpdate::setTitle);
                author.ifPresent(bookToUpdate::setAuthor);
                quantity.ifPresent(bookToUpdate::setQuantity);
                pricePerUnit.ifPresent(bookToUpdate::setPricePerUnit);
                releaseYear.ifPresent(bookToUpdate::setReleaseYear);

                antiqueBookService.updateBookById(bookToUpdate);

                ArrayList<AntiqueBook> bookArray = new ArrayList<>();
                bookArray.add(bookToUpdate);

                return new ResponseEntity<>(bookArray, HttpStatus.OK);

            } else if (findBook(id).get(0).getClass().equals(ScienceJournal.class)) {
                ScienceJournal bookToUpdate = scienceJournalService.getBookById(id).get().get(0);

                if(releaseYear.isPresent()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                if(scienceIndex.isPresent() && (scienceIndex.get() > 10 || scienceIndex.get() < 1)) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                title.ifPresent(bookToUpdate::setTitle);
                author.ifPresent(bookToUpdate::setAuthor);
                quantity.ifPresent(bookToUpdate::setQuantity);
                pricePerUnit.ifPresent(bookToUpdate::setPricePerUnit);
                scienceIndex.ifPresent(bookToUpdate::setScienceIndex);

                scienceJournalService.updateBookById(bookToUpdate);

                ArrayList<ScienceJournal> bookArray = new ArrayList<>();
                bookArray.add(bookToUpdate);

                return new ResponseEntity<>(bookArray, HttpStatus.OK);
            }


        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("books/{id}")
    ResponseEntity<Book> deleteBook(@PathVariable String id) {

        if(!findBook(id).isEmpty()) {
            if(findBook(id).get(0).getClass().equals(Book.class)) {
                bookService.deleteBookById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            if(findBook(id).get(0).getClass().equals(AntiqueBook.class)) {
                antiqueBookService.deleteBookById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            if(findBook(id).get(0).getClass().equals(ScienceJournal.class)) {
                scienceJournalService.deleteBookById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
