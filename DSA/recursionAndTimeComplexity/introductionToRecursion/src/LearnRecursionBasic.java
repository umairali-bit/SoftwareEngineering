public class LearnRecursionBasic {

    public static void main(String[] args) {

        LearnRecursionBasic obj = new LearnRecursionBasic();
        obj.fun(4);

    }

    void fun(int n) {

        if (n > 0) { //base case

            fun(n - 1); //recursion

            System.out.println("I did it for myself - " + n); //descending



        }

    }
}
