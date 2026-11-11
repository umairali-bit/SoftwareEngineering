package stack;

import java.util.ArrayDeque;

public class LearnStack {

    public static void main(String[] args) {

        ArrayDeque<Integer> stack = new ArrayDeque<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        System.out.println(stack);

        System.out.println(stack.pop());

//        queue
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.offer(5);
        q.offer(6);
        q.offer(7);
        q.offer(8);

        System.out.println(q);
        System.out.println(q.poll());



    }


}
