package loops.BreakAndContinue;

public class Main {

    public static void main(String[] args) {

//  printing even numbers - use of break statement

        for(int i = 0; i < 20; i += 2) {
            System.out.println(i);

            if (i >= 10) {
                break;
            }

        }
//  distributing candies amongst 10 people, skipping number 2 and 5
        for (int j = 1; j < 10; j += 1) {
            if(j == 2 || j == 5) {
                continue;
            }
            System.out.println("candy to " + j);

        }



        }
    }

