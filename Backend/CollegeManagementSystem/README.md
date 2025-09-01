# 📘 College Management System (Spring Boot Project)

A full-stack backend system for managing **students, professors, subjects, and admission records**.  
Built with **Spring Boot, JPA/Hibernate, PostgreSQL, and ModelMapper**.

---

## 🚀 Features
- CRUD for Students, Professors, Subjects
- Automatic Admission Record creation for each student
- Assign / Remove Subjects to Professors
- Assign Professors to Students per Subject
- Bi-directional relationship management
- Proper error handling with consistent API responses

---

## 🛠️ Tech Stack
- Java 17+
- Spring Boot 3.x
- Spring Data JPA & Hibernate
- PostgreSQL
- Lombok & ModelMapper
- REST API (JSON-based)

---

## 🔗 REST API Endpoints & Examples

### 👨‍🎓 Students

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/students` | Create student |
| GET    | `/api/students` | Get all students |
| GET    | `/api/students/{id}` | Get student by ID |
| PUT    | `/api/students/{id}` | Update student |
| PATCH  | `/api/students/{id}` | Partially update student |
| DELETE | `/api/students/{id}` | Delete student |

**Example Request (Create Student)**:
```json
{
  "name": "Chad",
  "admissionRecord": {
    "fees": 1200.0,
    "admissionDate": "2025-08-01T10:00:00"
  }
}
```

**Success Response**:
```json
{
  "timeStamp": "2025-08-29T06:14:09.0053847",
  "data": {
    "id": 4,
    "name": "Chad",
    "admissionRecord": {
      "id": 4,
      "fees": 1200.0,
      "studentId": 4,
      "studentName": "Chad",
      "admissionDate": "2025-08-01T10:00:00"
    },
    "subjectIds": [],
    "professorIds": []
  },
  "error": null
}
```

**Error Response** (Student not found):
```json
{
  "timeStamp": "2025-08-31T10:00:00",
  "data": null,
  "error": {
    "status": "NOT_FOUND",
    "message": "Student not found with the ID: 99",
    "subErrors": null
  }
}
```

---

### 👩‍🏫 Professors

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/professors` | Create professor |
| GET    | `/api/professors/{id}` | Get professor by ID |
| PUT    | `/api/professors/{id}` | Update professor |
| DELETE | `/api/professors/{id}` | Delete professor |
| POST   | `/api/professors/assign-subject` | Assign subject(s) to professor |
| DELETE | `/api/professors/{professorId}/subjects` | Remove subject(s) from professor |
| GET    | `/api/professors/{professorId}/subjects` | Get subjects taught by professor |

**Example Request (Assign Subjects to Professor)**:
```json
{
  "id": 4,
  "subjectIds": [1, 2, 3]
}
```

**Success Response**:
```json
{
  "timeStamp": "2025-08-30T03:17:39.0136899",
  "data": {
    "id": 4,
    "name": "Walter White",
    "studentIds": [],
    "subjectIds": [1, 2, 3]
  },
  "error": null
}
```

**Error Response** (Delete violation):
```json
{
  "timeStamp": "2025-08-30T04:26:26.4847787",
  "data": null,
  "error": {
    "status": "INTERNAL_SERVER_ERROR",
    "message": "update or delete on table 'professor' violates foreign key constraint 'fk_student_professor'",
    "subErrors": null
  }
}
```

---

### 📚 Subjects

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/subjects` | Create subject |
| GET    | `/api/subjects` | Get all subjects |
| GET    | `/api/subjects/{id}` | Get subject by ID |
| PUT    | `/api/subjects/{id}` | Update subject |
| DELETE | `/api/subjects/{id}` | Delete subject |
| POST   | `/api/subjects/{id}/professor/{professorId}` | Assign professor to subject |
| DELETE | `/api/subjects/{id}/professor` | Remove professor from subject |

**Example Response (Get All Subjects)**:
```json
{
  "timeStamp": "2025-08-31T09:24:28.4744738",
  "data": [
    {
      "id": 1,
      "name": "Computer Science",
      "professorId": 4,
      "professorName": "Walter White",
      "students": [
        { "id": 2, "name": "Brian Smith" },
        { "id": 3, "name": "Chloe Davis" }
      ]
    },
    {
      "id": 2,
      "name": "Physics",
      "professorId": 4,
      "professorName": "Walter White",
      "students": []
    }
  ],
  "error": null
}
```

---

### 📝 Admission Records
Created automatically when a student is created.

**Example (Fetched via Student API)**:
```json
{
  "id": 1,
  "fees": 5000.0,
  "studentId": 1,
  "studentName": "Alice Johnson",
  "admissionDate": "2025-08-01T10:00:00"
}
```

---

## ⚠️ Error Handling Convention
- All responses wrap data inside:
```json
{
  "timeStamp": "...",
  "data": {...},
  "error": {
    "status": "BAD_REQUEST | NOT_FOUND | INTERNAL_SERVER_ERROR",
    "message": "Human-readable error",
    "subErrors": [...]
  }
}
```
