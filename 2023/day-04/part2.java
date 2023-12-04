import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class part2 {

    static HashMap<Integer, List<String>> ownedNums = new HashMap<>();
    static HashMap<Integer, List<String>> winningNUMS = new HashMap<>();
    static HashMap<Integer, Integer> cardCounts = new HashMap<>(); // For Part 2

    private static void parseInput(String line) {
        // Split for "|" [0] goes to winningNUMS, [1] goes to ownedNums
        String[] split = line.split("\\|");
        String[] winning = (split[0].split(":"))[1].split(" ");
        String[] owned = split[1].split(" ");

        List<String> winningList = new ArrayList<>();
        List<String> ownedList = new ArrayList<>();

        for (String num : winning) {
            if (!num.equals("")) {
                winningList.add(num);
            }
        }
        for (String num : owned) {
            if (!num.equals("")) {
                ownedList.add(num);
            }
        }

        winningNUMS.put(winningNUMS.size(), winningList); // Add card index as key
        ownedNums.put(ownedNums.size(), ownedList); // Add card index as key

        // Update card counts for Part 2
        for (String num : winningList) {
            incrementCount(Integer.parseInt(num));
        }
    }

    private static void incrementCount(int number) {
        if (!cardCounts.containsKey(number)) {
            cardCounts.put(number, 1);
        } else {
            cardCounts.put(number, cardCounts.get(number) + 1);
        }
    }

    private static int checkWinningNums(int index) {
        // If all winning numbers are in owned numbers, the point is 1 and is doubled for each match thereafter
        int points = 0;
        for (String num : winningNUMS.get(index)) {
            if (ownedNums.get(index).contains(num)) {
                points++;
            }
        }
        return (int) Math.pow(2, points - 1);
    }

    public static void main(String[] args) throws FileNotFoundException {
        String filename = "input.txt"; // Replace with the actual filename

        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        int cardNum = 0;
        while (scanner.hasNextLine()) {
            cardNum++;
            String line = scanner.nextLine();
            parseInput(line);
        }

        // Part 2
        HashMap<Integer, Integer> copies = new HashMap<>(); // Store card copies
        for (int cardIndex = 0; cardIndex < cardNum; cardIndex++) {
            int matches = checkWinningNums(cardIndex);

            // Update card copies for all subsequent cards
            for (int i = 0; i < matches; i++) {
                for (int j = cardIndex + 1; j < cardNum; j++) {
                    if (copies.containsKey(j)) {
                        copies.put(j, copies.get(j) + 1);
                    } else {
                        copies.put(j, 1);
                    }
                }
            }
        }

        int totalCards = 0;
        for (int count : copies.values()) {
            totalCards += count;
        }

        System.out.println("Total Cards: " + totalCards); //9236992

        scanner.close();
    }
}
