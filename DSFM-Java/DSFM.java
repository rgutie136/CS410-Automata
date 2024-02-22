/*
  Java 17
  Compiler Used: Amazon Correto JDK 17.0
  IDE Used: VS Code

  Remade C++ DSFM Sim with Java
*/
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DSFM {
    public static String removeSeparators(String x) {
        return x.replace(",", "").replace(" ", "").replace("(", "").replace(")", "");
    }

    public static int findIndex(String a, char c) {
        int index = a.indexOf(c);
        return index != -1 ? index : -1;
    }

    public static void main(String[] args) {
        try {
            Scanner sim = new Scanner(new File("dsfm.txt"));

            // Grab Alphabet language
            String alphabet = removeSeparators(sim.nextLine());
            int numL = alphabet.length();
            char[] L = alphabet.toCharArray();
            int numLang = L.length;

            // Get num states
            int numStates = Integer.parseInt(sim.nextLine().trim());

            // Get accepting states
            String strAccept = removeSeparators(sim.nextLine());
            boolean[] A = new boolean[10];
            Arrays.fill(A, false);
            for (int i = 0; i < strAccept.length(); i++) {
                char k = strAccept.charAt(i);
                int indexAccept = strAccept.indexOf(k);
                if (indexAccept != -1) {
                    A[i] = true;
                }
            }

            // Transitions
            char[][] goTo = new char[numStates][numL];
            int state = 0;
            int lang = 0;
            for (int i = 0; i < numStates; i++) {
                int point = 2;
                String temp = removeSeparators(sim.nextLine());
                for (int j = 0; j < numLang; j++) {
                    char nextState = temp.charAt(point);
                    goTo[state][lang] = nextState;
                    point = point + 3;
                    lang++;
                }
                lang = 0;
                state++;
            }
            sim.close();

            // User interaction
            Scanner inputScanner = new Scanner(System.in);
            char retry;
            do {
                String input;
                int currentState = 0;
                boolean valid = true;
                boolean accepted = false;
                int index;
                System.out.println("Enter a string using alphabet");
                input = inputScanner.nextLine();
                int sizeInput = input.length();
                for (int i = 0; i < sizeInput; i++) {
                    char test = input.charAt(i);
                    index = findIndex(alphabet, test);
                    if (index != -1) {
                        currentState = goTo[currentState][index] - '0';
                        System.out.println("\nStep: " + currentState);
                    } else {
                        System.out.println("\nError certain characters not in Language");
                        valid = false;
                        break;
                    }
                }
                accepted = A[currentState];

                if (accepted && valid) {
                    System.out.println("\nYour string was accepted!");
                } else {
                    System.out.println("\nYour string was NOT accepted!");
                }
                // Prompt user to repeat program
                System.out.println("Do you want to try another string? (Y/N)");
                retry = inputScanner.nextLine().charAt(0);
                System.out.println("\n\n\n");
            } while (retry == 'y' || retry == 'Y');
        } catch (FileNotFoundException e) {
            System.out.println("Failed to retrieve file. Try again (Make sure file is \"testRun.txt\")\nPress ENTER to exit...");
            new Scanner(System.in).nextLine();
        }
    }
}