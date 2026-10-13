package arrays;

public class MultiDimensionalArray {

    public static void main(String[] args) {

//   2-dimensional array initialization
//   suppose we have 4 students, and we need to store their marks in three subjects
//   There is going to be 4 rows and three columns. rows represents students and columns represents subjects

//        int marks[][] = new int[4][3];

//        marks[0][0] = 58;
//        marks[0][1] = 67;
//        marks[0][2] = 92;
//
//        marks[1][0] = 56;
//        marks[1][1] = 78;
//        marks[1][2] = 89;
//
//        marks[2][0] = 76;
//        marks[2][1] = 52;
//        marks[2][2] = 85;

        int[][] marks = {
                {58,67,92},
                {56,78,89},
                {76,52,85},
                {76,52,85}
        };

        System.out.println(marks[2][2]);

    }
}
