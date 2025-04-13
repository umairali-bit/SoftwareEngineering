package dev;
import java.util.HashSet;
import java.util.Set;

public class lpa {

    public static void main(String[] args) {

        int[] nums = {3,2,4};
        int target = 6;

        int[] result = findTwoSum(nums, target);
        if (result.length == 2) {
            System.out.println("Pair found: " + result[0] + " " + result[1]);
        } else {
            System.out.println("No pair found");
        }

    }

    public static int[] findTwoSum (int[] nums, int target) {

        Set<Integer> seen = new HashSet<>();

        for (int num : nums) {

            int complement = target - num;

            if (seen.contains(complement)) {
                return new int[] {complement, num};//2,4
            }

            seen.add(num);

        }
        return new int[] {};

    }
}
