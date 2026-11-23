package fibinocciNumber;

/*
fibinocci series {0,1,1,2,3,5,8,13,21........}
 */

public class Main {

    public static void main(String[] args) {


        System.out.println("Fibonacci serial number: " + fibOfN(6));

    }

    static int fibOfN(int n) {

        if(n <= 1) {
            return n;
        }

        return fibOfN(n-1) + fibOfN(n-2);



    }
}
