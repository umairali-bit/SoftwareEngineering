public class Car extends Vehicle {

    String color;
    String model;


    void start() {
        System.out.println("Starting " + this.model);
        System.out.println("The color of the car is " + this.color);

        MotorCycle motorCycle = new MotorCycle();
        motorCycle.breaking(this);
    }

    Car() {
        super(2);

    }



    public static void main(String[] args) {


        Car obj = new Car();
        System.out.println("The car has " + obj.wheelsCount + " wheels");
        obj.wheelsCount = 4;
        obj.model = "Camaro";
        obj.color = "Red";
        obj.start();
        System.out.println(obj.wheelsCount);


    }


}

class MotorCycle {

    void breaking(Car car){
        System.out.println(car.model + " is breaking.");
    }
}
