# 🎧 Vibe — AI-Powered Song Recommendation Engine

An AI-powered backend service that recommends songs based on user mood using **vector embeddings** and **semantic search**.

Built with **Spring Boot + Spring AI + pgvector + PostgreSQL**.

---

## 🚀 Features

- 🎯 Semantic search using embeddings (not keyword-based)
- 🎵 Mood-based song recommendations (e.g., *sad*, *workout*, *classic*)
- 🧠 Vector similarity search using pgvector
- ⚡ Idempotent ingestion (no duplicate data)
- 🔎 Dynamic result size (`limit` parameter)
- 🧱 Clean layered architecture (Controller → Service → Vector Store)

---

## 🏗️ Tech Stack

- Java 17+
- Spring Boot
- Spring AI
- PostgreSQL
- pgvector extension
- Docker
- Ollama (local LLM) / OpenAI (optional)

---

## 📦 Architecture

```text
Client (Postman / Frontend)
        ↓
Controller (/ingest, /match-vibe)
        ↓
AiService
        ↓
EmbeddingModel → VectorStore (pgvector)
        ↓
PostgreSQL
```

---

## ⚙️ Setup Instructions

### 1️⃣ Start PostgreSQL with pgvector

```bash
docker compose up -d
```

If you get a port conflict:

```bash
docker ps
docker stop <container_id>
docker rm <container_id>
```

---

### 2️⃣ Database Configuration

```sql
CREATE DATABASE pgvector_vibe;
```

---

### 3️⃣ Application Configuration

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/pgvector_vibe
    username: postgres
    password: postgres

  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model: qwen3:30b
          temperature: 0.7

    vectorstore:
      pgvector:
        initialize-schema: true
```

---

### 4️⃣ Run the Application

```bash
./mvnw spring-boot:run
```

---

## 📥 API Endpoints

### 🔹 1. Ingest Songs

```http
POST /ingest
```

Response:

```text
Songs ingested successfully
```

or

```text
Songs already ingested, skipping
```

---

### 🔹 2. Match Vibe

```http
GET /match-vibe?feeling=sad&limit=3
```

| Param   | Description |
|--------|------------|
| feeling | Mood input |
| limit   | Number of results (1–10) |

Example:

```json
[
  {
    "title": "Someone Like You",
    "artist": "Adele",
    "genre": "sad",
    "summary": "A deeply emotional song..."
  }
]
```

---

## 🧠 How It Works

```text
Text → Embedding → Vector DB → Similarity Search → Results
```

---

## ⚠️ Key Concepts

### topK

- `topK(3)` = max 3 results
- Not guaranteed (threshold applies)

### Idempotent Ingestion

```java
if (!existing.isEmpty()) {
    return "Songs already ingested, skipping";
}
```

### Similarity Threshold

```java
.similarityThreshold(0.3)
```

---

## 🐛 Common Issues

### Container exists

```bash
docker rm -f pgvector-local
```

### Port conflict

```bash
netstat -ano | findstr 5432
```

### DB missing

```sql
CREATE DATABASE pgvector_vibe;
```

---

## 🔮 Future Improvements

- Genre filtering
- LLM integration
- Frontend UI
- AWS deployment

---

## 👨‍💻 Author

Umair Ali  
GitHub: umairali-bit
