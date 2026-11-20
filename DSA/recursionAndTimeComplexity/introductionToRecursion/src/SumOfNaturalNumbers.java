public class SumOfNaturalNumbers {

    public static void main(String[] args) {

        int[] arr = {1,2,3,4,5};

        int sum = 0;
        for (int i = 0; i< arr.length; i++){
            sum += arr[i];
        }

        System.out.println(sum);

        System.out.println(sumOfInt(5));



    }

    //recursion
    static int sumOfInt(int n) {

        if (n == 1) return 1;
        else return sumOfInt(n-1) + n;

    }
}
