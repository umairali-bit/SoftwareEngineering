package learnAbtsract;

public class Abstract {

    Car c1 = new Car();

    c1.accelrate();
}

abstract class vehicle{

    abstract void accelerate();
    abstract int brakes (int wheels);



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
