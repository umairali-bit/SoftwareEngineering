package day5;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Coding {


    public static void main(String[] args) {

        int[] transactionIds = {101,102,103,101,104,102};
        System.out.println(findDuplicateTransaction(transactionIds));
    }

    /*

   Write a Java method named findDuplicateTransactions() that:
- Accepts an array of transaction IDs.
- Identifies all duplicate transaction IDs.
- Returns each duplicate ID only once.
- Handles an empty array.
     */

    static List<Integer> findDuplicateTransaction(int[] arr) {
        HashSet<Integer> seen = new HashSet<>();
        HashSet<Integer> duplicate = new HashSet<>();

        for(int num : arr){
            if(!seen.add(num)){
                duplicate.add(num);
            }

        }

        return new ArrayList<>(duplicate);
    }

    // the time complexity of HashSet is O(n) and space is O(n) since its using only 1 variable

}
