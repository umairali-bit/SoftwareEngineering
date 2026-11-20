package day2;

public class WithDraw {

    public static void main(String[] args) {

    }

    public double withdraw(double balance, double amount) {

        if (amount <= 0) {
            return -1;
        } else if (amount > balance) {
            return -1;
        }

        return balance - amount;

    }


}
