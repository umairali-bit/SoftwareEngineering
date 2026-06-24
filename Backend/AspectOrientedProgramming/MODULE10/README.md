# Aspect-Oriented Programming (AOP)

This module explains Aspect-Oriented Programming in Spring Boot. AOP helps you move repeated logic, such as logging, validation, security, transaction checks, and error handling, away from your main business methods.

Instead of writing the same logging or validation code inside every service method, you write it once in an aspect and let Spring run it automatically at the correct time.

## Folder Structure

```text
MODULE10
|-- 10.1 IntroductionToAscpectOrientedPorgramming
|   `-- Basic AOP introduction with @Aspect and @Before advice
|-- 10.2 DeclaringPointCutInAOP
|   `-- Different pointcut expressions like execution, within, and @annotation
|-- 10.3 DeclaringAdvicesInAOP
|   `-- Different advice examples such as @Before, @After, and custom pointcuts
|-- 10.4 InternalWorkingOfAOP
|   `-- Notes about how Spring AOP works internally
|-- 10.5 AOPUseCases
|   `-- Real-world AOP use cases
|-- Implementation
|   `-- Final Spring Boot AOP project
`-- notes
    `-- PDF and Word notes for this module
```

## What Is AOP?

AOP stands for Aspect-Oriented Programming.

In normal programming, business logic and supporting logic often get mixed together.

Example:

```java
public String orderPackage(Long orderId) {
    log.info("Before method");
    validateOrder(orderId);
    checkSecurity();
    String result = processOrder(orderId);
    log.info("After method");
    return result;
}
```

This works, but the method is doing too many things. With AOP, the service method can focus on business logic, and the extra behavior can be handled separately.

## Why Use AOP?

Use AOP when the same logic is needed in many places.

Common examples:

- Logging method calls
- Measuring execution time
- Security checks
- Validation
- Transaction handling
- Error logging
- Auditing
- Caching

These are called cross-cutting concerns because they cut across many parts of the application.

## Important AOP Terms

### Aspect

An aspect is a class that contains extra behavior you want to apply to other methods.

In this module:

```java
@Aspect
@Component
public class LoggingAspect {
}
```

### Advice

Advice is the action that runs before, after, or around a method.

Common advice annotations:

- `@Before`: runs before the target method
- `@After`: runs after the target method finishes
- `@AfterReturning`: runs after successful return
- `@AfterThrowing`: runs when the method throws an exception
- `@Around`: wraps the method and can run code before and after it

### Join Point

A join point is a point during program execution where advice can run. In Spring AOP, this usually means a method call.

Example:

```java
public void beforeShipmentMethods(JoinPoint joinPoint) {
    log.info("Before Shipment: {}", joinPoint.getSignature());
}
```

### Pointcut

A pointcut decides which methods the advice should apply to.

Example:

```java
@Before("execution(* com.example.AopApplication.services.impl.ShipmentServiceImpl.*(..))")
```

This means:

- `execution`: match method execution
- `*`: any return type
- `ShipmentServiceImpl.*`: any method inside `ShipmentServiceImpl`
- `(..)`: any number of arguments

### Target Object

The target object is the real object whose method is being called, such as `ShipmentServiceImpl`.

### Proxy

Spring AOP works by creating a proxy object around your real bean. Calls go through the proxy first, then the proxy decides whether to run the aspect before calling the real method.

## Lesson Progression

### 10.1: Introduction to AOP

This section introduces a simple logging aspect.

The aspect runs before methods in `ShipmentServiceImpl`.

```java
@Before("execution(* com.example.AopApplication.services.impl.ShipmentServiceImpl.*(..))")
public void beforeShipmentMethods(JoinPoint joinPoint) {
    log.info("Before Shipment: {}", joinPoint.getSignature());
}
```

Main idea: AOP can run code before a service method without changing the service method itself.

### 10.2: Declaring Pointcuts

This section shows different ways to select methods.

Examples used:

- `execution(...)`: select methods by package, class, method name, return type, and arguments
- `within(...)`: select all methods inside a package or class
- `@annotation(...)`: select methods that have a specific annotation
- `@Pointcut`: create a reusable pointcut

