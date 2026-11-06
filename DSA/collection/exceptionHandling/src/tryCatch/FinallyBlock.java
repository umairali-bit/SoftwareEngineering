package tryCatch;

public class FinallyBlock {

    public static void main(String[] args) {

        int a[] = new int[5];

        System.out.println("Stay out of my territory");

        try {
            System.out.println(a[8]);
        } catch (Exception e) {
            System.out.println("Exceptions Handled");
        } finally {
            System.out.println("Finally Block");
        }

        System.out.println("Tread lightly");

        try {
            getLength(a);
        } catch (Exception e) {
            System.out.println("Exceptions Handled");
        }



    }

    static int getLength(int[] arr) throws ArithmeticException{
        return arr[8];
    }
}
