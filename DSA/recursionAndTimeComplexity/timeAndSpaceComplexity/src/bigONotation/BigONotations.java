package bigONotation;

public class BigONotations {

    public static void main(String[] args) {

        int[] arr = {3,7,1,7,23,5};
        int key = 10;

        int res = getFirstElement(arr);
        System.out.println(res);


        boolean result = checkIfNumberIsPresent(arr,key);
        System.out.println(result);



    }

//    0(1)
    static int getFirstElement(int[] arr) {
       return arr[0];
    }

//    0(n)
    static boolean checkIfNumberIsPresent(int[] arr, int key) {

        for (int element : arr) {
            if (element == key) {
                return true;
            }
        }
        return false;
    }
}
