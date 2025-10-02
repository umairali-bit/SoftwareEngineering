# 💱 Currency Converter API  

A Spring Boot application that integrates with **[FreeCurrencyAPI](https://freecurrencyapi.com/)** to convert between currencies, store conversion history in a MySQL database, and provide REST endpoints to access conversion results.  

---

## 🚀 Features  

- Convert between supported currencies using **FreeCurrencyAPI**  
- Store each conversion in **MySQL** for auditing/history  
- JPA **Auditing** support (`createdDate`, `createdBy`)  
- Optional **Hibernate Envers** support for historical versions of conversions  
- Global response wrapper (`ApiResponse`) and exception handling  
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

### 1. Convert Currency (Query Params)
```http
GET /converterCurrency?fromCurrency=EUR&toCurrency=USD&units=5000
```

✅ Example Response:
```json
{
  "fromCurrency": "EUR",
  "toCurrency": "USD",
  "units": 5000,
  "rate": 1.05,
  "convertedAmount": 5250.0,
  "provider": "freecurrencyapi",
  "timestamp": 1727746000
}
```

### 2. Convert Currency (Request Body)  
```http
POST /converterCurrency
Content-Type: application/json
```
```json
{
  "fromCurrency": "EUR",
  "toCurrency": "USD",
  "units": 5000
}
```

---

## 🗄️ Database  

Table: `conversion_history`  
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

If **Hibernate Envers** is enabled, an audit table `conversion_history_aud` will also be created.  

---

## ✅ TODO / Improvements  

- Add **user authentication** (Spring Security → auditorAware = logged-in user)  
- Add **OpenAPI/Swagger UI** for API docs  
- Add conversion history retrieval endpoint (`GET /history`)  
- Add validation (reject negative amounts, invalid currency codes)  

---

## 👨‍💻 Author  

Built by Umair Ali (Spring Boot + Backend Engineering journey 🚀).  
