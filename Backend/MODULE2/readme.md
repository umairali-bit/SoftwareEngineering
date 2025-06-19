
# 🚀 Introduction to Spring Boot Web MVC

Think of a web application as a **restaurant**:

- The **customer (user)** places an order (sends a request).
- The **waiter (controller)** takes the order.
- The **chef (service)** prepares the food (logic).
- The **kitchen (database)** stores the ingredients (data).
- The **waiter** delivers the food back (response).

Spring Boot Web MVC follows this model using the **MVC architecture** (Model-View-Controller).

---

## 🧱 MVC Architecture

| Layer           | Role |
|----------------|------|
| **Model**      | Data & business logic (e.g. Employee, Department) |
| **View**       | What the user sees (in REST APIs, it's usually JSON/XML) |
| **Controller** | Handles input and returns output |

---

## 🌐 Types of Web APIs

1. **REST APIs** (Stateless, lightweight, uses HTTP)
2. **SOAP APIs** (Heavy XML-based, enterprise apps)
3. **WebSockets** (Real-time 2-way communication)

---

## 🛠 What are REST APIs?

**REST = Representational State Transfer**

They use HTTP methods:

| Method  | Purpose                            |
|---------|------------------------------------|
| GET     | Retrieve data                      |
| POST    | Create data                        |
| PUT     | Replace data                       |
| PATCH   | Update partial data                |
| DELETE  | Remove data                        |

---

## 🧩 Spring Boot Starter Web

Add this to use REST in Spring Boot:
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

It includes:
- `Spring MVC` → web app support
- `Jackson` → handles JSON
- `Tomcat` → embedded web server

---

## 🗂️ What is a Controller?

It’s the **waiter** that:
- Accepts requests
- Maps URLs to methods
- Returns data (usually JSON)

Example:
```java
@RestController
public class EmployeeController {
    @GetMapping("/employees")
    public List<EmployeeDTO> getAll() {
        return service.getAllEmployees();
    }
}
```

---

## 📦 What is a DTO?

**DTO = Data Transfer Object**

Imagine a pizza delivery box:
- You only send what’s needed (no raw dough or kitchen tools)
- It **transfers safe, clean data**

DTOs help avoid exposing sensitive or internal fields from entities.

---

## 🎯 Request Mapping

| Annotation     | HTTP Method |
|----------------|-------------|
| `@GetMapping`  | GET         |
| `@PostMapping` | POST        |
| `@PutMapping`  | PUT         |
| `@PatchMapping`| PATCH       |
| `@DeleteMapping`| DELETE     |

---

## 🔄 Dynamic Data from Requests

| Use this        | For                          |
|----------------|------------------------------|
| `@PathVariable`| Data in URL (`/user/1`)       |
| `@RequestParam`| Query strings (`?id=1`)       |
| `@RequestBody` | JSON body from POST/PUT/PATCH |

---

## 💾 Persistence Layer with JPA

### What is JPA?

**Java Persistence API**: lets Java talk to databases using **objects instead of SQL**.

---

## 🧙 ORM: Magic Behind the Scenes

**ORM = Object-Relational Mapping**

Think of ORM as a **translator** between Java and SQL:
- Java class = Table
- Object = Row
- Field = Column

Popular ORMs:
- Java: **Hibernate**
- Python: Django ORM
- JS: Sequelize
- C#: Entity Framework

---

## 🔍 Example: JPA Entity

```java
@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
```

---

## 🧾 JPA Repository

You don’t need SQL! Use method names:

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByName(String name);
}
```

| Method            | What it does         |
|-------------------|----------------------|
| `findAll()`       | Get all rows         |
| `save()`          | Save/Update          |
| `findById(id)`    | Get by ID            |
| `deleteById(id)`  | Delete by ID         |

---

## 🧠 What is JPQL?

**Java Persistence Query Language**

Like SQL but works with Java **entities**, not tables.

```java
SELECT u FROM User u WHERE u.email = :email
```

---

## 🧪 H2 Database

An **in-memory database** great for:
- Testing
- Rapid development

No need to install anything.

---

## 🔧 Service Layer

Think of it as the **chef**:
- Contains **business logic**
- Connects controllers and repositories
- Keeps the code modular and maintainable

---

## 🔍 Reflection in Java

**Reflection = Looking into a mirror**

Lets you:
- Inspect classes and methods at runtime
- Dynamically call methods or access fields

Used heavily in Spring, Hibernate, JUnit.

---

## 🛡️ Exception Handling

Use:
- `@RestControllerAdvice` to catch global errors
- Custom error format (e.g. `ApiResponse` and `ApiError`)
- HTTP status codes like 404, 400, 500

### Sample `ApiResponse`
```json
{
  "timeStamp": "2025-06-17T06:34:19",
  "data": null,
  "error": {
    "status": "BAD_REQUEST",
    "message": "Input validation failed",
    "subErrors": [
      "Email is invalid"
    ]
  }
}
```

---

## ✅ Validation Annotations

| Annotation     | Use                             |
|----------------|----------------------------------|
| `@NotNull`     | Field must not be null          |
| `@NotEmpty`    | Not null and not empty          |
| `@NotBlank`    | Not null and not just spaces    |
| `@Size`        | Specify min/max length          |
| `@Min`/`@Max`  | Minimum/maximum value            |
| `@Email`       | Must be a valid email            |
| `@Pattern`     | Regex matching                   |
| `@AssertTrue`/`@AssertFalse` | Must be true/false |
| `@Past`/`@Future` | For dates                     |
| `@DecimalMin`/`@DecimalMax` | Decimal ranges       |
| `@Positive`, `@Negative` | Sign validations      |
| `@URL`, `@CreditCardNumber`, `@Length`, `@Range` | Special checks |

---

## 🧵 Summary Flow

1. User sends request → `Controller`
2. `Controller` sends data → `Service Layer`
3. `Service` fetches/stores from → `Repository`
4. `Repository` interacts with → `Database`
5. Responses go back → `Service` → `Controller` → Client
