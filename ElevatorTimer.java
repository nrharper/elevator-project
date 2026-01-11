import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Simple elevator timer program that calculates the time taken to travel between floors. Assumes floors are traveled in the order they are entered.
 */
public class ElevatorTimer {
    private static final int FLOOR_TRAVEL_SPEED = 10;
    private static final String INVALID_FLOOR_MESSAGE = "The floor must be an integer between 1 and 100.";

    /**
     * Main method to run the elevator timer program.
     * @param args unused
     */
    public static void main(String[] args) {
        // Create a Scanner object to read input from the console
        Scanner scanner = new Scanner(System.in);
        List<Integer> floorNumbers = new ArrayList<>();

        // Get the starting elavator floor from the user
        int firstFloor = getFloorFromUser("Enter the current elavator floor (1-100, or press enter to exit): ", scanner);
        if (firstFloor == -1) {
            System.out.println("No floor entered. Exiting.");
            scanner.close();
            return;
        }
        floorNumbers.add(firstFloor);

        // Continue reading floors to traverse until the user indicates they are done
        int nextFloor = -1;
        boolean shouldExit = false;
        while (shouldExit == false) {
            nextFloor = getFloorFromUser("Enter the next floor to travel to (1-100, or press enter to finish): ", scanner);
            if (nextFloor == -1) {
                shouldExit = true;
            } else {
                floorNumbers.add(nextFloor);
            }
        }

        // Print summary of results
        int totalTime = calculateTravelTime(floorNumbers);
        StringJoiner joiner = new StringJoiner(",");
        floorNumbers.forEach(floor -> joiner.add(floor.toString()));
        System.out.println("Floors visited: " + joiner.toString());
        System.out.println("Total time taken to travel between floors: " + totalTime + " seconds");

        scanner.close();
    }

    /**
     * Prompts the user for a floor number and validates the input.
     * @param prompt The prompt message to display to the user.
     * @param scanner The Scanner object to read user input.
     * @return The valid floor number entered by the user, or -1 if the user chooses to exit.
     */
    private static int getFloorFromUser(String prompt, Scanner scanner) {
        int floorNum = -1;
        boolean isValid = false;

        while (!isValid) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                return -1; // Exit the loop if the input is empty
            } else {
                try {
                    // Try to convert the string input to an integer
                    floorNum = Integer.parseInt(input);
                    if (floorNum > 0 && floorNum <= 100) {
                        isValid = true;
                    } else {
                        System.out.println(INVALID_FLOOR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    // Catch the exception if the input is not a valid integer
                    System.out.println(INVALID_FLOOR_MESSAGE);
                }
            }
        }

        return floorNum;
    }

    /**
     * Calculates the total travel time between the given floor numbers.
     * @param floorNumbers The list of floor numbers to travel between.
     * @return The total travel time.
     */
    private static int calculateTravelTime(List<Integer> floorNumbers) {
        int totalTime = 0;
        for(int i = 0; i < floorNumbers.size() - 1; i++) {
            int currentFloor = floorNumbers.get(i);
            int destinationFloor = floorNumbers.get(i + 1);
            int floorsTraveled = Math.abs(destinationFloor - currentFloor);
            totalTime += floorsTraveled * FLOOR_TRAVEL_SPEED;
        }
        return totalTime;
    }
}