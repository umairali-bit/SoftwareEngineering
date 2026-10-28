package arrays.sorting;

import java.util.Arrays;

public class SelectionSort {

    public static void main(String[] args) {

        int[] a = {4, 1, 8, 2, 3, 7};

        SelectionSort sort = new SelectionSort();
        sort.selectionSort(a);
        System.out.println(Arrays.toString(a));


    }

    public int[] selectionSort(int[] a) {

        int n = a.length;

        for (int i = 0; i < n - 1; i++) {

            int min = i; //min number in the ith index

            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }

            int temp = a[i];
            a[i] = a[min];
            a[min] = temp;


        }
        return a;
    }
}


