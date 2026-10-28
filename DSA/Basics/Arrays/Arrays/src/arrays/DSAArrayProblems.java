package arrays;

import java.util.Arrays;

public class DSAArrayProblems {

    public static void main(String[] args) {

        maxValue(new int[]{1,2,3,4,5,6,7,8,9});
        minValue(new int[]{1,2,3,4,5,6,7,8,9});

//        swapping two numbers in an array with temp variable

        int[] nums = {1,2,3,4,5};
        swappingNums(nums);
        System.out.println(Arrays.toString(nums));

//      swapping lastNumberInSortedPosition
        int[] array = {2,3,4,5,6,7,8,1};
        lastNumberInSortedPosition(array);
        System.out.println(Arrays.toString(array));

        practiceEx();


    }

    public static void swappingNums(int[] array) {

        int first_index = 0;
        int second_index = array.length -1;

        int temp = array[first_index];
        array[first_index] = array[second_index];
        array[second_index] = temp;

    }

    public static void maxValue(int[] array) {
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }

        }
        System.out.println(max);
    }

    public static void minValue(int[] array) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }

        }
        System.out.println(min);
    }

    public static void lastNumberInSortedPosition(int[] array) {

        int n = array.length;
        int last =  array[n - 1];
        int swapIndex = n -2;

        while(swapIndex >= 0 && array[swapIndex] > last) {
            swapIndex--;
        }

        for(int i = n - 1; i >= swapIndex+2; i--) {
            array[i] = array[i-1];

        }
        array[swapIndex+1] = last;

    }

    public static void practiceEx () {

        int[] a = {2,5,8,11,14,17,9};

        int n = a. length;
        int last = a[n -1];
        int swapIndex = n - 2;

        while(swapIndex >= 0 && a[swapIndex] > last) {
            swapIndex--;
        }

        for(int i = n -1; i >= swapIndex + 2; i--) {
            a[i] = a[i - 1];
        }
        a[swapIndex+1] = last;

        System.out.println(Arrays.toString(a));
    }
}
