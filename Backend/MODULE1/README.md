# Introduction to Spring & Spring Boot

Spring Boot is built on top of the Spring Framework. The Spring Framework was first introduced in 2003 by Rod Johnson as a solution to the complexity of Java Enterprise Edition (JEE), which required extensive configuration before writing business logic. These configurations involved setting up servers, databases, logging, and more.

The Spring Framework introduced the Inversion of Control (IoC) container to manage such configurations. Initially, configuration was done using XML, which still required significant setup.

In 2009, Spring was sold to VMware. By 2014, VMware introduced **Spring Boot**, a streamlined, production-ready version of Spring with auto-configurations and pre-built dependencies, allowing developers to focus on business logic right away.

---

## Spring Framework

- **Spring** is a dependency injection framework that promotes loose coupling in Java applications.
- It simplifies the development of Java EE applications.
- Enables development using **Plain Old Java Objects (POJOs)** with enterprise-level features applied non-invasively.

### Key Components
- Core Container  
- AOP (Aspect-Oriented Programming)  
- JDBC  
- Web  
- Testing  

---

## IoC Container

The **IoC Container** in Spring is responsible for:

- Creating and managing **beans** (Java objects)
- Injecting **dependencies** into those beans
- Managing their lifecycle

### Responsibilities

- **Dependency Injection (DI):** Injecting dependencies into beans during initialization.
- **Event-Driven Programming (EDP):** Handling events and listeners.
- **Aspect-Oriented Programming (AOP):** Adding cross-cutting concerns like logging or security.

### Types of Configuration

- **XML-based**
- **Annotation-based**
- **Java-based**

---

## Gradle, Maven, and Artifact

Spring projects typically use **Gradle** or **Maven** as build automation tools to manage:

- Project dependencies
- Builds
- Testing
- Packaging

### Artifact
An artifact is the final build output (e.g., `myapp.jar`). It also defines the application name, base directory, main class, and possibly the package structure.

Maven artifacts follow this coordinate structure:  
`groupId:artifactId:version`

---

## Gradle vs Maven

| Feature               | Maven                          | Gradle                                      |
|----------------------|--------------------------------|---------------------------------------------|
| Language              | XML (`pom.xml`)               | Groovy/Kotlin DSL (`build.gradle`)          |
| Performance           | Slower due to XML parsing      | Faster with incremental builds & daemons    |
| Popularity            | Mature and widely adopted      | Newer but increasingly popular              |
| Configuration Style   | Convention-based (rigid)       | Script-based (flexible)                     |
| Dependency Management | Centralized                    | Flexible with support for Maven Central     |

---

## Beans in Spring

- Beans are core components of a Spring application.
- Managed by the IoC container.
- Their lifecycle includes creation, dependency injection, initialization, usage, and destruction.

---

## Spring Annotations

Spring annotations offer a modern, Java-based way to configure beans without relying on verbose XML files.

### Common Annotations
- `@Component`
- `@Service`
- `@Repository`
- `@Controller`
- `@SpringBootApplication`

---

## Defining Beans

1. **Stereotype Annotations**  
   Annotate your class with `@Component`, `@Service`, `@Repository`, or `@Controller`.  
   - `@SpringBootApplication` is the main entry point for Spring Boot apps.

2. **Configuration Class**  
   - Annotate the class with `@Configuration`.
   - Define beans manually using `@Bean` annotated methods.

---

## Bean Lifecycle

1. **Creation** – Instance is created by the IoC container.  
2. **Dependency Injection** – Dependencies are injected via constructor, field, or setter.  
3. **Initialization** – Spring invokes initialization methods like those annotated with `@PostConstruct`.  
4. **Usage** – The bean is available for use in the application.  
5. **Destruction** – Spring calls `@PreDestroy` methods when shutting down.

---

## Bean Lifecycle Hooks

- `@PostConstruct`: Called immediately after dependency injection.
- `@PreDestroy`: Called just before the bean is removed from the container.

---

## Bean Scopes

Beans are stored in heap memory.

| Scope     | Description                                                                                       |
|-----------|---------------------------------------------------------------------------------------------------|
| Singleton | (Default) A single instance per Spring IoC container.                                              |
| Prototype | A new instance is created each time the bean is requested.                                        |
| Request   | One bean instance per HTTP request (Web-aware context only).                                      |
| WebSocket | One bean instance per WebSocket lifecycle (Web-aware context only).                               |

---

## Dependency Injection (DI)

### Smoothie Analogy 🧃

**Without DI:**  
You (the class) go shopping for ingredients (dependencies) yourself.  
- Tightly coupled  
- Harder to change or test

**With DI:**  
Ingredients are provided to you.  
- Loosely coupled  
- Easier to manage and test

### Why Use DI?

- **Loose Coupling**: Components are independent and maintainable  
- **Flexibility**: Swap dependencies easily  
- **Testability**: Mock dependencies during unit testing

### DI Methods

1. **Constructor Injection**  
   - Dependencies passed via constructor  
   - Promotes immutability and testability  

2. **Field Injection**  
   - Uses `@Autowired` to inject directly into fields  

---

## Spring Boot vs Spring Framework

| Feature                       | Spring Framework | Spring Boot                              |
|------------------------------|------------------|-------------------------------------------|
| Dependencies Management      | Manual           | Uses pre-configured starter dependencies |
| Configuration                | Manual           | Auto-configuration out of the box        |
| Server Setup                 | External         | Embedded Tomcat, Jetty, etc.             |
| Health & Metrics             | Manual           | Built-in Actuator support                |
| External Configuration Files | Limited          | Flexible via `.properties`, `.yaml`, etc.|

