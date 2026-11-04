package learnInnerClass;

public class LearnInnerClass {

    class Toy {

        int price;



    }

    static class Play {

        int hours;
    }



    public static void main(String[] args) {

        LearnInnerClass learnInnerClass = new LearnInnerClass();
        Toy toy = learnInnerClass.new Toy();
        toy.price = 100;

        Play play = new Play();
        play.hours = 100;
        System.out.println(play.hours);




    }
}
