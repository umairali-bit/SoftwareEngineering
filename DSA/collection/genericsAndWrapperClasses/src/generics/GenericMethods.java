package generics;

public class GenericMethods {

    public static void main(String[] args) {

        printData("Say my name");
        printData(401234);

    }

//    static void printData(String data) {
//        System.out.println(data);
//    }

    static<T> void printData(T data){
        System.out.println(data);
    }
}
