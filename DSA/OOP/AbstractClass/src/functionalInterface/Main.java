package functionalInterface;

import java.util.function.Consumer;

public class Main {

    FInterface fI = new FInterface() {
        @Override
        public void function() {

        }
    };

    FInterface fI2 = () -> {

    };

//    Walkable walkable = new Walkable() {
//
//        @Override
//        public void walks(int steps) {
//            System.out.println("walks " + steps);
//
//        }
//
//    };

    public static void main(String[] args) {

        Walkable walkable = (int steps) -> {
            System.out.println("walked " + steps + " steps");
            return steps;

        };

        walkable.walks(6);


    }
}






@FunctionalInterface
interface FInterface{

    void function();
}


@FunctionalInterface
interface Walkable {

    int walks (int steps);
}


