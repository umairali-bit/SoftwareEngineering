package methods;

public class Test {
    public static void main(String[] args) {
//        int x = 10;
//        if (x > 5) {
//            int y = x++; }
//        System.out.println(x + " " + y);


        StringBuilder sb = new StringBuilder("Java");
        String result = sb.append("17").substring(1, 5);
        System.out.println(result);
    }
}
