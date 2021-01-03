package com.dematic.testassignment;

import static org.assertj.core.api.Assertions.assertThat;

import com.dematic.testassignment.helpers.BookParser;
import com.dematic.testassignment.helpers.FileIO;
import com.dematic.testassignment.model.AntiqueBook;
import com.dematic.testassignment.model.Book;
import com.dematic.testassignment.model.ScienceJournal;
import com.dematic.testassignment.repository.AntiqueBookRepository;
import com.dematic.testassignment.repository.BookRepository;
import com.dematic.testassignment.repository.ScienceJournalRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class BookRepositoryMethodTests {

	@Autowired
	BookRepository bookRepository;
	ArrayList<Book> books;


	@Test
	void getBooksShouldReturnArrayOfBooks() {
		books = bookRepository.getBooks();
		assertThat(books.isEmpty()).isFalse();
		books.forEach(book -> assertThat(book).isInstanceOf(Book.class));
	}

	@Test
	void getBooksByIdShouldReturnArrayWithBook() {
		books = bookRepository.getBooks();
		UUID barcode = books.get(0).getBarcode();

		Optional<List<Book>> book = bookRepository.getBookById(barcode.toString());

		assertThat(book.isPresent()).isTrue();
		book.ifPresent(bookList -> assertThat(bookList.isEmpty()).isFalse());
	}

	@Test
	void deleteBookById() {
		String testBookId = "63cffbfe-4aae-11eb-b378-0242ac130002";

		Optional<List<Book>> expectedBook = bookRepository.getBookById(testBookId);
		expectedBook.ifPresent(bookList -> assertThat(bookList.isEmpty()).isFalse());

		bookRepository.deleteBookById(testBookId);

		Optional<List<Book>> deletedBook = bookRepository.getBookById(testBookId);
		deletedBook.ifPresent(bookList -> assertThat(bookList.isEmpty()).isTrue());
	}

	@Test
	void saveNewBookShouldSaveABookToFile() throws ParseException {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String testBookId = "63cffbfe-4aae-11eb-b378-0242ac130002";

		jsonObject.put("title", "Java");
		jsonObject.put("author", "Gosling");
		jsonObject.put("barcode", testBookId);
		jsonObject.put("quantity", 2L);
		jsonObject.put("pricePerUnit", 2.5);

		jsonArray.add(jsonObject);

		bookRepository.saveNewBook(jsonArray.toJSONString());

		Optional<List<Book>> expectedBook = bookRepository.getBookById(testBookId);

		assertThat(expectedBook.isPresent()).isTrue();
		assertThat(expectedBook.get().isEmpty()).isFalse();

	}

	@Test
	void updateBookByIdMethodShouldUpdateSelectedRecord() {
		String testBookId = "63cffbfe-4aae-11eb-b378-0242ac130002";

		Optional<List<Book>> referenceBookList = bookRepository.getBookById(testBookId);

		Book expectedBook;
		String expectedTitle = "Test Book";

		if(referenceBookList.isPresent()) {
			if(!referenceBookList.get().isEmpty()) {
				expectedBook = referenceBookList.get().get(0);

				expectedBook.setTitle(expectedTitle);
				bookRepository.updateBookById(expectedBook);
				Book actualBook = bookRepository.getBookById(testBookId).get().get(0);

				assertThat(actualBook.getTitle().equals(expectedTitle)).isTrue();
			}
		}
	}

	@Test
	void savingSameBookShouldBeDeclined() throws ParseException {
		String testBookId = "63cffbfe-4aae-11eb-b378-0242ac130002";

		Optional<List<Book>> referenceBookList = bookRepository.getBookById(testBookId);

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		if(referenceBookList.isPresent()) {
			if(!referenceBookList.get().isEmpty()) {
				Book referenceBook = referenceBookList.get().get(0);

				jsonObject.put("title", referenceBook.getTitle());
				jsonObject.put("author", referenceBook.getAuthor());
				jsonObject.put("barcode", testBookId);
				jsonObject.put("quantity", referenceBook.getQuantity());
				jsonObject.put("pricePerUnit", referenceBook.getPricePerUnit());

				jsonArray.add(jsonObject);

				ArrayList<Book> actualBookArray = bookRepository.saveNewBook(jsonArray.toJSONString());

				assertThat(actualBookArray.isEmpty()).isTrue();
			}
		}
	}
}

@SpringBootTest
class AntiqueBookRepositoryTests {

	@Autowired
	AntiqueBookRepository bookRepository;
	ArrayList<AntiqueBook> books;


	@Test
	void getBooksShouldReturnArrayOfBooks() {
		books = bookRepository.getBooks();
		assertThat(books.isEmpty()).isFalse();
		books.forEach(book -> assertThat(book).isInstanceOf(AntiqueBook.class));
	}

	@Test
	void getBooksByIdShouldReturnArrayWithBook() {
		books = bookRepository.getBooks();
		UUID barcode = books.get(0).getBarcode();

		Optional<List<AntiqueBook>> book = bookRepository.getBookById(barcode.toString());

		assertThat(book.isPresent()).isTrue();
		book.ifPresent(bookList -> assertThat(bookList.isEmpty()).isFalse());
	}


