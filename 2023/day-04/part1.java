import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class part1 {
    static List<List<String>> ownedNums = new ArrayList<>();
    static List<List<String>> winningNUMS = new ArrayList<>();

    private static void parseInput(String line) {
        // split for "|" [0] goes to winningNUMS, [1] goes to ownedNums
        String[] split = line.split("\\|");
        String[] winning = (split[0].split(":"))[1].split(" ");
        String[] owned = split[1].split(" ");

        List<String> winningList = new ArrayList<>();
        List<String> ownedList = new ArrayList<>();

        for (String num : winning) {
            if(!num.equals(""))
                winningList.add(num);
        }
        for (String num : owned) {
            if(!num.equals(""))
                ownedList.add(num);
        }

        winningNUMS.add(winningList);
        ownedNums.add(ownedList);
    }

    private static int checkWinningNums(int index) {
        // if all winning numbers are in owned numbers, the point is 1 and is doubled for each match thereafter
        int points = 0;
        for (String num : winningNUMS.get(index)) {
            if (ownedNums.get(index).contains(num)) {
                points++;
            }
        }
        return (int)Math.pow(2, points-1);
    }

    public static void main(String[] args) throws FileNotFoundException {
        String filename = "input.txt"; // Replace with the actual filename

        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        int sum = 0;

        int cardNum = 0;
        while (scanner.hasNextLine()) {
            cardNum++;
            String line = scanner.nextLine();
            parseInput(line);
            sum += checkWinningNums(cardNum-1);
        }

        System.out.println(sum); //23028

        scanner.close();
    }
}
