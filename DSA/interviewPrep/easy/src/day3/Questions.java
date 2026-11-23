package day3;

public class Questions {

    /*
    What is the difference between ArrayList and HashSet in Java
    ArrayList implements List interface which further implements collection
    HashSet implements Set interface which further implements collection

    ArrayList allow duplicates, preserves insertion order
    HashSet does not allow duplicates, insertion order is not guaranteed


    LLM:
    ArrayList implements the List interface, while HashSet implements the Set interface. Both interfaces
    extend the Collection interface.

    ArrayList allows duplicate elements and maintains insertion order. It also supports accessing elements by index.
    Allow multiple null values

    HashSet does not allow duplicate elements and does not guarantee insertion order. It uses a hash-based structure
    for storing elements, making operations such as adding, removing, and searching typically O(1) on average, allows
    only 1 null value



    AI/ML
    What is the difference between classification and regression in supervised machine learning?

    Give one practical banking example of each and explain what the model would predict

    Regression is used to predict a numerical value
    Classification is used to predict a class or a type

    In a bank if we are hiring an employee and predicting the salary based on experience

    On the other hand if we need to predict a group or a class of customers that left the bank based on our new policy
    i could use classification where leave the bank is (1) and stay with the bank is (0)


    LLM:
     Regression and classification are two types of supervised machine learning.

    Regression predicts continuous numerical values. For example, a bank could use linear regression to predict an
    employee's salary based on years of experience.

    Classification predicts discrete categories or labels. For example, a bank could use a classification model to
    predict whether a customer is likely to leave after a new policy is introduced.

    The main difference is that regression predicts a numerical quantity, while classification predicts a category.



    SpringBoot

    What is Dependency Injection in SpringBoot

    Dependency injection is a design technique where an object or function receives it required dependencies from an
    external source

    Constructor injection guarantees an object created in a completed and valid state with all required dependencies.
    Its also achieve immutability, because the dependencies can be marked final. it's easier in unit testing, you can
    easily instantiate the class in a test by directly passion mock or fake objects into the constructor.




     */
}
