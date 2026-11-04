package classesAndObjects;

public class Constructors {

    public static void main(String[] args) {

        Complexity num = new Complexity(2,3);
        num.print();

    }
}

class Complexity {
    int a;
    int b;
   public Complexity(int a, int b) {
        this.a = a;
        this.b = b;
    }

    void print() {
        System.out.println(a + b);
    }
}
