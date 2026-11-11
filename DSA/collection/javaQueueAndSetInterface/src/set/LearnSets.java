package set;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class LearnSets {

    public static void main(String[] args) {

        Set<Integer> hashSet = new HashSet<>();

        hashSet.add(30);
        hashSet.add(10);
        hashSet.add(20);

        System.out.println(hashSet);



        Set<Integer> treeSet = new TreeSet<>();

        treeSet.add(30);
        treeSet.add(10);
        treeSet.add(20);

        System.out.println(treeSet);


        Set<Integer> set = new LinkedHashSet<>();

        set.add(30);
        set.add(10);
        set.add(20);

        System.out.println(set);
    }
}
