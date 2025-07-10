package math.primenumbers;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DialogSequence{
  Scanner scanner;

  public DialogSequence(){
    scanner = new Scanner(System.in);
    executeSequence();
  }

  public void executeSequence(){
    choosePrimeOperationDialog();
  }

  public void clearDialog(){
    scanner.nextLine();
    System.out.println("Press enter to try again.");
    try {
      String os = System.getProperty("os.name").toLowerCase();

      if (os.contains("windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        new ProcessBuilder("clear").inheritIO().start().waitFor();
      }
    } catch (Exception e) {
      for (int i = 0; i < 20; i++) System.out.println();
    }
  }

  private void choosePrimeOperationDialog(){
    System.out.println("What would you like to check?");
    System.out.println("-----------------------------");
    System.out.println("1. Check if a number is prime.");
    System.out.println("2. Check how many primes there are up to and including a number (looped miller-rabin test).");
    System.out.println("3. Sift through all prime numbers up to a number.");
    int answer = 0;
    while(answer==0){
      try {
        answer = scanner.nextInt();
        if(answer>0 && answer<4){
          choosePrimeOperation(answer);
        } else {
          throw new InputMismatchException();
        }
      } catch (InputMismatchException e) {
        System.err.println("Please choose a valid number of a given option.");
        e.printStackTrace();
        System.err.println("");
        clearDialog();
      } catch (IllegalStateException | NoSuchElementException e) {
        System.err.println("The command line is not accepted, please open a command line tool with a acceptable System.in line.");
        e.printStackTrace();
        System.err.println("");
        clearDialog();
      }
    }
  }

  private void choosePrimeOperation(int choice){
    switch (choice) {
      case 1:
        isPrimeSequence();
        break;
      case 2:
        PrimeNumber.sumOfPrimeNumbers(enterNumberDialog("Enter the number to check for prime:"));
        break;
      case 3:
        break;
      default:
        throw new AssertionError();
    }
  }

  private void isPrimeSequence(){
    long numberToCheck = enterNumberDialog("Enter the number to check for prime:");
    System.out.println("Your number "+ numberToCheck + "is" + (PrimeNumber.isPrime(numberToCheck)?" ":" not ") + "a prime number");
  }

  private long enterNumberDialog(String msg) throws IllegalStateException{
    boolean isCorrect = false;
    while(!isCorrect){
      System.out.println(msg);
      try {
        long input = scanner.nextLong();
        isCorrect = !isCorrect;
        return input;
      } catch (Exception e) {
        System.err.println("Please enter a valid 64bit non-floating point number.");
        e.printStackTrace();
        clearDialog();
      }
    }
    throw new IllegalStateException("Critical Error: while-loop was not entered correctly. Please restart the program and report this error.");
  }

}
