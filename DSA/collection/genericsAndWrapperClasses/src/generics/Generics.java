package generics;

public class Generics {

    public static void main(String[] args) {

        Animal animal = new Animal("40", "Rockey");
        System.out.println(animal.id + " " + animal.name);

        Animal<Integer, String> animal1 = new Animal<>(40, "Sabastian");
        System.out.println(animal1.id + " " + animal1.name);

        System.out.println(animal.getId());







    }
}

class Animal<T, V> {

    T id;
    V name;

    public Animal(T id, V name) {
        this.id = id;
        this.name = name;
    }

    T getId() {
        return id;
    }
}
