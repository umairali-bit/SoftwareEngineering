package classesAndObjects;

public class MethodOverloading {

    public static void main(String[] args) {

        Greeting greeting = new Greeting();
        greeting.print();
        greeting.print("Programmer");


    }
}

class Greeting {

    void print() {
        System.out.println("Hello World");
    }

    void print(String name) {
        System.out.println("Hello World " + name);
    }
}
