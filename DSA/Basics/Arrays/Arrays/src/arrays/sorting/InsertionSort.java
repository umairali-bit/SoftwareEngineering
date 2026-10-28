package arrays.sorting;

import java.util.Arrays;

public class InsertionSort {

    public static void main(String[] args) {

        int[] a = {4,1,8,2,3,7};
        InsertionSort sort = new InsertionSort();
        sort.insertionSort(a);
        System.out.println(Arrays.toString(a));


    }

    public int[] insertionSort(int[] arr) {

        int n =  arr.length;

        for(int i = 1; i < n; i++){

            int last = arr[i];
            int swapIndex = i - 1;

            while(swapIndex >= 0 && arr[swapIndex] > last){
                swapIndex--;
            }

            int j = i;
            while(j > swapIndex + 1) {
                arr[j] = arr[j-1];
                j--;
            }
            arr[swapIndex + 1] = last;
        }
        return arr;

    }
}
