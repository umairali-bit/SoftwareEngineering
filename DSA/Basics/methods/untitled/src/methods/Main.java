package methods;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        greet();

//      calling a non-static member from a static context
        Main obj = new Main();
        int res = obj.addNumbers(1,2);
        System.out.println(res);

        average(5,10);

        System.out.println(obj.getRandom());


    }

    static void greet() {
        System.out.println("Hello World");
    }

    public int addNumbers(int a, int b) {
        return a + b;
    }


    public static void average(int a, int b) {
        int avg = (a + b) / 2;
        System.out.println(avg);


    }

    public int getRandom() {

        Random random = new Random();
        return (random.nextInt(1, 11));
    }
}