Example:

```java
@Pointcut("@annotation(com.example.AopApplication.aspects.MyLogging) && within(com.example.AopApplication.services.impl.*)")
public void annotationPointcut() {}
```

Main idea: pointcuts control where your aspect logic runs.

### 10.3: Declaring Advice

This section focuses on advice types and reusable pointcuts.

Examples include:

- Running code before a method
- Running code after a method
- Creating custom pointcuts
- Using custom annotations to trigger aspects

Main idea: advice controls when the aspect logic runs.

### 10.4: Internal Working of AOP

This section explains how Spring AOP works internally.

Important idea:

Spring does not directly call your original bean in many cases. It creates a proxy object. The proxy intercepts method calls and applies advice before or after calling the real method.

Simple flow:

```text
Client calls service method
        |
        v
Spring proxy receives the call
        |
        v
Aspect advice runs
        |
        v
Actual service method runs
        |
        v
More advice may run
        |
        v
Result is returned
```

### 10.5: Real-World Use Cases

AOP is useful for features that are needed across many classes.

Real-world examples:

- Log every service method call
- Track method execution time
- Check user permissions before sensitive operations
- Validate input before saving data
- Log exceptions globally
- Audit who performed an action

## Final Implementation Project

The final project is located here:

```text
Implementation/AopApplication
```

It is a Spring Boot project using:

- Java 21
- Spring Boot
- Spring AOP
- Spring Security
- Spring Validation
- Lombok
- JUnit tests

## Main Classes in the Implementation

### ShipmentService

This is the service interface.

```java
public interface ShipmentService {
    String orderPackage(Long orderId);
    String trackPackage(Long trackId);
    String createEmployee(Employee employee);
}
```

### ShipmentServiceImpl

This class contains the actual business methods.

```java
@Service
public class ShipmentServiceImpl implements ShipmentService {
}
```

Important methods:

- `orderPackage(Long orderId)`: processes an order
- `trackPackage(Long trackId)`: tracks a package and demonstrates exception handling
- `createEmployee(Employee employee)`: creates an employee after validation

### LoggingAspect

This aspect logs before and after methods marked with `@MyLogging`.

It also measures execution time for service methods using `@Around`.

```java
@Around("allServiceMethodsPointcut()")
public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    Long startTime = System.currentTimeMillis();

    try {
        return joinPoint.proceed();
    } finally {
        Long endTime = System.currentTimeMillis();
        Long diff = endTime - startTime;
        log.info("Execution time for {} : {}", joinPoint.getSignature().getName(), diff);
    }
}
```

Main idea: `@Around` is powerful because it controls when the target method actually runs.

### ValidationAspect

This aspect validates an `Employee` object before calling the service method.

It runs when a method is marked with:

```java
@ValidateEmployee
```

If the employee is invalid, it throws an exception before the service method runs.

Validation rules in `Employee`:

- Name cannot be blank
- Email must be valid
- Age must be at least 18

### SecurityAspect

This aspect checks whether the current user has the `ROLE_ADMIN` authority.

It runs when a method is marked with:

```java
@RequiresAdmin
```

If the user is not an admin, it throws:

```text
Only admins can access this resource
```

### GlobalErrorHandler

This aspect logs exceptions thrown from service methods.

```java
@AfterThrowing(pointcut = "execution(* com.example.AopApplication.services.*.*(..))", throwing = "ex")
```

Note: if service implementations are inside `services.impl`, the pointcut should include that package if you want to catch implementation methods directly.

Example:

```java
execution(* com.example.AopApplication.services..*(..))
```

The `..` means include subpackages too.

## Custom Annotations Used

### @MyLogging

Used to trigger logging advice.

```java
@MyLogging
public String orderPackage(Long orderId) {
}
```

### @RequiresAdmin

Used to require admin access before a method runs.

```java
@RequiresAdmin
public String orderPackage(Long orderId) {
}
```

### @ValidateEmployee

Used to validate employee input before the method runs.

