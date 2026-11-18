package day1;

public class CoreJava {

    /*
    What is the difference between method overloading and method overriding in Java?
    Can you give one simple example?


    Method overloading:
    when two methods have the same name but different paramter list.
    compile time polymorphism is an example of method overloading

    Method overriding
    if a child class overrides a method mentioned in the parent class. This is called method overriding
    run time polymorphism is an example of that
     */


    public static void main(String[] args) {

        CoreJava c = new CoreJava();
        System.out.println(c.add(5 , 2));

        double result = c.add(5 , 2,3);
        System.out.println(result);

        Animal a = new Dog();
        a.sound();




    }

    public int add (int a, int b) {
        return a + b;
    }

    public double add (int a, int b, int c) {
        return a + b + c;
    }

    //invalid
    /*
    public double add (int a, int b){
        return (double) (a + b)
     */



}

class Animal {

    public void sound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {

    @Override
    public void sound() {
        System.out.println("Dog sound");
    }

}
