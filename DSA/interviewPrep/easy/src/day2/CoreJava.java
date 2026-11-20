package day2;

public class CoreJava {

    /*

    What is the difference between == and .equals() in Java? Explain how they behave
    when comparing two String objects.

    == checks if two references are pointing to the same object
    .equals() compares if the stored value in those 2 object are logically equal (same data)





     */

    public static void main(String[] args) {


        String a = "My name is Walter White";
        String b = "My name is Walter White";
        String c = new String ("My name is Walter White");

        String s1 = "hello";
        String s2 = new String("hello");

        System.out.println(a == b); //true
        System.out.println(a.equals(b));//true
        System.out.println(a == c);//false

        System.out.println(s1 == s2);// false
        System.out.println(s1.equals(s2));//true
    }
}
