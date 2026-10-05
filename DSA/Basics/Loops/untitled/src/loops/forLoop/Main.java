package loops.forLoop;

import java.util.Scanner;

public class Main {

//    for loop
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a number greater than 1");
        int n = sc.nextInt();

//        printing numbers from 1 - 5

//        for(int i = 1; i<=5; ++i) {
//            System.out.println(i);
//        }
//        Table of N number
//        for(int i = 1; i<=10; i++) {
//            System.out.println(n + " * " + i + " = " + i*n);
//        }

//        sum of N natural numbers
        int res = 0;
        for (int i = 1; i <=n; i++) {

            res += i;

        }
        System.out.println(res);

//        Fibonacci series
//        int first = 0;
//        int second = 1;
//
//        System.out.print(first + " " + second + " ");
//
//
//        for  (int i = 3; i <=n; i++) {
//            int next = first + second;
//
//            System.out.print(next + " ");
//
//            first = second;
//            second = next;



        }





    }



