package polymorphism;

public class Main {

    public static void main(String[] args) {

        Data d1 = new Data();

//        compile time polymorphism (method Overloading)
        System.out.println(d1.add(5,2));
        System.out.println(d1.add(2.5, 3.5));
        System.out.println(d1.add(1, 2, 3));

//      runtime polymorphism (method overriding)
        Data d2 = new Child();
        System.out.println(d2.add(5,2, 5));

    }
}

class Data {

    public int add (int a, int b) {
        return a + b;
    }

    public double add (double a, double b) {
        return a + b;
    }

    public int add (int a, int b, int c) {
        return a + b + c;
    }


}

class Child extends Data {

    @Override
    public int add(int a, int b, int c) {
        System.out.println("Runtime polymorphism");

        return a + b + c;
    }
}
