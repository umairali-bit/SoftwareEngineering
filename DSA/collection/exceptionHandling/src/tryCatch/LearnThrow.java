package tryCatch;

import java.util.Scanner;

public class LearnThrow {

    public static void main(String[] args)  {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of cases: ");

        int cases = sc.nextInt();
        try {
            if (cases > 100) {
                throw new MyException("My Exception");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }


}

class MyException extends Exception {

    MyException(String message) {
        super(message);
    }

}
