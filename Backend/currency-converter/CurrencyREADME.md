# 💱 Currency Converter API  

A Spring Boot application that integrates with **[FreeCurrencyAPI](https://freecurrencyapi.com/)** to convert between currencies, store conversion history in a MySQL database, and provide REST endpoints to access conversion results.  

---

## 🚀 Features  

- Convert between supported currencies using **FreeCurrencyAPI**  
- Store each conversion in **MySQL** for auditing/history  
- **Spring Data JPA Auditing**  
  - Tracks `createdDate` and `createdBy`  
  - `AuditorAwareImpl` returns `"system-user"` (can later be extended to authenticated users)  
- **Global Response Wrapping**  
  - All responses are wrapped in `ApiResponse<T>` with a timestamp and error structure  
- **Centralized Exception Handling**  
  - Via `GlobalExceptionHandler` (`RuntimeException`, `Validation`, and generic exceptions)  
- **Logging**  
  - SLF4J used across services/clients  
  - Client logs calls to FreeCurrencyAPI (`➡️` for outgoing calls, `❌` for errors)  
- Optional **Hibernate Envers** support for full historical entity versioning  
- OpenAPI/Swagger integration-ready  

---

## 🛠️ Tech Stack  

- **Java 17**  
- **Spring Boot 3**  
- **Spring Data JPA**  
- **MySQL 8**  
- **Hibernate Envers** (for entity history)  
- **Lombok**  
- **ModelMapper**  
- **Slf4j Logging**  

---

## ⚙️ Setup  

### 1. Clone Repo  
```bash
git clone https://github.com/your-username/currency-converter.git
cd currency-converter
```

### 2. Add API Key  
Get a **FreeCurrencyAPI** key from [here](https://freecurrencyapi.com/).  

Add it as an environment variable:  
#### Linux / macOS
```bash
export FREECURRENCY_API_KEY=your_api_key
```
#### Windows PowerShell
```powershell
$Env:FREECURRENCY_API_KEY="your_api_key"
```

### 3. Configure Database  
Update `application.properties`:  

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/currencydb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 4. Run App  
```bash
mvn spring-boot:run
```

---

## 📡 API Endpoints  

### Convert Currency (Query Params)
```http
GET /converterCurrency?fromCurrency=EUR&toCurrency=USD&units=5000
```

✅ Example Response:
```json
{
  "timeStamp": "12:40:17 02-10-2025",
  "data": {
    "fromCurrency": "EUR",
    "toCurrency": "USD",
    "units": 5000,
    "rate": 1.05,
    "convertedAmount": 5250.0,
    "provider": "freecurrencyapi",
    "timestamp": 1727746000
  },
  "error": null
}
```

---

## 🗄️ Database  

### Conversion History Entity  
Stored in `conversion_history`:  

```sql
CREATE TABLE conversion_history (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  from_currency VARCHAR(10),
  to_currency VARCHAR(10),
  units DOUBLE,
  rates DOUBLE,
  converted_amount DOUBLE,
  created_date TIMESTAMP,
  created_by VARCHAR(255)
);
```

- **Auditing** auto-fills `created_date` and `created_by` (`system-user` by default).  
- If **Envers** is enabled, an audit table `conversion_history_aud` will also be created for full history.  

---

## 📋 Logging  

- Every API call to FreeCurrencyAPI logs:  
  - `➡️ Calling FreeCurrencyAPI: base=EUR target=USD`  
  - `❌ Client error from FreeCurrencyAPI: {error details}`  
- Application startup logs confirm DB + auditing config:  
  - `"API Key Loaded: fca_li*****"`  

---

## ✅ TODO / Improvements  

- Add **user authentication** (Spring Security → auditorAware = logged-in user)  
- Add **OpenAPI/Swagger UI** for API docs  
- Add conversion history retrieval endpoint (`GET /history`)  
- Add validation (reject negative amounts, invalid currency codes)  
- Add caching layer for rates to reduce API calls  

---

## 👨‍💻 Author  

Built by Umair Ali (Spring Boot + Backend Engineering journey 🚀).  
