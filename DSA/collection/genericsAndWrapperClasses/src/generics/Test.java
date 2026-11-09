package generics;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        List<Number> numbers = new ArrayList<>();

        numbers.add(10);
        numbers.add(20.5);

        Number n1 = numbers.get(0);
        Number n2 = numbers.get(1);

        System.out.println(n1 + " " + n2);
    }
}