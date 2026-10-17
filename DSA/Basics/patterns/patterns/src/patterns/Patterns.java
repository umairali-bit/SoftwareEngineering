package patterns;

public class Patterns {

    public static void main(String[] args) {


        int a = 6;
        for (int i = 0; i <= a; i++) {

            for (int j = 1; j <= a - i; j++) {
                System.out.print("*" + " ");
            }
            System.out.println();


        }

        System.out.println("-".repeat(20));

        int b = 6;
        for (int i = 0; i <= b; i++) {

            for (int j = 1; j <= b - i; j++) {
                System.out.print(j + " ");
            }
            System.out.println();


        }

        System.out.println("-".repeat(20));

        int c = 6;

        for (int i = 1; i <= c; i++) {

            for (int j = 1; j <= c - i; j++) {
                System.out.print("  ");
            }
            for (int j = i; j >= 1; j--) {
                System.out.print(j + " ");
            }

            for (int k = 2; k <= i; k++) {
                System.out.print(k + " ");
            }
            System.out.println();

            }

        System.out.println("-".repeat(20));

        int d = 6;

        for (int i = 0; i <= d; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= d - i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }

        }






        }









