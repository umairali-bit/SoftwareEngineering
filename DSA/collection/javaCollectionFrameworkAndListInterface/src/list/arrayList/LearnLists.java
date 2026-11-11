package list.arrayList;

import java.util.ArrayList;
import java.util.List;

public class LearnLists {

    public static void main(String[] args) {

//        int arr[] = new int[10];


//  Arraylist is implementing List, they are dynamic in size
        List<Integer> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();

        list.add(10);
        list.add(20);
        list.add(30);

        list1.add(1);
        list1.add(2);
        list1.add(4);

        System.out.println(list);
        System.out.println(list.size());
        System.out.println(list.contains(10));

//        removing an element on index bases
        System.out.println(list.remove(2));
        System.out.println(list);

//        removing the element as an object
        System.out.println(list.remove(Integer.valueOf(20)));
        System.out.println(list);

//        adding list 1 in list
        list.addAll(list1);
        System.out.println(list);


        System.out.println(list.get(1));
        list.set(1,200);
        System.out.println(list);


        System.out.println(list.indexOf(200));



    }
}
