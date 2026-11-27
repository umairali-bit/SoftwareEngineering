package day5;

public class Questions {

    /*
    What is the difference between an abstract class and an interface in Java?
    In a banking application with savings, checking and business accounts, when would you choose abstract class
    over interface?

    An interface is a contract. Methods mentioned in an interface needs to be implemented. A class can implements
    multiple interfaces

    An abstract class is abstract. Half of the code implementation can be implemented in there and the other half could
    be implemented in a class that's extending the abstract class.

    In a banking application I would create a generic account class that provides id, account number, account type, customer
    , balance, and make BusinessAccount, checking accounts, and saving accounts extend that class with an enum accountType



    LLM: An interface defines a contract that implementing classes must follow. A class can implement multiple interfaces.
        Interfaces can also provide implementations through default methods.

        An abstract class provides a common foundation for related classes. It can contain constructors,
        instance variables, abstract methods and concrete methods. However, a class can extend only one class.

        In a banking application, I would create an abstract Account class containing common properties such as account
        ID, account number, customer and balance, along with shared business logic.

        SavingsAccount, CheckingAccount and BusinessAccount would extend this class and provide their own
        account-specific business logic.



        AI:
        What is the difference between overfitting and underfitting in machine learning?

        Suppose a bank trains a fraud detection model with the following results:
                Dataset	        Accuracy
                Training data	98%
                Testing data	65%

        What problem might the model be experiencing, and what techniques would you use to address it?

        Overfitting. when model learns from the training data but fails to implement the same accuracy on testing data.
        Its just learning noise from the data.
        Underfitting: when model fails to learn and fails to test for example training and testing both are 65%

        The fraud detection model addresses overfitting
        I would collect more training data, I would also limit the maximum_depth of a decision tree


        SpringBoot:
        What is a Spring Bean, and what is the default bean scope in Spring Boot?

        Suppose AccountController and TransactionController both inject AccountService.

        Will Spring create two separate instances of AccountService, or will both controllers share the same instance?
        Explain why.

        A spring bean is simply an object that is instantiated, assembled and managed by spring IoC container.
        The default bean scope
        in Spring boot is singleton

        The controllers share the same instance specifically because of the singleton scope, not simply because the object is
        a bean. Other bean scopes exist, such as prototype and request.






     */
}
