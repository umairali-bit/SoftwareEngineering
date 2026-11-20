package day1;

public class SpringBoot {

    /*

    What is the difference between @Component, @Service, and @Repository in Spring Boot?
    Why would you use @Service for business logic?


    All these annotations comes from dependency injection

    @Component tells spring to treat an object like a component of this application
    @Service defines business logic of the application
    @Repository used to fetch, save or update data in database.

    @Service marks the class that holds the brain of the application. Its a specialized version of componenet
    when the application starts spring spots @service and creates an single instance of that class that you are
    abke to inject anywhere in the application


    correction: these are Spring StereoType annotations not dependency injection annotations
    correction: besides the persistence layer, @Repository supports Spring's persistence exception translation
                mechanism, which can convert supported database exceptions into Spring's DataAccessException hierarchy



    LLM answer: "@Component, @Service, and @Repository are Spring stereotype annotations. All three allow Spring to
                detect and manage classes as beans.

                @Component is a general-purpose annotation.

                @Service is used for classes containing business logic, such as transferring money between
                accounts.

                @Repository identifies the data access layer, which communicates with the database. It also supports
                exception translation.

                I use @Service for business logic because it keeps responsibilities separate and makes the application
                easier to maintain and test."



     */



    /*

    What is a Large Language Model (LLM), and how would you integrate an LLM into an
    existing Java Spring Boot application?


    LLM is large language model. It uses artificial intelligence to generate, understand and process human language.
    it powers modern AI chatbots, writing assistants and coding tools such as Gemini, chatGpt, and Claude


    I would create a service that will talk to LLM. so basically requests comes in through controller and
    flows through the service. service is connected to the LLM and LLM will answer the query. it the other way
    to output the answer.

    basically user -> controller -> service -> LLM
    llm-> service -> controller -> user


    LLM answer: "A Large Language Model is an AI model trained on large amounts of data that can understand and
                generate human language. Examples include models powering ChatGPT, Claude, and Gemini.

                To integrate an LLM into a Spring Boot application, I would create a REST controller to
                receive user requests and a service layer to handle the business logic.

                Inside the service, I would use Spring AI or an HTTP client to communicate with the LLM
                provider through its API.

                The service sends the user's prompt to the LLM, receives the generated response, and
                returns it through the controller to the user.

                I would also secure the API key and handle errors, timeouts, and sensitive customer
                information."

     */


}
