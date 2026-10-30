public class Vehicle {

        int wheelsCount;

        Vehicle() {
            System.out.println("Creating a Vehicle");
        }

        Vehicle(int wheelsCount) {
            this.wheelsCount = wheelsCount;
        }

        void start() {
            System.out.println("Vehicle started");
        }



}