```java
@ValidateEmployee
public String createEmployee(Employee employee) {
}
```

## How to Run the Project

Go to the implementation project:

```bash
cd D:\SoftwareEngineering\Backend\AspectOrientedProgramming\MODULE10\Implementation\AopApplication
```

Run the Spring Boot application:

```bash
.\mvnw.cmd spring-boot:run
```

Run tests:

```bash
.\mvnw.cmd test
```

## How the Test Examples Work

### Admin Security Test

```java
@WithMockUser(roles = "ADMIN")
void appTestOrderPackage() {
    shipmentService.orderPackage(4L);
}
```

This test passes because the mock user has the admin role.

If you use:

```java
@WithMockUser(roles = "USER")
```

then the security aspect should block access.

### Validation Test

```java
Employee employee = new Employee();
employee.setName("");
employee.setEmail("bad-email");
employee.setAge(15);
```

This employee is invalid because:

- Name is blank
- Email is not valid
- Age is below 18

The validation aspect throws an `IllegalArgumentException`.

## AOP Cheat Sheet

| Concept | Meaning |
|---|---|
| Aspect | Class containing cross-cutting logic |
| Advice | Code that runs before, after, or around a method |
| Pointcut | Expression that selects target methods |
| Join Point | Method execution point where advice can run |
| Proxy | Wrapper object created by Spring to apply aspects |
| Target | Real object being proxied |
| Weaving | Process of applying aspects to target objects |

## Common Pointcut Examples

Match all methods in a class:

```java
execution(* com.example.AopApplication.services.impl.ShipmentServiceImpl.*(..))
```

Match all methods in a package:

```java
within(com.example.AopApplication.services.impl.*)
```

Match methods with an annotation:

```java
@annotation(com.example.AopApplication.aspects.annotations.MyLogging)
```

Match all methods in a package and subpackages:

```java
execution(* com.example.AopApplication.services..*(..))
```

## 10 Top AOP Interview Questions

### 1. What is AOP?

AOP stands for Aspect-Oriented Programming. It is used to separate cross-cutting concerns, such as logging, validation, security, and transaction handling, from business logic.

### 2. What problem does AOP solve?

AOP reduces duplicate code. Instead of writing logging, validation, or security checks inside every method, you write that logic once in an aspect and apply it wherever needed.

### 3. What is an aspect in Spring AOP?

An aspect is a class marked with `@Aspect`. It contains advice methods that run before, after, or around selected target methods.

### 4. What is advice?

Advice is the code that runs at a selected point. Examples include `@Before`, `@After`, `@AfterReturning`, `@AfterThrowing`, and `@Around`.

### 5. What is a pointcut?

A pointcut is an expression that selects where advice should run. For example, it can select all methods in a package or all methods with a specific annotation.

### 6. What is the difference between `@Before` and `@Around`?

`@Before` runs only before the target method. `@Around` wraps the target method, so it can run code before and after the method, change arguments, handle exceptions, or even stop the method from running.

### 7. What is a join point?

A join point is a point in program execution where advice can be applied. In Spring AOP, a join point is usually a method execution.

### 8. How does Spring AOP work internally?

Spring AOP works mainly through proxies. Spring creates a proxy around a bean. When a method is called through the proxy, Spring checks matching pointcuts and runs the advice.

### 9. What is the difference between Spring AOP and AspectJ?

Spring AOP is proxy-based and mainly works with Spring beans and method executions. AspectJ is more powerful and supports compile-time or load-time weaving, but it is more complex.

### 10. What are real-world use cases of AOP?

Common use cases include logging, execution time tracking, security checks, input validation, transaction management, exception logging, auditing, and caching.

## Quick Summary

AOP helps keep business logic clean.

In this module:

- `10.1` introduces basic AOP
- `10.2` explains pointcuts
- `10.3` explains advice
- `10.4` explains internal proxy working
- `10.5` shows real-world use cases
- `Implementation` combines logging, timing, validation, security, and exception handling

The most important idea: AOP lets you write supporting logic once and apply it automatically to many methods.
