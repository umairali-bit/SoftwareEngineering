# 📘 Spring Boot + Hibernate + JPA Made Simple

This README explains how **Spring Boot**, **Hibernate**, and **JPA** work together for database access in Java applications. It’s written to be beginner-friendly.

---

## 🚀 Core Concepts

### 1. Entities
- **Entity** = A Java class that represents a database table.  
- **Primary Key** = Unique identifier for a record.  
- **Foreign Key** = Connects rows between tables.  

Example:
```java
@Entity
public class Patient {
   @Id
   private Long id;
   private String name;
}
```

---

### 2. Hibernate
- Hibernate = A framework that **maps Java objects to database tables**.  
- Executes SQL behind the scenes using **JDBC**.  
- Implements the **JPA standard** (so Hibernate = JPA provider).  

---

### 3. JPA (Jakarta Persistence API)
- **JPA is just a specification (set of rules)**.  
- Provides annotations like `@Entity`, `@Id`, `@OneToMany`.  
- Hibernate is one implementation. Others include EclipseLink and OpenJPA.  

---

### 4. Spring Data JPA
- An **abstraction layer** on top of JPA.  
- Removes boilerplate code — you don’t need to write DAO classes manually.  
- Provides ready-to-use repository interfaces.

Example:
```java
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
   List<Patient> findByName(String name);
}
```

---

## 🔄 Entity Lifecycle (Hibernate)

1. **Transient** → New object, not in DB.  
2. **Persistent** → Saved/managed by Hibernate (`persist()`, `save()`).  
3. **Detached** → Was persistent, now not tracked (`detach()`, `close()`).  
4. **Removed** → Deleted from DB (`delete()`).  

---

## 🤝 Relationships

### Common Annotations
- `@OneToOne`
- `@OneToMany`
- `@ManyToOne`
- `@ManyToMany`
- `@JoinColumn`, `@JoinTable`

### Owning Side vs Inverse Side
- **Owning side** → controls the foreign key in the database.  
- **Inverse side** → mapped side; does not control the foreign key.  

---

## ⚡ Cascade & Orphan Removal

### Cascade Types
- `PERSIST` → Saves children automatically.  
- `MERGE` → Updates children.  
- `REMOVE` → Deletes children when parent is deleted.  
- `REFRESH` → Refreshes children.  
- `DETACH` → Detaches children.  
- `ALL` → Applies all of the above.  

### Orphan Removal
- Deletes children if no longer referenced by parent.  
- Example: A `Patient`’s `Appointment` is deleted when removed from the list.  

---

## ⚙️ Hibernate & JPA Configs in Spring Boot

```properties
# How Hibernate handles DB schema
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

- **ddl-auto options**  
  - `none` → Do nothing.  
  - `validate` → Only check schema.  
  - `update` → Update schema automatically.  
  - `create` → Recreate schema at startup.  
  - `create-drop` → Create schema on start, drop at shutdown.  

💡 Use:
- `none` / `validate` → Production.  
- `update` / `create` → Development.  
- `create-drop` → Testing.  

---

## 📊 Extra Features

- **Pagination & Sorting**: Spring Data JPA supports both via `Pageable` and `Sort`.  
- **Custom Queries**: Create methods like `findByNameAndDepartment`.  
- **JPQL (Java Persistence Query Language)**: Query Java objects instead of raw SQL.  

---

## ✅ Summary
- **Hibernate** = ORM framework that implements JPA.  
- **JPA** = Specification for ORM in Java.  
- **Spring Data JPA** = Easier way to use JPA/Hibernate with less boilerplate.  
- **Mappings, Cascades, and Lifecycle** help manage entities and relationships.  
- **Config properties** let you control how Hibernate interacts with your database.  

---

👉 This is your **all-in-one cheat sheet** for Spring Boot + Hibernate + JPA.
