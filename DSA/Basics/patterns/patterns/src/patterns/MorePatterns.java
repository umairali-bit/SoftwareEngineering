package patterns;

public class MorePatterns {
    public static void main(String[] args) {

        int b = 6;
        for (int i = 0; i < b; i++) {
            for (int j = 1; j <= b - i; j++) {
                System.out.print(" ");
            }
            System.out.print("*");

            for (int j = 0; j <= (2 * i - 1); j++) {
                System.out.print("*");
            }
            System.out.println();

        }

        for (int i = b - 2; i >= 0; i--) {
            for (int j = 1; j <= b - i; j++) {
                System.out.print(" ");
            }
            System.out.print("*");

            for (int j = 0; j <= (2 * i - 1); j++) {
                    System.out.print("*");

            }
            System.out.println();


        }


        int d = 7;
        for (int i = 0; i <= d; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");

            }
            System.out.println(" ");

            }

        for (int i = 0; i < d; i++) {
            for (int j = 1; j <= d - i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
        }

    }


