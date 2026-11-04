package functionalInterface;

import java.util.function.*;

public class TypesOFFunctionalInterface {

    public static void main(String[] args) {


    // types of functional interfaces

    //consumer - prints

    Consumer<String> printer =
            e -> System.out.println("You said " + e);

        printer.accept("Hello");


//  BiConsumer - takes two inputs

        BiConsumer<Double, Double> printCoords =
                (lat, lang) -> System.out.printf("Coords: %.2f, %.2f%n", lat, lang);

        printCoords.accept(1.0, 2.0);


//  Supplier - generate a value
        Supplier<Double> randomValue = () -> Math.random();
        System.out.println(randomValue.get());



//  Function - Transform a value into another
        Function<String, Integer> lengthFunc = s -> s.length();
        System.out.println(lengthFunc.apply("Hello"));


//  Predicate - Test a condition
        Predicate<String> isHello = s -> s.equalsIgnoreCase("hello");
        System.out.println(isHello.test("Hello"));

    }



}


