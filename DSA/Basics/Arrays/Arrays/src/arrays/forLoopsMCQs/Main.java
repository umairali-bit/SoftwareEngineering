package arrays.forLoopsMCQs;

public class Main {

    public static void main(String[] args) {
        for (int i = 1; i <=3; i++) {
            for (int j = 3; j >= 1; j--) {
                System.out.print(i + " " + j + " ");
            }
        }

        System.out.println("-".repeat(5));

        for (int i = 1; i <=5; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.print(i + " " + j);
            }
        }
        System.out.println("-".repeat(5));

        for(int i = 1; i <=3; i++) {
            for(int j = 1; j <= i; j++) {
                System.out.print(j + " ");
            }
        }
        System.out.println("-".repeat(5));

        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (i + j == 4) {
                    continue;
                }

                System.out.print(i  + j + " ");
            }
        }

        System.out.println("-".repeat(5));

        int[][] matrix = new int[3][3];
        int value = 1;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = value;
                value++;
            }
        }

        int sum = 0;

        for (int[] row : matrix) {
            for (int num : row){
                sum += num;
            }
        }

        System.out.println(sum);

        System.out.println("-".repeat(5));

        String[] names = {"Alice", "Bob", "Charlie"};
        for (String name : names) {
            System.out.print(name + " ");
        }

        System.out.println("-".repeat(5));


        String str = "Java Programming";
        String[] words = str.split("a");

        System.out.println(words.length);

        System.out.println("-".repeat(5));

        int[] numbers = {1,2,3,4,5};
        int target = 3;
        boolean found = false;

        for(int i = 0; i < numbers.length; i++){
            if(numbers[i] == target){
                found = true;
                break;
            }
        }

        System.out.println(found);

        System.out.println("-".repeat(5));

        int[][] matrix2 = {
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };

        int sum2 = 0;

        for (int i = 0; i < matrix2.length; i++) {
            sum2 += matrix2[i][matrix2.length -i -1];
        }

        System.out.println(sum2);

        System.out.println("-".repeat(5));

        for (int i = 1; i <=3; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.println(i * j + " ");
            }
        }





    }


}
