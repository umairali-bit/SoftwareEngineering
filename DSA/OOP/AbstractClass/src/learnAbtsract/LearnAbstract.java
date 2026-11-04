package learnAbtsract;

public class LearnAbstract {



    public static void main(String[] args) {

        Car c1 = new Car();

        c1.accelerate();
        c1.brakes(4);
        c1.honks();

    }


}

abstract class vehicle{

    abstract void accelerate();
    abstract int brakes (int wheels);
    void honks() {
        System.out.println("Honks");
    }



}

class Car extends vehicle{


    @Override
    void accelerate() {
        System.out.println("Car is accelerating.....");

    }

    @Override
    int brakes(int wheels) {

        System.out.println("Car is braking.....");

        return wheels;

    }
}
