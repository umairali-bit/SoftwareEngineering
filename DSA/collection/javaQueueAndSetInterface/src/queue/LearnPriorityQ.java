package queue;

import java.util.PriorityQueue;
import java.util.Queue;

public class LearnPriorityQ {
    public static void main(String[] args) {

        Queue<Integer>  q = new PriorityQueue<>();

        q.add(3);
        q.add(2);
        q.add(7);
        q.add(5);
        q.add(4);

        System.out.println(q);
        System.out.println(q.poll());
        System.out.println(q);
        System.out.println(q.peek());
        System.out.println(q);



    }
}
