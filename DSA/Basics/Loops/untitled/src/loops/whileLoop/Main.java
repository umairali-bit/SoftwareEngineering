package loops.whileLoop;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

//  A while loop repeatedly executes a block of code as long as a condition is true.
//  The body is executed at least once

//        int i = 0;
//
//        while(i<5) {
//            System.out.println("Hello Java");
//            i++;
//        }

//        Scanner sc = new Scanner(System.in);
//        boolean hasLearnt = false;
//
//        while(!hasLearnt) {
//            System.out.println("still need to learn");
//            System.out.println("did you learn this?");
//            hasLearnt = sc.nextBoolean();
//        }



//   do-while loop

        int i = 0;
        do{
            System.out.println(i);
            i++;
        } while(i <= 5);
    }
}