	@Test
	void saveNewBookShouldSaveABookToFile() throws ParseException {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String testBookId = "d0f5e344-4cfa-11eb-ae93-0242ac130002";

		jsonObject.put("title", "Java");
		jsonObject.put("author", "Gosling");
		jsonObject.put("barcode", testBookId);
		jsonObject.put("quantity", 2L);
		jsonObject.put("pricePerUnit", 2.5);
		jsonObject.put("releaseYear", 1905);

		jsonArray.add(jsonObject);

		bookRepository.saveNewBook(jsonArray.toJSONString());

		Optional<List<AntiqueBook>> expectedBook = bookRepository.getBookById(testBookId);

		assertThat(expectedBook.isPresent()).isTrue();
		assertThat(expectedBook.get().isEmpty()).isFalse();

	}

	@Test
	void updateBookByIdMethodShouldUpdateSelectedRecord() {
		String testBookId = "d0f5e344-4cfa-11eb-ae93-0242ac130002";

		Optional<List<AntiqueBook>> referenceBookList = bookRepository.getBookById(testBookId);

		AntiqueBook expectedBook;
		String expectedTitle = "Test Book";

		expectedBook = referenceBookList.get().get(0);

		expectedBook.setTitle(expectedTitle);
		bookRepository.updateBookById(expectedBook);
		AntiqueBook actualBook = bookRepository.getBookById(testBookId).get().get(0);

		assertThat(actualBook.getTitle().equals(expectedTitle)).isTrue();
	}

	@Test
	void deleteBookById() {
		String testBookId = "d0f5e344-4cfa-11eb-ae93-0242ac130002";

		Optional<List<AntiqueBook>> expectedBook = bookRepository.getBookById(testBookId);
		expectedBook.ifPresent(bookList -> assertThat(bookList.isEmpty()).isFalse());

		bookRepository.deleteBookById(testBookId);

		Optional<List<AntiqueBook>> deletedBook = bookRepository.getBookById(testBookId);
		deletedBook.ifPresent(bookList -> assertThat(bookList.isEmpty()).isTrue());
	}

	@Test
	void savingSameBookShouldBeDeclined() throws ParseException {
		String testBookId = "d0f5e344-4cfa-11eb-ae93-0242ac130002";

		Optional<List<AntiqueBook>> referenceBookList = bookRepository.getBookById(testBookId);

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		if(referenceBookList.isPresent()) {
			if(!referenceBookList.get().isEmpty()) {
				AntiqueBook referenceBook = referenceBookList.get().get(0);

				jsonObject.put("title", referenceBook.getTitle());
				jsonObject.put("author", referenceBook.getAuthor());
				jsonObject.put("barcode", testBookId);
				jsonObject.put("quantity", referenceBook.getQuantity());
				jsonObject.put("pricePerUnit", referenceBook.getPricePerUnit());
				jsonObject.put("releaseYear", referenceBook.getReleaseYear());

				jsonArray.add(jsonObject);

				ArrayList<AntiqueBook> actualBookArray = bookRepository.saveNewBook(jsonArray.toJSONString());

				assertThat(actualBookArray.isEmpty()).isTrue();
			}
		}
	}
}

@SpringBootTest
class ScienceJournalRepositoryTests {

	@Autowired
	ScienceJournalRepository bookRepository;
	ArrayList<ScienceJournal> books;

	@Test
	void getBooksShouldReturnArrayOfBooks() {
		books = bookRepository.getBooks();
		assertThat(books.isEmpty()).isFalse();
		books.forEach(book -> assertThat(book).isInstanceOf(ScienceJournal.class));
	}

	@Test
	void getBooksByIdShouldReturnArrayWithBook() {
		books = bookRepository.getBooks();
		UUID barcode = books.get(0).getBarcode();

		Optional<List<ScienceJournal>> book = bookRepository.getBookById(barcode.toString());

		assertThat(book.isPresent()).isTrue();
		book.ifPresent(bookList -> assertThat(bookList.isEmpty()).isFalse());
	}

	@Test
	void saveNewBookShouldSaveABookToFile() throws ParseException {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String testBookId = "25f03ea2-4d1a-11eb-ae93-0242ac130002";

		jsonObject.put("title", "National Geographic");
		jsonObject.put("author", "Nation");
		jsonObject.put("barcode", testBookId);
		jsonObject.put("quantity", 2L);
		jsonObject.put("pricePerUnit", 2.5);
		jsonObject.put("scienceIndex", 6);

		jsonArray.add(jsonObject);

		bookRepository.saveNewBook(jsonArray.toJSONString());

		Optional<List<ScienceJournal>> expectedBook = bookRepository.getBookById(testBookId);

		assertThat(expectedBook.isPresent()).isTrue();
		assertThat(expectedBook.get().isEmpty()).isFalse();

	}

