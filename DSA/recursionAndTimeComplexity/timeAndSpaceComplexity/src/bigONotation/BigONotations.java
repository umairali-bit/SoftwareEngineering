package bigONotation;

public class BigONotations {

    public static void main(String[] args) {

        int[] arr = {3,7,1,7,23,5};
        int key = 10;

        int res = getFirstElement(arr);
        System.out.println(res);

        System.out.println("-".repeat(50));


        boolean result = checkIfNumberIsPresent(arr,key);
        System.out.println(result);

        System.out.println("-".repeat(50));

        printPairsInArray(arr);



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


//    0(n^2)
    static void printPairsInArray(int[] arr) {
        for (int i = 0; i< arr.length; i++) {

            for(int j = i + 1; j < arr.length; j++) {
                System.out.println(arr[i] + "  " + arr[j]);
            }
        }
    }
}
