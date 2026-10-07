package loops.nestedLoops;


/*
0. 1,2,3,4,5
1. 1,2,3,4,5
2. 1,2,3,4,5
3. 1,2,3,4,5
4. 1,2,3,4,5
5. 1,2,3,4,5
6. 1,2,3,4,5
7. 1,2,3,4,5
8. 1,2,3,4,5
9. 1,2,3,4,5
 */

public class Main {
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            System.out.print(i + ". ");
            for (int j = 1; j <= 5; j++) {
                System.out.print(j);
                if(j <5) {
                    System.out.print(",");
                }

            }

            System.out.println(" Printed " + i );

        }

/*
0
0 1
0 1 2
0 1 2 3
0 1 2 3 4
0 1 2 3 4 5
0 1 2 3 4 5 6
0 1 2 3 4 5 6 7
0 1 2 3 4 5 6 7 8
0 1 2 3 4 5 6 7 8 9
 */
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.print(j + " ");
            }
            System.out.println();
        }



    }
}
