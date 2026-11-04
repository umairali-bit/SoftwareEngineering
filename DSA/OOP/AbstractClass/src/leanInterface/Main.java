package leanInterface;

interface Animals {

    void animalEats();
}

class Monkey implements Animals {
    @Override
    public void animalEats() {
        System.out.println("Monkey is an animal that eats");
    }
}

public class Main {
    public static void main(String[] args) {
        Animals animal = new Monkey();
        animal.animalEats();
    }
}


