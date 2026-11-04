package toString;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        Car c1 = new Car("Dodge", 2019);
        Car c2 = new Car("Dodge", 2019);
        System.out.println(c1);

        System.out.println(c1.equals(c2));

    }
}

class Car {

    String model;
    int year;

    public Car(String model, int year) {
        this.model = model;
        this.year = year;
    }

    public String toString() {
        return model + " " + year;
    }


    public boolean equals(Object o) {
        if (this.model.equals(((Car) o).model) && this.year == ((Car) o).year) {
            return true;
        }
        return false;

    }

    @Override
    public int hashCode() {
        return Objects.hash(model, year);
    }
}
