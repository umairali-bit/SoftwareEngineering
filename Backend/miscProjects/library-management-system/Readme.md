# 📚 Library Management System

A beginner-friendly Spring Boot project to manage **authors** and **books** using a REST API. It demonstrates best practices in DTO mapping, validation, exception handling, and database interaction with JPA & PostgreSQL.

---

## 🚀 Features

* Manage **Authors**

  * Create, retrieve, update, and delete authors
  * Validation on author names (custom + built-in)
  * List all books by a specific author

* Manage **Books**

  * Create, retrieve, update, and delete books
  * Associate books with authors (bidirectional relationship)
  * Query books by title or publication date

* Validation & Exception Handling

  * Global exception handler with consistent JSON responses
  * Built-in annotations (`@NotBlank`, `@Size`, `@PastOrPresent`)
  * Custom validation annotation: `@ValidateAuthorName`

* Custom Annotations

  * `@AdminOnly` (for admin-restricted endpoints)

* API Responses

  * All responses wrapped in a standard `ApiResponse` object
  * Errors represented as `ApiError` with optional subErrors

---

## 🏗️ Project Structure

```
LibraryManagementSystem/
 ├── advices/         # Global exception & response handlers
 ├── annotations/     # Custom annotations & validators
 ├── controller/      # REST controllers
 ├── dtos/            # Data Transfer Objects
 ├── entities/        # JPA entities (Author, Book)
 ├── exception/       # Custom exceptions (AuthorNotFound, etc.)
 ├── repositories/    # Spring Data JPA repositories
 ├── services/        # Business logic & mappers
 └── LibraryManagementSystemApplication.java
```

---

## ⚙️ Technologies

* **Java 17+**
* **Spring Boot 3+**
* **Spring Data JPA**
* **PostgreSQL**
* **Hibernate Validator**
* **Lombok** (to reduce boilerplate)

---

## 🗄️ Database Setup

PostgreSQL configuration (`application.properties`):

```properties
spring.application.name=LibraryManagementSystem
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/libraryManagementSystem
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
```

---

## 📡 Example API Endpoints

### Authors

* `POST /api/authors` → create new author
* `GET /api/authors/{id}` → get author by ID
* `GET /api/authors/by-name/{name}` → get author by name (ignore case)
* `GET /api/authors/{id}/books` → list books by author
* `DELETE /api/authors/{id}` → delete author

### Books

* `POST /api/books` → create new book (requires existing author)
* `GET /api/books/{id}` → get book by ID
* `GET /api/books/by-title/{title}` → get book by title
* `GET /api/books/published-after?date=YYYY-MM-DD` → get books published after a given date
* `DELETE /api/books/{id}` → delete book

---

## 🔎 Example JSON Requests

### Create Author

```json
{
  "name": "J.K. Rowling",
  "books": []
}
```

### Create Book

```json
{
  "title": "Harry Potter and the Philosopher's Stone",
  "publishedDate": "1997-06-26",
  "author": {
    "id": 1
  }
}
```

---

## 🛡️ Validation Examples

* Author name must be at least 3 characters and only letters, dots, spaces, hyphens allowed (`@ValidateAuthorName`).
* Book title must be at least 3 characters (`@Size(min=3)`).
* Book published date must not be in the future (`@PastOrPresent`).

---

## ❗ Custom Exceptions

1. **AuthorNotFoundException** -> Thrown when an author is not found by ID
2. **AuthorNotFoundNameException** -> Thrown when an author is not found by Name
3. **BookNotFoundException** -> Thrown when an book is not found by ID

    These exceptions are handled centrally and mapped to proper HTTP status codes.
---

## 🧪 Testing

### Testing Strategy

#### AuthorController Tests

- Create author → **201 CREATED**
- Get author by ID → **includes associated books**
- Get author by name → **case-insensitive**
- Update author:
    - `books = null` → **update name only**
    - `books provided` → **replace book associations**
- Delete author:
    - **Author is removed**
    - **Books are unlinked** (author becomes `null`)

---

#### BookController Tests

- Create book with **valid author**
- Validate **title** and **published date** constraints
- Fetch all books and test **filter queries**
- Update and delete book flows

---

### Tools Used

- `@SpringBootTest`
- `WebTestClient`
- **PostgreSQL Testcontainers**
- Repository cleanup in `@BeforeEach`

---

### Run Tests

```bash
 ./mvnw test
```
---


## ✅ Recommendations for Beginners

1. **Use Mapper classes** → Keep DTO ↔ Entity conversion outside services.
2. **Apply validation at DTO/controller level** → Don’t validate inside services.
3. **Use helper methods in entities** → `addBook()`, `removeBook()` to manage bidirectional relations.
4. **Consistent naming** → singular for single results (`getBookById`), plural for lists (`getAllBooks`).
5. **Experiment with Spring Security** → make `@AdminOnly` actually check user roles.
6. **Write tests** → start with service and controller tests using JUnit & MockMvc.

---

## 🚀 Next Steps to Improve

* Add Spring Security with JWT or roles.
* Add pagination to `getAllBooks` and `getAllAuthors`.
* Use **MapStruct** to replace manual mappers.
* Add integration tests with Testcontainers (PostgreSQL).

---

## 👨‍💻 Author

A beginner-friendly project built while learning **Java + Spring Boot + PostgreSQL**.
