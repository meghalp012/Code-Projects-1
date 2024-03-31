import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * CS312 Assignment 6
 * 
 * On my honor, <Meghal>, this programming assignment is my own work and I have
 * not shared my solution with any other student in the class.
 *
 * A program to play determine the extend of home field advantage in sports.
 *
 *  Name: Meghal Patel
 *  UTEID: mrp3937
 *  Grader name: Minerva
 *  slip days used : 2
 */
 
 /**
 * Data from 14 input files and analysis of Home Field Advantate go here. 
 * It seems from the files being proccessed that there is to some extent 
 * a home field advantage. I can deduce this from seeing that every file 
 * has the home team win percentage over 50% often being close to or over 60%
 * which is a huge increase when it comes to winning a game. Though we are not sure 
 * exactly what causes this advanatage from the files, we can see that the 
 * home field advantage does truly exist. 
 * 
 */
 
public class HomeField {

    // Ask the user for the name of a data file and process
    // it until they want to quit.
    public static void main(String[] args) throws IOException { // runs main code 
        System.out.println("A program to analyze home field advantage in sports.");
        System.out.println();
        Scanner keyboard = new Scanner(System.in);

        boolean continueProcessing = true;

        while (continueProcessing) { // runs until continueProcessing is false 
            System.out.print("Enter the file name: ");
            String filename = keyboard.nextLine();

            File file = new File(filename);

            while (!file.exists()) { // runs until file exist 
                System.out.println("Sorry, that file does not exist");
                System.out.print("Enter the file name: ");
                filename = keyboard.nextLine(); // ask for which file 
                file = new File(filename);
            }

            processFile(file, keyboard);

            System.out.println("\nDo you want to check another data set?");
            System.out.print("Enter Y or y to analyze another file, anything else to quit: ");
            String response = keyboard.nextLine(); // ask if want to play again
            System.out.println();

            if (response.equalsIgnoreCase("y") || response.charAt(0) == 'y' 
            || response.charAt(0) == 'Y') { // if is y or contains y continue 
                continueProcessing = true;
            }else{                                // else stop proccessing and close 
                continueProcessing = false;
                keyboard.close();
            }
        }
    }

    // void that prints the sport and season, with the parameter of user input 
    public static void printSeasonInfo(Scanner input) { 
        // Read sport and season information
        String sport = input.nextLine();  // gets the sport
        String season = input.nextLine(); // gets the year/ season

        System.out.println("\n**********   " + sport + " --- " + season + "   **********");

    }
    
    // void method that prints the stats calls the number of homegames, total games,
    // home team wins, homeTotal, and away total to calculate and print the stats
    public static void printStats(int homeGame, int totalGames, int homeTeamWins, int homeTotal, 
    int awayTotal) 
    {
        double percentageHome = (double) homeGame/totalGames; 
        double winPercentage =(double) homeTeamWins/homeGame;
        double averageMargin = Math.abs((double) (homeTotal-awayTotal))/homeGame;

        System.out.println("\nHOME FIELD ADVANTAGE RESULTS");
        System.out.printf("\nTotal number of games: %,d\n", totalGames);
        System.out.printf("Number of games with a home team: %,d\n", homeGame);
        System.out.printf("Percentage of games with a home team: %.1f%%\n",
         percentageHome * 100);
        System.out.printf("Number of games the home team won: %,d\n", homeTeamWins);
        System.out.printf("Home team win percentage: %.1f%%\n", winPercentage * 100);
        System.out.printf("Home team average margin: %.2f\n", averageMargin);
    }

    // calls the file and scanner, to be able to actually line and token base proccess 
    // and execute the code 
    public static void processFile(File file, Scanner keyboard) throws IOException {
        Scanner input = new Scanner(file);

        printSeasonInfo(input);
       
        boolean isHomeGame = false;
        int homeGame = 0, homeScore = 0, awayScore = 0, awayTotal = 0, homeTotal = 0;
        int totalGames = 0, homeTeamWins = 0;

        while (input.hasNextLine()) { // runs until file has a next line
            String line = input.nextLine();
            totalGames++;
            Scanner lineScanner = new Scanner(line);
        
            lineScanner.next(); // skip the date 
            String team1 = lineScanner.next();
        
            int score1 = 0;
            while (!lineScanner.hasNextInt()) { // runs when there is not an int 
                lineScanner.next(); // Skip non-integer token
            }
            score1 = lineScanner.nextInt();
        
            String team2 = lineScanner.next();
        
            int score2 = 0;
            while (!lineScanner.hasNextInt()) { // runs when there is not an int 
                lineScanner.next(); // Skip non-integer token
            }
            score2 = lineScanner.nextInt();
            // these while loops are used to not have a mainthrow exception error

            if (team1.contains("@")) { // if team 1 contains a @ then it is a home game 
                isHomeGame = true;        // and correlates homescore from teams 
                homeScore = score1;
                awayScore = score2;
                homeTotal += homeScore;
                awayTotal += awayScore;
            } else if (team2.contains("@")) {// if team 2 contains a @ then it is a home game 
                isHomeGame = true;        // and correlates homescore from teams 
                homeScore = score2;
                awayScore = score1;
                homeTotal += homeScore;
                awayTotal += awayScore;
            } else{
                isHomeGame = false; // else there is no home game 
            }
            
            if(isHomeGame){ // if home gain is true then increase home game to count 
                homeGame++;
            }

            if (isHomeGame && homeScore > awayScore) { // if home score is greater then 
                homeTeamWins++;                        // they won and increase homeTeamWins
            }
        
            lineScanner.close();
        }

        // Close the input scanner 
        input.close();

        printStats(homeGame, totalGames, homeTeamWins, homeTotal, awayTotal);

    }

}