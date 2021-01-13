# Overview:
The book storage system is based on controller-service-repository architecture. Controller section is responsible for all basic CRUD operations and special endpoint mentioned below. Repository section is responsible for saving/retrieving and managing records. Files contain information in JSON format. Service layer is left for possible extension. There is a main class of Book in the system. Antique Books and Science Journals are child classes of Book class. There are 3 types of books in the system: Regular books, Science Journals, Ancient books:

* Regular books have **"title", "author", "quantity", "pricePerUnit"** fields.

* Ancient books have **"title", "author", "quantity", "pricePerUnit", "releaseYear"** fields

* Ancient books have **"title", "author", "quantity", "pricePerUnit", "scienceIndex"** fields

All the books "barcodes" are **UUID type**. All barcodes are have to be unique.
# Endpoints:
* **GET /books** endpoint is responsible for getting all the books (including antique and science journals) in a single list.
* **GET /books/{barcode}** endpoint returns a particular book based on the book "barcode" in UUID format
* **GET /books/{barcode}/total-price** endpoint gets the selected book total price.
* **POST /books** endpoint is used to save a single book to a file. Using this endpoint require to have a request JSON body with all information about book title, author, barcode, quantity, pricePerUnit. Duplicate barcode books are declined. **Request body must be an array with a JSON object inside.** Doesn't accept antique books or science journals.
* **POST /books/antique** endpoint is used to save a single antique book to a file. Using this endpoint require to have a request JSON body with all information about book title, author, barcode, quantity, pricePerUnit, releaseYear. Duplicate barcode books are declined. **Request body must be an array with a JSON object inside.** Doesn't accept regular books or science journals.
* **POST /books/science** endpoint is used to save a single science journal to a file. Using this endpoint require to have a request JSON body with all information about book title, author, barcode, quantity, pricePerUnit, scienceIndex. Duplicate barcode books are declined. **Request body must be an array with a JSON object inside.** Doesn't accept antique books or regular books.
* **PUT /books/{barcode}** endpoint can update single or multiple book fields using query request. For example can update a book quantity and price per unit in single request by sending a put request to **/books/{barcode}?quantity=integerQuantity&pricePerUnit=doublePricePerUnit**
* **DELETE /books/{barcode}** endpoint is used to delete a book from the file.