package deque;

import java.util.ArrayDeque;
import java.util.Deque;

public class LearnDeque {

    public static void main(String[] args) {

        Deque<Integer> dq = new ArrayDeque<>();

        dq.offer(20);
        dq.offerLast(10);
        dq.offerFirst(30);

        System.out.println(dq);

        System.out.println(dq.poll());
        System.out.println(dq.pollLast());

        System.out.println(dq);



    }
}
