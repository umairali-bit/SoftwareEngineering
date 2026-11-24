package josephusProblem;

public class Main {

    public static void main(String[] args) {

        System.out.println(josephusProblem(5,3));


    }

    static int josephusProblem(int n, int k){

//        base case
        if (n == 1) return 0;
        return (josephusProblem(n - 1, k ) + k) % n;


    }
}
