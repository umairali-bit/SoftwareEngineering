package palindrome;
/*
aba is a palindrome
 */
public class Main {

    public static void main(String[] args) {

        String str = "abcba";
        System.out.println(isPalindrome(str));


    }

    static boolean isPalindrome(String str) {

        return palindromeHelper(str, 0, str.length()-1);
    }

    static boolean palindromeHelper(String s, int l, int r) {

        if (l >= r) {
            return true;
        }

        if(s.charAt(l) == s.charAt(r)) {
            return palindromeHelper(s, l+1, r-1);
        }
        return false;

    }
}
