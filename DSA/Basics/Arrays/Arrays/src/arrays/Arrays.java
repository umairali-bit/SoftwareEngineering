package arrays;

public class Arrays {

    public static void main(String[] args) {

//        declaring an array

        int intArray[];
        int[]  intArray2;


//    allocating space in the memory
        intArray = new int[20];

//    declaration and allocation in the same line
//    entering 5 numbers in the age[] by using for loop

        int age[] = new int[5];

        for (int i = 0; i < 5; i++) {
            age[i] = i + 1 ;
        }

        System.out.println(age[2]);
        System.out.println(java.util.Arrays.toString(age));
        System.out.println(age.length);


        int marks[] = {98,56,12,67};
        System.out.println(marks[2]);

//        foreach loop

        String names[] = {"Walter", "Hank", "Gustavo", "Todd","Mike"};

        for(String name : names){
            System.out.println(name);
        }






    }
}