	@Test
	void updateBookByIdMethodShouldUpdateSelectedRecord() {
		String testBookId = "25f03ea2-4d1a-11eb-ae93-0242ac130002";

		Optional<List<ScienceJournal>> referenceBookList = bookRepository.getBookById(testBookId);

		ScienceJournal expectedBook;
		String expectedTitle = "Test Book";

		expectedBook = referenceBookList.get().get(0);

		expectedBook.setTitle(expectedTitle);
		bookRepository.updateBookById(expectedBook);
		ScienceJournal actualBook = bookRepository.getBookById(testBookId).get().get(0);

		assertThat(actualBook.getTitle().equals(expectedTitle)).isTrue();
	}

	@Test
	void deleteBookById() {
		String testBookId = "25f03ea2-4d1a-11eb-ae93-0242ac130002";

		Optional<List<ScienceJournal>> expectedBook = bookRepository.getBookById(testBookId);
		expectedBook.ifPresent(bookList -> assertThat(bookList.isEmpty()).isFalse());

		bookRepository.deleteBookById(testBookId);

		Optional<List<ScienceJournal>> deletedBook = bookRepository.getBookById(testBookId);
		deletedBook.ifPresent(bookList -> assertThat(bookList.isEmpty()).isTrue());
	}

	@Test
	void savingSameBookShouldBeDeclined() throws ParseException {
		String testBookId = "25f03ea2-4d1a-11eb-ae93-0242ac130002";

		Optional<List<ScienceJournal>> referenceBookList = bookRepository.getBookById(testBookId);

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		ScienceJournal referenceBook = referenceBookList.get().get(0);

		jsonObject.put("author", referenceBook.getAuthor());
		jsonObject.put("title", referenceBook.getTitle());
		jsonObject.put("barcode", testBookId);
		jsonObject.put("quantity", referenceBook.getQuantity());
		jsonObject.put("pricePerUnit", referenceBook.getPricePerUnit());
		jsonObject.put("scienceIndex", referenceBook.getScienceIndex());

		jsonArray.add(jsonObject);

		ArrayList<ScienceJournal> actualBookArray = bookRepository.saveNewBook(jsonArray.toJSONString());

		assertThat(actualBookArray.isEmpty()).isTrue();
	}

}


@SpringBootTest
class FileIOMethodsTests {

	String readTestPath = "src\\main\\resources\\readTest.json";
	String writeTestPath = "src\\main\\resources\\writeTest.json";
	FileIO fileIO = new FileIO();


	@Test
	void readFileMethodShouldReturnCorrectResult() {

		JSONArray jsonArray = fileIO.readFile(readTestPath);
		JSONObject obj = (JSONObject) jsonArray.get(0);
		String test = (String) obj.get("test");

		assertThat(test).isEqualTo("successful");
	}

	@Test
	void writeFileMethodShouldWriteCorrectInformation() {
		UUID randomUUID = UUID.randomUUID();
		String id = randomUUID.toString();

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("id", id);
		jsonArray.add(jsonObject);

		fileIO.writeToFile(jsonArray, writeTestPath);

		JSONArray arrayFromFile = fileIO.readFile(writeTestPath);
		JSONObject resultObject = (JSONObject) arrayFromFile.get(0);
		String resultId = (String) resultObject.get("id");

		assertThat(id).isEqualTo(resultId);
	}
}

@SpringBootTest
class BookParserMethodsTests {

	BookParser bookParser = new BookParser();

	@Test
	void parseBookObjectShouldReturnCorrectBookObject() {
		JSONObject newBook = new JSONObject();
		newBook.put("title", "Java");
		newBook.put("author", "Gosling");
		newBook.put("barcode", UUID.randomUUID().toString());
		newBook.put("quantity", 2L);
		newBook.put("pricePerUnit", 2.5);

		Book book = bookParser.parseBookObject(newBook);

		assertThat(book.getTitle()).isEqualTo("Java");
		assertThat(book.getAuthor()).isEqualTo("Gosling");
		assertThat(book.getBarcode()).isInstanceOf(UUID.class);
		assertThat(book.getQuantity()).isEqualTo(2);
		assertThat(book.getPricePerUnit()).isEqualTo(2.5);
	}

	@Test
	void parseArrayListToJSONArrayReturnCorrectJSONArray() {
		ArrayList<Book> bookArrayList = new ArrayList<>();
		Book newBook = new Book("Java", "Gosling", UUID.randomUUID(), 2, 5);

		bookArrayList.add(newBook);

		JSONArray jsonArray = bookParser.parseArrayListToJSONArray(bookArrayList);

		JSONObject parsedBook = (JSONObject) jsonArray.get(0);
		String title = (String) parsedBook.get("title");
		String author = (String) parsedBook.get("author");
		Integer quantity = (Integer) parsedBook.get("quantity");
		Double pricePerUnit = (Double) parsedBook.get("pricePerUnit");

		assertThat(title).isEqualTo(newBook.getTitle());
		assertThat(author).isEqualTo(newBook.getAuthor());
		assertThat(quantity).isEqualTo(newBook.getQuantity());
		assertThat(pricePerUnit).isEqualTo(newBook.getPricePerUnit());
	}
}