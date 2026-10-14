package strings;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String name = "Saul Goodman"; //stored in string pool in heap memory
        String sameName = "Saul Goodman";// point to the same object in heap memory
        String newName = new String("Saul Goodman"); //creates a new object outside of string pool

//      test
        System.out.println(name == sameName); //true
        System.out.println(name == newName); //false
        System.out.println(name.equals(newName)); //true


        Scanner sc  = new Scanner(System.in);
        System.out.println("Enter your first name");
        String firstName =  sc.nextLine();
        System.out.println("Enter your second name");
        String secondName = sc.nextLine();

        System.out.println(firstName + " " + secondName);

    }
}
