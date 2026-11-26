package day4;

/*
Write a Java method named calculateTransferFee() that accepts:
- double balance
- double transferAmount
Business requirements:
- Reject negative balances and zero or negative transfer amounts.
- Calculate a transfer fee of 2% of the transfer amount.
- Reject the transaction if the balance is insufficient to cover both the transfer amount and the fee.
- Return the remaining account balance after deducting both the transfer amount and the fee.
 */

public class Coding {

    public static void main(String[] args) {


        System.out.println(calculateTransferFee(50, 10));


    }

    public static double calculateTransferFee(double balance, double transferAmount) {


        if (balance < 0 || transferAmount <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        double transferFee = (0.02 * transferAmount);

        if (balance < transferFee + transferAmount){
            throw new IllegalStateException("Insufficient funds");
        }

        return balance - (transferAmount + transferFee);
    }
}
