# Spring Boot Development Guide

This guide explains how to set up useful development tools and practices in a Spring Boot application, including **DevTools, Auditing, RestClient, Logging, Actuator, and OpenAPI/Swagger**.

---

## 🚀 Installing DevTools

Spring Boot DevTools provides features that make development faster, such as automatic restarts and live reloads.

### 1. Add DevTools Dependency

In your `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

If using Gradle:

```groovy
developmentOnly("org.springframework.boot:spring-boot-devtools")
```

### 2. IntelliJ Setup
- Enable **"Build project automatically"**.
- Enable **"Allow auto-make to start even if app is running"** under *Registry* (`Ctrl + Alt + Shift + /`).

### 3. Automatic Restart
- Restarts the application whenever files on the classpath change.
- Uses two class loaders:
  - One for unchanged classes (dependencies).
  - One for changed classes (your code).
- Faster than a full restart.

### 4. Useful Configurations

```properties
# Disable restart
spring.devtools.restart.enabled=false

# Exclude static resources from restart
spring.devtools.restart.exclude=static/**,public/**

# Poll interval (ms) for classpath changes
spring.devtools.restart.poll-interval=2000

# Quiet period (ms) before triggering a restart
spring.devtools.restart.quiet-period=1000
```

---

## 📅 Auditing in Spring Boot

Auditing automatically manages fields such as **created date, last modified date, and user information**.

### Steps to Enable Auditing

1. **Create an Auditable Base Entity**

```java
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
```

2. **Extend Entities**
```java
@Entity
public class ConversionHistory extends Auditable {
    @Id
    @GeneratedValue
    private Long id;
    private String fromCurrency;
    private String toCurrency;
}
```

3. **Provide an AuditorAware Implementation**
```java
@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("system"); // Replace with authenticated user
    }
}
```

4. **Enable JPA Auditing**
```java
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class JpaConfig {}
```

5. **Hooks (Entity Lifecycle Events)**
- `@PrePersist` – before insert  
- `@PreUpdate` – before update  
- `@PreRemove` – before delete  

---

## 🌐 RestClient

The `RestClient` (Spring 6+) is a modern synchronous HTTP client.

### Example Usage

```java
RestClient restClient = RestClient.builder()
    .baseUrl("https://api.example.com")
    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer token")
    .build();

String response = restClient.get()
    .uri("/data")
    .retrieve()
    .body(String.class);
```

### Error Handling
- Use `.onStatus()` to handle HTTP errors.
- Wrap calls with `try/catch` for custom exceptions.

---

## 📖 Logging

Logging helps trace application flow and debug issues.  
Spring Boot uses **SLF4J + Logback** by default.

### SLF4J Overview
- SLF4J is **not** a logger itself.
- It’s a **facade** that routes log calls to the chosen implementation (Logback, Log4j2, JUL).

### Log Levels
- `FATAL` – crashes the system  
- `ERROR` – runtime errors  
- `WARN` – warnings  
- `INFO` – normal events  
- `DEBUG` – debugging details  
- `TRACE` – most detailed  

### Configuration

```properties
logging.level.root=INFO
logging.level.com.example=DEBUG
```

### Log Formatters
```properties
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%level] %c{1.} - %m%n
logging.file.name=app.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%level] %c{1.} - %m%n
```

---

## ⚡ Actuator

Spring Boot Actuator provides **metrics, health checks, and monitoring endpoints**.

### Dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### Example Endpoints
- `/actuator/health`
- `/actuator/metrics`
- `/actuator/env`

Configure exposure:

```properties
management.endpoints.web.exposure.include=health,metrics,info
```

---

## 📑 API Documentation with OpenAPI & Swagger

Springdoc integrates **Swagger UI** with Spring Boot.

### Dependency

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>
```

### Access Swagger UI
Run the app and open:

```
http://localhost:8080/swagger-ui.html
```

---

## ✅ Summary

- **DevTools** → Faster development restarts.  
- **Auditing** → Auto-track created/updated info.  
- **RestClient** → Modern HTTP client for APIs.  
- **Logging** → Use SLF4J/Logback for structured logs.  
- **Actuator** → Health & metrics endpoints.  
- **Springdoc OpenAPI** → API documentation via Swagger UI.  
