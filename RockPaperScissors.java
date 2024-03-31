import java.util.Scanner;

/**
 * CS312 Assignment 4.
 *
 * On my honor, <Meghal Patel>, this programming assignment is my own work and I
 * have
 * not shared my solution with any other student in the class.
 *
 * A program to play Rock Paper Scissors
 *
 * Name: Meghal Patel
 * email address: meghalp012@gmail.com
 * UTEID:mrp3937
 * Section 5 digit ID:
 * Grader name: Minerva 
 * Number of slip days used on this assignment: 1
 */

public class RockPaperScissors {
    /*
     * A program to allow a human player to play rock - paper - scissors
     * against the computer. If args.length != 0 then we assume
     * the first element of args can be converted to an int
     */
    public static final int ROCK = 1;
    public static final int PAPER = 2;
    public static final int SCISSORS = 3;
    public static String PlayerName; 

    public static void main(String[] args) {
        RandomPlayer computerPlayer = buildRandomPlayer(args);
        Scanner keyboard = new Scanner(System.in);

        intro(keyboard); // Ask player for their name and greets them

        int totalRounds = numberOfRounds(keyboard);
        int playerWins = 0; // counts the amount of times player wins
        int computerWins = 0; // counts the amount of times computer wins
        int draws = 0; //counts the amount of draws
        
        // each loop will run one round until the given total rounds
        for (int round = 1; round <= totalRounds; round++) { 
            System.out.println("\nRound " + round + ".");   
            int userChoice = userChoice(keyboard); 
            int computerChoice = computerPlayer.getComputerChoice(); 
            String userChoiceName = choiceName(userChoice);
            String computerChoiceName = choiceName(computerChoice);

            System.out.println( "Computer picked " + computerChoiceName + ", " 
                + PlayerName + " picked " + userChoiceName + ".");
            System.out.println();

            //  counts the results of the round
            int roundResult = roundResult(userChoice, computerChoice);
            if (roundResult == 0) {
                draws++;
            } else if (roundResult == 1) {
                playerWins++;
            } else {
                computerWins++;
            }
        }
        // must be inside main because the ++ would not run propely when called in conclusion

        conclusion(totalRounds, computerWins, playerWins, draws); 
        // shows end result of the game 

        keyboard.close();
    }

    public static void intro(Scanner keyboard) { // beginning introductions and ask for name
        System.out.println("Welcome to ROCK PAPER SCISSORS. I, Computer, will be your opponent.");
        System.out.print("Please type in your name and press return: ");
        PlayerName = keyboard.nextLine();
        System.out.println();
        System.out.println("Welcome " + PlayerName + ".");
    }

    public static int numberOfRounds(Scanner keyboard) { // ask how many rounds 
        System.out.print("\nAll right " + PlayerName + ". How many rounds would you like to " +
                "play?\nEnter the number of rounds you want to play and press return: ");
        return keyboard.nextInt();
    }

    public static int userChoice(Scanner keyboard) { // ask what element they want to use
        System.out.print(PlayerName + ", please enter your choice for this round.\n" +
                "1 for ROCK, 2 for PAPER, and 3 for SCISSORS: ");
        return keyboard.nextInt();
    }

    public static String choiceName(int choice) { // returns what the user chose
        if (choice == ROCK) {
            return "ROCK";
        } else if (choice == PAPER) {
            return "PAPER";
        } else if (choice == SCISSORS) {
            return "SCISSORS";
        } else {
            return "Invalid Choice"; 
        }
    }

    // tells the result of each round played
    // if 0 is returned there is a draw 
    // if 1 is returned then there the player wins
    // if negative 1 is returned then the computer wins
    public static int roundResult(int userChoice, int computerChoice) {
        if (userChoice == computerChoice) {
            System.out.println("We picked the same thing! This round is a draw.");
            return 0; // Draw
        } else if (userChoice == ROCK && computerChoice == SCISSORS) {
            System.out.println("ROCK breaks SCISSORS. You win.");
            return 1;
        } else if (userChoice == PAPER && computerChoice == ROCK) {
            System.out.println("PAPER covers ROCK. You win.");
            return 1;
        } else if (userChoice == SCISSORS && computerChoice == PAPER) {
            System.out.println("SCISSORS cut PAPER. You win.");
            return 1;
        } else if (computerChoice == ROCK && userChoice == SCISSORS) {
            System.out.println("ROCK breaks SCISSORS. I win.");
            return -1;
        } else if (computerChoice == PAPER && userChoice == ROCK) {
            System.out.println("PAPER covers ROCK. I win.");
            return -1;
        } else {
            System.out.println("SCISSORS cut PAPER. I win.");
            return -1;
        }
    }
       
    public static void conclusion(int totalRounds, int computerWins, 
        int playerWins, int draws) { // tells the results of the game and who the winner is 
        System.out.println();
        System.out.println();
        System.out.println("Number of games of ROCK PAPER SCISSORS: " + totalRounds);
        System.out.println("Number of times Computer won: " + computerWins);
        System.out.println("Number of times " + PlayerName + " won: " + playerWins);
        System.out.println("Number of draws: " + draws );

        if (playerWins > computerWins) { // runs when player wins
            System.out.println("You, " + PlayerName + ", are a master at ROCK, PAPER, SCISSORS.");
            System.out.println();
        } else if (computerWins > playerWins) { // runs when computer wins
            System.out.println("I, Computer, am a master at ROCK, PAPER, SCISSORS.");
            System.out.println();
        } else {                      // runs when there us a draw 
            System.out.println("We are evenly matched.");
            System.out.println();
        }
    }

    public static RandomPlayer buildRandomPlayer(String[] args) {
        if (args.length == 0) {
            return new RandomPlayer();
        } else {
            int seed = Integer.parseInt(args[0]);
            return new RandomPlayer(seed);
        }
    }
}