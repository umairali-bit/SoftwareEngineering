package day4;

public class Questions {

    /*
    What is the difference between checked and unchecked exceptions in Java
    Give one example of each and explain how Java handles them


    Checked exception:
        It's checked by compiler, Java explicitly wants us to use throws in the method. we can also use catch it
        gracefully by using try-catch block

    Unchecked Exception:
        This exception doesn't require compile time exception. The compiler doesn't force you to use try-catch block or
        use throws in the method.
        , example Runtime exception, or Arithmetic exception


     LLM - Checked exceptions are exceptions that the compiler requires us to either handle using a try-catch block
              or declare using the throws' keyword. Examples include IOException and SQLException.

              Unchecked exceptions do not require explicit handling or declaration. Examples include ArithmeticException
              and NullPointerException.

              Both types occur at runtime, but the main difference is whether exception handling is enforced by the
              compiler.


     AI
     What is the difference between supervised learning, unsupervised learning, and reinforcement learning?

     Give one practical banking example of each and explain how each model learns.

     Supervised learning learns from the labeled training data to learn the relationships between input and outputs
     For example a bank can predict an employees salary based on experience using historical salary data

     Unsupervised learning uses unlabel data discover hidden patterns or groups. For example a bank can group their customer
     based on their spending habits using k-means or clustering

     Reinforcement learning is a separate type of machine learning in which an agent interacts with an environment.
     Takes actions, and receives rewards. For example a bank can learn which advertisement to display tro maximize customer
     clicks



     LLM
     Supervised learning uses labeled training data to learn the relationship between inputs and known outputs.
     For example, a bank can predict an employee's salary based on experience using historical salary data.

     Unsupervised learning uses unlabeled data to discover hidden patterns or groups. For example, a bank can group
     customers based on their spending habits using K-means clustering.

     Reinforcement learning is a separate type of machine learning in which an agent interacts with an environment,
     takes actions, and receives rewards. For example, a bank can learn which advertisements to display to maximize
     customer clicks.


     SpringBoot
     What is the difference between @Component, @Service, @Repository in Spring boot
     Why would you use @Service for an AccountService class and @Repository for an AccountRepository class instead of
     annotating both with @Component?

     These are stereotype SpringBoot annotations.
     @COmponent tells springboot to treat the object like a component of the respective application
     @Service defines the business logic
     @Repository used to map the entity to a table. it also translates the SQL exceptions

     I would use @Service for the AccountService class to separate the business layer. and @Repsoiutry for the Data
     Access Object.


     LLM @Component, @Service, and @Repository are Spring stereotype annotations that allow Spring to discover and manage
     components as beans.

    @Component is a generic stereotype annotation, while @Service and @Repository are specialized forms of @Component.

    @Service identifies classes responsible for business logic, whereas @Repository identifies classes responsible for data
    access and supports persistence exception translation.

    I would use @Service for AccountService and @Repository for AccountRepository to clearly separate application layers,
    improve readability, and maintain separation of concerns.




     */
}
