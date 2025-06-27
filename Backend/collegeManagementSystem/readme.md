```markdown
# 🎓 College Management System

A full-stack Java Spring Boot application to manage students, professors, subjects, and admission records within a college environment.

---

## 🚀 Features

- **Student Management**
  - Create, update, delete, and retrieve students
  - View subjects and professors linked to students

- **Professor Management**
  - Assign professors to students and subjects
  - Fetch student lists per professor

- **Subject Management**
  - Assign subjects to students and professors
  - Many-to-many relationship with students

- **Admission Records**
  - One-to-one mapping with Student entity
  - Record fees and timestamp on admission
  - Fee validation with constraints

- **Error Handling & Validation**
  - Centralized exception handling using `@RestControllerAdvice`
  - Consistent API responses wrapped with `ApiResponse`
  - Bean validation with meaningful error messages

---

## 🛠 Tech Stack

| Layer            | Technology                     |
|------------------|--------------------------------|
| Backend          | Spring Boot, Spring Data JPA   |
| ORM              | Hibernate                      |
| Database         | MySQL                          |
| Object Mapping   | ModelMapper                    |
| Validation       | Jakarta Bean Validation (JSR 380) |
| Build Tool       | Maven or Gradle                |

---

## 📁 Project Structure

```

com.collegeManagementSystem
│
├── controller
├── services
├── dto
├── entities
├── repositories
├── advices
└── configuration

````

---

## 📦 Sample API Requests

### ✅ Create Student

```http
POST /students
````

```json
{
  "name": "Harry Potter",
  "subjects": [{ "id": 1 }],
  "professors": [{ "id": 2 }]
}
```

---

### ✅ Create Admission Record

```http
POST /admissions
```

```json
{
  "fees": 900,
  "student": {
    "id": 1
  }
}
```

---

## ❗ Validation Rules

* `fees` must be **≥ 500** and **≤ 1000**

**Example invalid request:**

```json
{
  "fees": 150,
  "student": { "id": 1 }
}
```

**Response:**

```json
{
  "timeStamp": "03:57:00 27-06-2025",
  "data": null,
  "error": {
    "status": "BAD_REQUEST",
    "message": "Validation failed",
    "subErrors": [
      "Fees cannot be less than 500"
    ]
  }
}
```

---

## 🔁 API Response Format

All responses are wrapped in a unified format:

```json
{
  "timeStamp": "03:31:11 27-06-2025",
  "data": { ... },         // actual response body
  "error": null            // or error details
}
```

---

## ⚙️ Getting Started

### 📋 Prerequisites

* Java 17+
* MySQL
* Maven or Gradle

### 🚀 Running the Application

1. **Clone the repository:**

```bash
git clone https://github.com/your-username/college-management-system.git
```

2. **Update DB config** in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/collegemanagementsystem
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

3. **Run the app:**

```bash
./mvnw spring-boot:run
```

or from your IDE (IntelliJ, Eclipse, etc.)

---

## 📚 Future Enhancements

* ✅ Add Swagger/OpenAPI documentation
* 🔐 Authentication & Authorization
* 📊 Dashboard for analytics
* 🌐 Frontend with React or Angular
* 🐳 Docker & CI/CD pipeline

---

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you'd like to change.

---

## 📄 License

This project is licensed under the MIT License.

```

---

Let me know if you'd like:
- Swagger config and link in the README
- A section for DB schema (ERD)
- A demo video link or screenshots section

Happy coding! 💻
```
