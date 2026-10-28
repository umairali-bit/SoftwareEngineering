package arrays.sorting;

import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {

//  Bubble Sort

        int[] a = { 9, 5, 4, 3, 1 };
        BubbleSort bSort = new BubbleSort();
        bSort.bubbleSort(a);
        System.out.println(Arrays.toString(a));

    }

    public int[] bubbleSort(int[] a) {

        int n = a.length;
        for (int i = 0; i< n-1; i++){

            boolean didISwap = false;

            for (int j = 0; j < n - i -1; j++) {
                if (a[j] > a[j+1]) {
                    int temp = a[j + 1];
                    a[j + 1] = a[j];
                    a[j] = temp;

                    didISwap = true;

                }
            }
            if(!didISwap) break;

        }
        return a;


    }
}
