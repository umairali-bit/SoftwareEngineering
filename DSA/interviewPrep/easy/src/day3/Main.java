package day3;

/*

Write a Java method named calculateInterest() that accepts three parameters:
- double balance
- double annualInterestRate
- int years
The method must reject negative balances, negative interest rates, and zero or negative years.

For valid inputs, calculate and return the total simple interest earned over the specified years.
 */

public class Main {

    public static void main(String[] args) {

        Main m = new Main();
        double res = m.calculateInterest(50, 0.2, 2);
        System.out.println("The interest is " + res);

    }

    public double calculateInterest(double balance, double annualInterest, int years) {

        if (balance < 0 || annualInterest < 0 || years <= 0 ) {
            throw new IllegalArgumentException("Invalid input");
        }

        return balance * annualInterest * years;

    }
}
