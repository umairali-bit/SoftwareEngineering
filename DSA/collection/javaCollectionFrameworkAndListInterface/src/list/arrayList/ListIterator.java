package list.arrayList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListIterator {

    public static void main(String[] args) {

        List<String> breakingBadCharacters = new ArrayList<String>();
        breakingBadCharacters.add("Walter White");
        breakingBadCharacters.add("Skyler White");
        breakingBadCharacters.add("Walter Junior");
        breakingBadCharacters.add("Holly White");
        breakingBadCharacters.add("Badger");
        breakingBadCharacters.add("Skinny pete");
        breakingBadCharacters.add("Jessie Pinkman");

        for(int i = 0; i < breakingBadCharacters.size(); i++) {
            System.out.println(breakingBadCharacters.get(i));

        }

        System.out.println("-".repeat(50));

        for (String names:  breakingBadCharacters) {
            System.out.println(names);
        }

        System.out.println("-".repeat(50));

        Iterator<String> bb =  breakingBadCharacters.iterator();
        while (bb.hasNext()) {
            System.out.println(bb.next());
        }

        System.out.println("-".repeat(50));

        List<String> subList = breakingBadCharacters.subList(0, 5);
        System.out.println(subList);


    }
}