---

## `pom.xml` in Spring Boot

- Spring Boot uses **Maven** to manage dependencies defined in `pom.xml`.
- The `spring-boot-starter-parent` includes default libraries and configurations.
- Spring Boot uses `spring-boot-dependencies` for managing library versions via `dependencyManagement`.

---

## What is Auto-Configuration?

**Auto-configuration** automatically configures Spring applications based on the dependencies on the classpath and application properties.

### How It Works

1. **Classpath Scanning**  
   Spring Boot looks for classes/libraries in the classpath and applies configuration accordingly.

2. **Configuration Classes**  
   Spring Boot includes predefined configuration classes for common components (DataSource, JPA, etc.).

3. **Conditional Beans**  
   Spring uses annotations like `@ConditionalOnClass`, `@ConditionalOnBean`, `@ConditionalOnProperty` to apply configurations selectively.

### Example Conditional Annotations

- `@ConditionalOnClass(DataSource.class)` – Only configure if the class is present  
- `@ConditionalOnBean(DataSource.class)` – Only configure if a bean exists  
- `@ConditionalOnProperty("my.property")` – Only configure if property is set

---

## Auto-Configuration Features

- **@PropertySources**: Automatically loads common property sources.  
- **META-INF**: The magic of auto-configuration is powered by `spring-boot-autoconfigure` JAR.  
- **Enhanced Conditional Support**: Spring Boot extends the conditional system to cover common patterns.

---

## Spring Boot Internal Flow

1. **Initialization**  
   Application starts from a class annotated with `@SpringBootApplication`.

2. **Component Scanning**  
   Automatically detects and loads components, beans, and configurations.

3. **Auto-Configuration**  
   Applies default settings for available libraries on the classpath.

4. **External Configuration**  
   Loads `.properties`, `.yaml`, environment variables, or CLI arguments.

5. **Embedded Server**  
   Launches a pre-configured web server (e.g., Tomcat).

6. **Lifecycle Callbacks**  
   Executes `@PostConstruct` methods and initialization logic.

7. **Application Ready**  
   App is fully initialized and ready to handle requests.


   # 🚗 Spring Boot Annotations Explained (With a Toy Car Analogy)

Imagine you're building a **smart toy car**, and Spring is your **smart assistant**. You just label parts, and Spring assembles and connects everything for you.

---

## 🔧 Core Annotations and What They Do

### 1. `@SpringBootApplication`  
**🛠 Main switch to start the project**  
Starts the whole Spring Boot app by combining:
- `@Configuration`
- `@EnableAutoConfiguration`
- `@ComponentScan`

> Think of this as **pressing the ON button** for the toy car factory.

---

### 2. `@Service`  
**⚙️ Business logic class**  
Marks a class that does core work like controlling speed or direction.

> Like labeling the **Engine**.

---

### 3. `@Component`  
**📦 Generic Spring-managed part**  
Marks a class as something Spring should manage.

> Like labeling a **GPS sensor or horn** — important but not "core logic".

---

### 4. `@Configuration`  
**📘 Custom build instructions**  
Tells Spring: "This class contains instructions (methods) to build beans manually."

> Acts like the **manual for building parts**.

---

### 5. `@Autowired`  
**🔌 Auto-plug required parts**  
Tells Spring to **auto-connect dependencies**.

---
``` java
@Autowired
Battery battery;
Like saying: “Put a battery here, please.”
````

6. @Bean
🧪 Create a custom part manually
Used inside @Configuration to build and return a Spring-managed object.

```java
@Bean
public Battery battery() {
    return new Battery("Rechargeable");
}
```
Like writing: “Here’s how to build a custom battery.”


7. @Scope
📦 How many parts to build?
Defines lifecycle and count of the bean.

* singleton = one shared part

* prototype = new part every time

Example: Use 1 battery for all cars or 1 per car?

8. @PostConstruct
   
🔧 Run setup after creation

Runs a method after the part is made.

```java
@PostConstruct
public void init() {
    System.out.println("Wheels are spinning!");
}
Like testing parts after installing them.
```

9. @PreDestroy
    
🧹 Cleanup before shutdown

Runs a method just before the part is removed or app shuts down.

```java
@PreDestroy
public void cleanup() {
    System.out.println("Car is shutting down...");
}
Like unplugging the battery before storing the car.
```

10. @ConditionalOnProperty
    
🕹 Only build if switch is ON

Activates a bean only if a setting is true in application.properties.

```java
@ConditionalOnProperty(name = "car.gps.enabled", havingValue = "true")
Like: “Only add GPS if the user enables it.”
```

| Annotation               | Description                                                  |
| ------------------------ | ------------------------------------------------------------ |
| `@SpringBootApplication` | Starts the whole Spring Boot factory                         |
| `@Service`               | Marks business logic class (Engine)                          |
| `@Component`             | General-purpose part (like a horn)                           |
| `@Configuration`         | Manual to create parts                                       |
| `@Autowired`             | Auto-attach needed part (like battery)                       |
| `@Bean`                  | Create a custom part (manually coded battery)                |
| `@Scope`                 | Defines how often to make the part (one or many)             |
| `@PostConstruct`         | Setup logic after part creation (test wheels)                |
| `@PreDestroy`            | Cleanup logic before part is removed (unplug battery)        |
| `@ConditionalOnProperty` | Create part only if config switch is ON (add GPS if enabled) |



