package classesAndObjects;

public class Main {

    public static void main(String[] args) {

        Dog dog1 = new Dog();
        dog1.name = "Tommy";
        dog1.bark();

        Dog dog2 = new Dog();
        dog2.name = "Jerry";
        dog2.eat();

        ComplexNumber c1 = new  ComplexNumber();
        c1.a = 3;
        c1.b = 4;
        c1.print();





    }
}

class ComplexNumber {
//    calculating Complex number a + bi
    int a;
    int b;

    void print() {
        System.out.println( a + " + " + b + "i");

    }
}


//treat it as a factory
class Dog {
//    properties
    String name;
    int age;
    String breed;

//    behavior
    void walk () {
        System.out.println(name + " is walking");
    }

    void eat () {
        System.out.println(name + " is eating");
    }

    void bark() {
        System.out.println(name + " is barking");
    }
}

class cat {

}
