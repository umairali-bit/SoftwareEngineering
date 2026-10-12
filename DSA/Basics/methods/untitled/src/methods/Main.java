package methods;

public class Main {
    public static void main(String[] args) {

        greet();

//      calling a non-static member from a static context
        Main obj = new Main();
        int res = obj.addNumbers(1,2);
        System.out.println(res);


    }

    static void greet() {
        System.out.println("Hello World");
    }

    public int addNumbers(int a, int b) {
        return a + b;
    }
}
