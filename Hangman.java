/**
 * CS312 Assignment 5
 *
 * On my honor, <Meghal Patel>, this programming assignment is my own work
 * and I have not shared my solution with any other student in the class.
 *
 * A program to play Hangman.
 *
 * Email address: meghalp012@gmail.com
 * UTEID: mrp3937
 * TA: Minvera
 * Slip days used: 1 
 */

import java.util.Scanner;

public class Hangman {

    public static final int MAX_NUM_GUESSES = 5;

    public static void main(String[] args) {
        PhraseBank phrases = buildPhraseBank(args);
        Scanner keyboard = new Scanner(System.in);
        boolean playAgain = true;
        intro(); // beginning intros
        playRounds(keyboard, phrases, playAgain); // runs and executes bulk of code
        keyboard.close();
    }

    // runs most of the rounds while calling the other methods so main can be concise 
    public static void playRounds(Scanner keyboard, PhraseBank phrases, boolean playAgain) {
        String topic = phrases.getTopic(); 

        while (playAgain) { // runs until the user doesn't want to play
            System.out.println("\nI am thinking of a " + topic + " ...");
            String secretPhrase = phrases.getNextPhrase();
            String maskedPhrase = maskPhrase(secretPhrase); 
            String notGuessed = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            int wrongGuesses = 0;

            // runs until user is less than 5 guesses and still has * in his phrase
            while (wrongGuesses < MAX_NUM_GUESSES && maskedPhrase.contains("*")) {
                displayRoundStatus(maskedPhrase, notGuessed); 
                char guess = getValidGuess(keyboard, notGuessed);

                if (secretPhrase.indexOf(guess) != -1) { // the user got a right guess
                    maskedPhrase = updateMaskedPhrase(secretPhrase, maskedPhrase, guess);
                    displayCorrectGuess(guess);
                } else {                         // the user got an incorrect guess
                    wrongGuesses++;            // counts the number of time user got a wrong guess
                    displayIncorrectGuess(guess);
                }
                
                System.out.println("Number of wrong guesses so far: " + wrongGuesses);
                notGuessed = removeFromNotGuessed(notGuessed, guess); 

                if (!maskedPhrase.contains("*")) { // user has no more * and wins
                    System.out.println("The phrase is " + secretPhrase + "." + "\nYou win!!");
                }
            }

            if (maskedPhrase.contains("*")) { // there is still * left meaning he lost 
                System.out.println("You lose. The secret phrase was " + secretPhrase);
            }

            playAgain = askToPlayAgain(keyboard);
        }
    }

    private static PhraseBank buildPhraseBank(String[] args) {
        PhraseBank result;
        if (args == null || args.length == 0
                || args[0] == null || args[0].length() == 0) {
            result = new PhraseBank();
        } else {
            result = new PhraseBank(args[0]);
        }
        return result;
    }

    public static void intro() { // begenning intros to the game
        System.out.println("This program plays the game of hangman.");
        System.out.println();
        System.out.println("The computer will pick a random phrase.");
        System.out.println("Enter letters for your guess.");
        System.out.println("After 5 wrong guesses you lose.");
    }

    // calls the phrase and turns it into * and _ for spaces
    public static String maskPhrase(String phrase) {
        String masked = "";
        for (int i = 0; i < phrase.length(); i++) { // runs for the length of the phrase
            char currentChar = phrase.charAt(i);
            if (Character.isLetter(currentChar)) { // if it is a letter converts to *
                masked += '*';
            } else if (currentChar == ' ') { // if it is a space converts to _
                masked += '_';
            }
        }
        return masked;
    }

    // displays what the user has left of the phrase and what he has not guessed yet 
    public static void displayRoundStatus(String maskedPhrase, String notGuessed) {
        System.out.println("\nThe current phrase is " + maskedPhrase);
        System.out.println();
        System.out.println("The letters you have not guessed yet are:\n" + 
        formatNotGuessed(notGuessed));
        System.out.println();
    }

    // shows what they have guessed when it is correct
    public static void displayCorrectGuess(char guess){
        System.out.println();
        System.out.println("You guessed: " + guess);
        System.out.println();
        System.out.println("That is present in the secret phrase.");
        System.out.println();
    }

    // shows what they have guessed when incorrect 
    public static void displayIncorrectGuess(char guess){
        System.out.println();
        System.out.println("You guessed: " + guess);
        System.out.println();
        System.out.println("That is not present in the secret phrase.");
        System.out.println();
    }

    // updates and formats what the user has not guessed so far 
    public static String formatNotGuessed(String notGuessed) {
        String updatedNotGuessed = "";
    
        // runs for the lenghth of what is not gussed
        for (int i = 0; i < notGuessed.length(); i++) { 
            updatedNotGuessed += notGuessed.charAt(i);

            if (i < notGuessed.length() - 1) { // makes sure that the z doesn't have --
                updatedNotGuessed += "--";
            }
        }
        return updatedNotGuessed;
    }

    // gets the user's guess and tells them if it is not valid 
    public static char getValidGuess(Scanner keyboard, String notGuessed) {
        char guess;
        while (true) { // runs when true, has error when taken out
            System.out.print("Enter your next guess: ");
            String input = keyboard.nextLine().toUpperCase(); // gets user guess from scanner

            if (input.length() > 0) {  // makes sure there is a guess
                guess = input.charAt(0); // only takes the first letter of guess
    
                if (notGuessed.indexOf(guess) != -1) { // makes sure the guess is not duplicate 
                    return guess;
                
                } else { // tells the user the guess is incorrect 
                    input=input.toLowerCase();
                    System.out.println("\n" + input + " is not a valid guess.");
                    System.out.println("The letters you have not guessed yet are:\n" 
                    + formatNotGuessed(notGuessed));
                    System.out.println();
                }
            }
        }
    }

    // updates the masked phrase when the user inputs a correct guess
    public static String updateMaskedPhrase(String secretPhrase, String maskedPhrase, 
    char guess) {
        String newMaskedPhrase = ""; 
        for (int i = 0; i < secretPhrase.length(); i++) { // runs for the length of the phrase
            if (secretPhrase.charAt(i) == guess) {   // changes when there is a correct guess
                newMaskedPhrase += guess;
            } else {
                newMaskedPhrase += maskedPhrase.charAt(i);
            }
        }
        return newMaskedPhrase;
    }

    // updates notguessed when user gets a correct guess 
    public static String removeFromNotGuessed(String notGuessed, char guess) {
        String removed = "";
        for (int i = 0; i < notGuessed.length(); i++) {
            if (notGuessed.charAt(i) != guess) {
                removed += notGuessed.charAt(i);
            }
        }
        return removed;
    }

    // ask the user to play again or not 
    public static boolean askToPlayAgain(Scanner keyboard) {
        System.out.println("Do you want to play again?");
        System.out.print("Enter 'Y' or 'y' to play again: ");
        return keyboard.next().equalsIgnoreCase("y");
    }
}