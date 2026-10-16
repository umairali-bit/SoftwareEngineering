package patterns;

public class Main {

    public static void main(String[] args) {
        /*
         * * * * * * *
         * * * * * * *
         * * * * * * *
         * * * * * * *
         * * * * * * *
         * * * * * * *
         * * * * * * *
         */

        int a = 7;
        for (int i = 1; i <= a; i++) {

            for (int j = 1; j <= a; j++) {
                System.out.print('*' + " ");
            }
            System.out.println();

        }

        System.out.println("-".repeat(20));
        /*
         *
         * *
         * * *
         * * * *
         * * * * *
         * * * * * *
         */

        int n = 6;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("*" + " ");

            }
            System.out.println();
        }


        System.out.println("-".repeat(20));
/*
        1
        1 2
        1 2 3
        1 2 3 4
        1 2 3 4 5
        1 2 3 4 5 6
 */
        int m = 6;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + " ");

            }
            System.out.println();
        }

        System.out.println("-".repeat(20));
        /*
         *
         **
         ***
         ****
         *****
         ******
         */
        int k = 6;
        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= k - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }

            System.out.println();

        }

        System.out.println("-".repeat(20));
        /*
         *
         * *
         * * *
         * * * *
         * * * * *
         * * * * * *
         */
        int l = 6;
        for (int i = 1; i <= l; i++) {
            for (int j = 1; j <= l - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                System.out.print("*" + " ");
            }

            System.out.println();

        }

        System.out.println("-".repeat(20));


        int b = 6;
        for (int i = 0; i < b; i++) {
            for (int j = 1; j <= b - i; j++) {
                System.out.print(" ");
            }
            System.out.print("*");

            for (int j = 0; j <= (2 * i - 1); j++) {
                if (j == (2 * i - 1)) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }

            }
            System.out.println();

        }

        for (int i = b - 2; i >= 0; i--) {
            for (int j = 1; j <= b - i; j++) {
                System.out.print(" ");
            }
            System.out.print("*");

            for (int j = 0; j <= (2 * i - 1); j++) {
                if (j == (2 * i - 1)) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }

            }
            System.out.println();


        }

        System.out.println("-".repeat(20));
    }
}
