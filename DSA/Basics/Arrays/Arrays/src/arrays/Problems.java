package arrays;

import java.util.Arrays;

public class Problems {

    public static void main(String[] args) {

//  find the sum of this array
        int numbers[] = {23, 12, 4 ,90, 80, 3};

        int result = 0;

        for (int i = 0; i < numbers.length; i++) {
            result += numbers[i];
        }
        System.out.println(result);


//   find the array.length without using array.length

        int res = 0;
        for(int number: numbers) {
            res++;

        }
        System.out.println(res);



//    find the min value in the numbers array

        int min = Integer.MAX_VALUE;
        for (int k: numbers)  {
            if (k < min) {
                min = k;
            }


        }
        System.out.println(min);



//    find the max value in the numbers array
        int max = Integer.MIN_VALUE;
        for (int m: numbers) {
            if (m > max) {
                max = m;
            }
        }
        System.out.println(max);

    }
}
