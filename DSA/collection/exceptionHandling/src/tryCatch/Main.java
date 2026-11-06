package tryCatch;

public class Main {

    public static void main(String[] args) {

        int a[] = new int[5];

        System.out.println("I am not in danger");

        try {

            int result = 5 / 0;
            System.out.println(a[8]);


//        } catch (ArrayIndexOutOfBoundsException e) {
//            System.out.println("Tried to access the out of the bound element");
//        } catch (ArithmeticException e) {
//            System.out.println(e.getStackTrace());
//            System.out.println(e.getMessage());
//            System.out.println(e.toString());
//        }

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("I am the danger");






    }
}
