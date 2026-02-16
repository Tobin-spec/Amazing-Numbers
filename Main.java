package numbers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    final static String[] availableProperties = new String[]{"EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        long userInputInt = 0;
        String[] userInputs;
        String[] params = new String[12];



        System.out.println("Welcome to Amazing Numbers!");
        printSupportedRequests();

        do {
            System.out.print("\nEnter a request: ");
            String userInput = scanner.nextLine();
            if (!(userInput.isBlank())) {
                userInputs = userInput.split(" ");
                updateParams(params, userInputs);

                try {
                    userInputInt = Long.parseLong(params[0]);
                } catch (Exception e) {
                    System.out.println("The first parameter should be a natural number or zero.");
                    continue;
                }
            } else {
                printSupportedRequests();
                continue;
            }


            if (userInputInt < 0) {
                System.out.println("The first parameter should be a natural number or zero.");
            } else if (userInputInt == 0) {
                run = false;
            } else if (!isNatural(params[1])) {
                System.out.println("The second parameter should be a natural number.");
            } else if (!isValidProperties(params)) {
                System.out.println("Available properties: " + Arrays.toString(availableProperties));
            } else if (isMutuallyExclusive(params)) {
                System.out.println("There are no numbers with these properties.");
            } else {
                printProperties(params);
            }
        } while (run);

        scanner.close();
        System.out.println("\nGoodbye!");

    }

    public static void printSupportedRequests() {
        System.out.println("""
                
                Supported requests:\
                
                -enter a natural number to know its properties;\
                
                -enter two natural numbers to obtain the properties of the list:\
                
                  * the first parameter represents a starting number;\
                  
                  * the second parameter shows how many consecutive numbers are to be printed;\
                  
                - two natural numbers and properties to search for;\
                
                - a property preceded by minus must not be present in numbers;\
                  
                - separate the parameters with one space;\
                
                - enter 0 to exit.
                """);
    }

    public static void updateParams(String[] params, String[] userInputs) {
        params[0] = userInputs[0];

        try {
            params[1] = userInputs[1];
        } catch (Exception e) {
            params[1] = "0";
        }

        for (int i = 2; i < params.length; i++) {
            try {
                params[i] = userInputs[i].toUpperCase();
            } catch (Exception e) {
                params[i] = "";
            }
        }
    }

    public static boolean isNatural(String aString) {
        try {
            int num = Integer.parseInt(aString);
            if (num < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static boolean isValidProperties(String[] params) {
        for (int i = 2; i < params.length && !("").equals(params[i]); i++) {
            if (!Arrays.toString(availableProperties).contains(params[i]) && !Arrays.toString(availableProperties).contains(params[i].replace("-", ""))) {
                System.out.printf("The property [%s] is wrong.%n", params[i]);
                return false;
            }
        }

        return true;
    }

    public static void printProperties(String[] userInputs) {
        long firstParam = Long.parseLong(userInputs[0]);
        int secondParam = Integer.parseInt(userInputs[1]);
        String[] properties = new String[10];

        for (int i = 0; i < properties.length; i++) {
            properties[i] = userInputs[i + 2].toLowerCase();
        }
        String thirdParam = userInputs[2].toLowerCase();
        String fourthParam = userInputs[3].toLowerCase();

        if (secondParam != 0 && properties[0].isEmpty()) {
            for (long i = firstParam; i < firstParam + secondParam; i++) {
                System.out.println(getProperties(i));
            }
        } else if (secondParam != 0 && properties[1].isEmpty()) {
            for (long i = firstParam; secondParam != 0; i++) {
                String result = getProperties(i);
                if(properties[0].contains("-")) {
                    if (doesNotContainExcludedProperties(result, properties)) {
                        System.out.println(result);
                        secondParam--;
                    }
                }else if (result.contains(properties[0])) {
                    System.out.println(result);
                    secondParam--;
                }
            }
        } else if (secondParam != 0 && !properties[1].isBlank()) {
            for (long i = firstParam; secondParam != 0; i++) {
                String result = getProperties(i);
                if (Arrays.toString(properties).contains("-")) {
                    if (doesNotContainExcludedProperties(result, properties) && containsProperties(result, properties)) {
                        System.out.println(result);
                        secondParam--;
                    }
                }else if (containsProperties(result, properties)) {
                    System.out.println(result);
                    secondParam--;
                }
            }
        } else {
            System.out.println("\nProperties of " + firstParam);
            System.out.println("\t\teven: " + isEven(firstParam) +
                    "\n\t\t odd: " + isOdd(firstParam) +
                    "\n\t\tbuzz: " + isBuzz(firstParam) +
                    "\n\t\tduck: " + isDuck(Long.toString(firstParam)) +
                    "\npalindromic: " + isPalindrome(Long.toString(firstParam)) +
                    "\n\t  square: " + isSquare(firstParam) +
                    "\n\t   sunny: " + isSunny(firstParam) +
                    "\n\t\t spy: " + isSpy(Long.toString(firstParam)) +
                    "\n\t jumping: " + isJumping(Long.toString(firstParam)) +
                    "\n\t  gapful: " + isGapful(Long.toString(firstParam)) +
                    "\n\t   happy: " + isHappy(Long.toString(firstParam)) +
                    "\n\t\t sad: " + !isHappy(Long.toString(firstParam)));
        }
    }

    public static String getProperties(Long num) {
        String even = isEven(num) ? "even" : "";
        String odd = isOdd(num) ? "odd" : "";
        String buzz = isBuzz(num) ? "buzz, " : "";
        String duck = isDuck(Long.toString(num)) ? "duck, " : "";
        String gapful = isGapful(Long.toString(num)) ? "gapful, " : "";
        String palindromic = isPalindrome(Long.toString(num)) ? "palindromic, " : "";
        String spy = isSpy(Long.toString(num)) ? "spy, " : "";
        String sunny = isSunny(num) ? "sunny, " : "";
        String square = isSquare(num) ? "square, " : "";
        String jumping = isJumping(Long.toString(num)) ? "jumping, " : "";
        String happy = isHappy(Long.toString(num)) ? "happy, " : "sad, ";


        return num + " is " + buzz + palindromic + duck + gapful + spy + square + sunny + jumping + happy + even + odd;
    }

    public static boolean containsProperties(String result, String[] properties) {
        int noOfProperties = 0, count = 0;
        String propertiesArrayString = Arrays.toString(properties);


        for (int i = 0; i < properties.length && !properties[i].isEmpty() && !properties[i].contains("-"); i++) {
            noOfProperties++;
        }

        for (int i = 0; i < properties.length && !properties[i].isEmpty(); i++) {
            if (result.contains(properties[i])) {
                count++;
            }
        }

        return noOfProperties == count;
    }

    public static boolean doesNotContainExcludedProperties(String result, String[] properties) {
        for (int i = 0; i < properties.length && !properties[i].isEmpty(); i++) {
            if (properties[i].contains("-") && result.contains(properties[i].replace("-", ""))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isMutuallyExclusive(String[] params) {
        String paramsArray = Arrays.toString(params);


        if (paramsArray.contains("-EVEN") && paramsArray.contains("-ODD")) {
            System.out.println("The request contains mutually exclusive properties: [-EVEN, -ODD]");
            return true;
        } else if (paramsArray.contains("DUCK") && paramsArray.contains("SPY")) {
            System.out.println("The request contains mutually exclusive properties: [DUCK, SPY]");
            return true;
        } else if ((paramsArray.contains("SUNNY") && paramsArray.contains("SQUARE")) && (!paramsArray.contains("-SUNNY") && !paramsArray.contains("-SQUARE"))) {
            System.out.println("The request contains mutually exclusive properties: [SUNNY, SQUARE]");
            return true;
        } else if (paramsArray.contains("-HAPPY") && paramsArray.contains("-SAD")) {
            System.out.println("The request contains mutually exclusive properties: [-HAPPY, -SAD]");
            return true;
        } else if (paramsArray.contains("HAPPY") && paramsArray.contains("SAD")) {
            System.out.println("The request contains mutually exclusive properties: [HAPPY, SAD]");
            return true;
        } else if (paramsArray.contains("EVEN") && paramsArray.contains("ODD")) {
            System.out.println("The request contains mutually exclusive properties: [EVEN, ODD]");
            return true;
        }

        for (String param: params) {
            if (!param.isBlank() && Arrays.toString(params).contains(param) && Arrays.toString(params).contains("-" + param)) {
                System.out.printf("The request contains mutually exclusive properties: [%s, -%s]%n", param, param);
                return true;
            }
        }

        return false;
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
        return Long.parseLong(number) % Integer.parseInt(firstAndLast) == 0;
    }

    public static boolean isSpy(String number) {
        int sum = 0;
        int product = 1;

        for (int i = 0; i < number.length(); i++) {
            sum += number.charAt(i) - '0';
            product *= number.charAt(i) - '0';
        }

        return sum == product;
    }

    public static boolean isSquare(long number) {
        return Math.sqrt(number) % 1 == 0;
    }

    public static boolean isSunny(long number) {
        return isSquare(number + 1);
    }

    public static boolean isJumping(String number) {
        for (int i = 0; i < (number.length()) / 2; i++) {
            int currNum = number.charAt(i) - '0';
            int adjacentNum = number.charAt(i + 1) - '0';

            int tailNum = number.charAt((number.length() - 1) - i) - '0';
            int tailAdjacent = number.charAt((number.length() - 1) - (i + 1)) - '0';

            if (Math.abs(currNum - adjacentNum) != 1 || Math.abs(tailNum - tailAdjacent) != 1) {
                return false;
            }
        }

        return true;
    }

    public static boolean isHappy(String number) {
        int sum = 0;
        String currNum = number;
        Set<String> seenNumbers = new HashSet<>();

        do {
            sum = 0;
            seenNumbers.add(currNum);
            for (int i = 0; i < currNum.length(); i++) {
                int i1 = Integer.parseInt(String.valueOf(currNum.charAt(i)));
                sum += i1 * i1;
            }
            currNum = Integer.toString(sum);

        } while (sum != 1 && !seenNumbers.contains(Integer.toString(sum)));

        return sum == 1;
    }
}
