package wrapperClass;

public class WrapperClass {

    public static void main(String[] args) {

//        Integer obj = new Integer(12); this way is depreciated

        Integer obj2 = Integer.valueOf(12);

        Integer obj3 = 12; //autoboxing

        int age = obj3; //unboxing

//        uses of wrappers -> used in list, arrays, collection framework

        //it can also convert a string into int example
        Integer obj4 = Integer.valueOf("16");
        System.out.println(obj4);



    }
}
