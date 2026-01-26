package numbers;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        long userInputInt = 0;

        System.out.println("Welcome to Amazing Numbers!");


        do {
            printSupportedRequests();
            System.out.print("Enter a request: ");
            String userInput = scanner.next();
            try {
                userInputInt = Long.parseLong(userInput);
            } catch (Exception e) {
                System.out.println("The first parameter should be a natural number or zero.");
            }

            if (userInputInt < 0) {
                System.out.println("The first parameter should be a natural number or zero.");
            } else if (userInputInt == 0) {
                run = false;
            } else {
                System.out.println("\nProperties of " + userInput);
                System.out.println("\t\teven: " + isEven(userInputInt) +
                        "\n\t\t odd: " + isOdd(userInputInt) +
                        "\n\t\tbuzz: " + isBuzz(userInputInt) +
                        "\n\t\tduck: " + isDuck(userInput) +
                        "\npalindromic: " + isPalindrome(userInput) +
                        "\n\t  gapful: " + isGapful(userInput));

            }
        } while (run);

        System.out.println("\nGoodbye!");

    }

    public static void printSupportedRequests() {
        System.out.println("""
                
                Supported requests:\
                
                -enter a natural number to know its properties;\
                
                -enter two natural numbers to obtain the properties of the list:\
                
                  * the first parameter represents a starting number;\
                  
                  * the second parameter shows how many consecutive numbers are to be processed;\
                  
                - separate the parameters with one space;\
                
                - enter 0 to exit.
                """);
    }

    public static boolean isDivisibleBy7(long number) {
        return number % 7 == 0;
    }

    public static boolean endsWith7(long number) {
        return number % 10 == 7;
    }

    public static boolean isBuzz(long number) {
        return isDivisibleBy7(number) || endsWith7(number);
    }

    public static boolean isEven(long number) {
        return number % 2 == 0;
    }

    public static boolean isOdd(long number) {
        return number % 2 == 1;
    }

    public static boolean isDuck(String number) {
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) == '0') {
                return true;
            }
        }

        return false;
    }

    public static boolean isPalindrome(String number) {
        StringBuilder num = new StringBuilder(number);
        BigInteger bigInteger = new BigInteger(num.reverse().toString());

        return bigInteger.equals(new BigInteger(number));
    }

    public static boolean isGapful(String number) {
        if (number.length() < 3) {
            return false;
        }
        String firstAndLast = String.format("%c%c",number.charAt(0), number.charAt(number.length() -1));
        return Integer.parseInt(number) % Integer.parseInt(firstAndLast) == 0;
    }
}
