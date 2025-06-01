# Introduction to Spring & Spring Boot

- Spring Boot is built on the Spring Framework.  
- The Spring Framework was first introduced in 2003 by Rod Johnson.  
- Before 2003, developers created Java applications using Java Enterprise Edition (JEE).  
- The problem with JEE was that developers had to configure a lot before writing a single piece of business logic.  
- Configurations included server, database, and logging setup.  
- The Spring Framework helped developers by managing these configurations.  
- The part of the Spring Framework responsible for these configurations is the IoC container. It manages the lifecycle and dependencies of objects in a Spring application.  
- IoC initially used XML files for configuration.  
- However, IoC containers still required a lot of configuration time.  
- In 2009, Rod Johnson sold Spring to VMware.  
- In 2014, through VMware, Spring Boot was introduced, which came pre-configured with starter dependencies and was ready for business logic.  

## Spring Framework

- Spring is a Dependency Injection framework that makes Java applications loosely coupled.  
- Spring simplifies the development process for JavaEE applications.  
- Spring enables building applications from “plain old Java objects” (POJOs) and applying enterprise services non-invasively to POJOs.  
- Important components:  
  - Core Container  
  - AOP (Aspect-Oriented Programming)  
  - JDBC  
  - Web  
  - Testing  

## IoC Container

- In the Spring Framework, the IoC container is responsible for managing application components and injecting dependencies. The container creates objects (beans), wires them together, configures them, and manages their complete lifecycle.  
- IoC handles event-driven programming (EDP), Dependency Injection (DI), and Aspect-Oriented Programming (AOP).  
- IoC Container:  
  - The Inversion of Control (IoC) container instantiates, configures, and assembles beans.  
  - It starts working as soon as the application context is loaded, either via XML configuration, annotations, or Java-based config.  
- Dependency Injection (DI):  
  - The process where the IoC container injects dependencies into beans based on configuration (e.g., via XML).  
  - DI happens during the container's initialization phase, not before.  
- POJOs (Business Objects):  
  - These are the actual beans managed by the IoC container.  
  - They are instantiated and injected by the container as it processes the configuration.  

## Gradle, Maven, and Artifact

- In the Spring Framework, Gradle and Maven are build automation tools used to manage dependencies, build projects, and automate tasks like testing, packaging, and deployment.  
- An artifact is the name of the build output (e.g., myapp.jar).  
- It also sets the name of your project directory, application class, and sometimes the package structure.  
- It is part of the Maven coordinate system (groupId:artifactId:version).  

## What Are Gradle and Maven?

| Feature               | Maven                   | Gradle                                |
|-----------------------|-------------------------|-------------------------------------|
| Language              | Uses XML (pom.xml)       | Uses Groovy/Kotlin DSL (build.gradle or build.gradle.kts) |
| Performance           | Slower builds due to XML parsing | Faster with incremental builds and daemon process |
| Popularity            | Very mature and widely used | Newer, but increasingly popular    |
| Configuration         | More rigid and convention-based | More flexible and scriptable       |
| Dependency Management | Based on a centralized repository model | Also uses Maven Central, but with more customization |

- POM stands for Project Object Model file.  

## Beans

- Beans build up Spring applications.  
- Beans are classes whose lifecycle and dependencies are managed by the Spring Framework.  
- Beans are managed objects that are instantiated, assembled, and managed by the Spring IoC container.  
- Beans are the backbone of any Spring application and are the core building blocks wired together to create an application.  
- They are used to manage dependencies.  

## Spring Annotations

- Traditionally, Spring allows developers to manage bean dependencies using XML-based configuration.  
- With Spring Boot, these beans are managed by Spring Annotations.  
- Spring Annotations provide an alternative way to define beans and their dependencies using Java-based configuration.  
- Unlike the XML approach, Java-based configuration allows you to manage bean components programmatically. That is why Spring annotations were introduced.  

## Defining Beans

Two ways to define beans:  
1. Using Stereotype Annotations  
   - Annotate your class with one of the stereotype annotations (@Component, @Service, @Repository, @Controller). These annotations tell Spring that the class should be managed as a bean.  
   - @SpringBootApplication is the starting point of any Spring Boot application.  
2. Explicit Bean Declaration in the Configuration Class  
   - Create a configuration class annotated with @Configuration. This class contains methods to define and configure beans manually.  

## Bean Lifecycle

1. Bean Created  
   a. The bean instance is created by invoking a static factory method (when defining a bean in the configuration class) or an instance factory method (@Component).  
2. Dependency Injected  
   a. After the bean is created, Spring sets the bean’s properties and dependencies, either through setter injection, constructor injection, or field injection.  
3. Bean Initialized  
   a. If a bean implements the InitializingBean interface or defines a custom initialization method annotated with @PostConstruct, Spring invokes this initialization method after configuring the bean.  
4. Bean Used  
   a. The bean is now fully initialized and ready to be used by the application.  
5. Bean Destroyed  
   a. Spring invokes the destruction method when the bean is no longer needed or when the application context shuts down.  

## Bean Lifecycle Hooks

1. The @PostConstruct annotation marks a method that should be invoked immediately after a bean has been constructed and all dependencies have been injected.  
2. The @PreDestroy annotation marks a method that should be invoked just before a bean is destroyed by the container. This method can perform necessary cleanup or resource release tasks.  

## Scope of Beans

Beans are stored in heap memory.  

| Scope     | Description                                                                                  |
|-----------|----------------------------------------------------------------------------------------------|
| Singleton | (Default) Scopes a single bean definition to a single object instance per Spring IoC container. |
| Prototype | Scopes a single bean definition to any number of object instances.                          |
| request   | Scopes a single bean definition to the lifecycle of a single HTTP request. That is, each HTTP request has its own instance of a bean created from a single bean definition. Only valid in the context of a web-aware Spring Application context. |
| WebSocket | Scopes a single bean definition to the lifecycle of a WebSocket. Only valid in the context of a web-aware Spring Application context. |
