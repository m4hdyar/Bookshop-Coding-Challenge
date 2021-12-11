package mainpack;

import java.util.Scanner;

public class BookshopsSystem {

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new BookshopsSystem();
        System.out.println("Exit...");
    }

    public BookshopsSystem() {

        scanner.useDelimiter("");

        char charUserChoice = 0;
        while(charUserChoice != 'q') {
            printMainMenu();

            String userInput = scanner.nextLine();
            if(userInput.length() <= 0){
                System.out.println("Wrong Input! Try Again.");
                continue;
            }
            charUserChoice = userInput.charAt(0);
            doMainCommand(charUserChoice);
        }



    }

    private void doMainCommand(char charUserChoice) {
    }

    private static void printMainMenu() {
        System.out.println("Bookshops System 1.0");
        System.out.println("------------------------------");
        System.out.println("[q] - quit program");
        System.out.println("Enter your choice: ");

    }
}
