import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class part2 {
    static String nums[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    private static String wordsToNum(String str) {
        String num = "";
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                num += str.charAt(i);
                continue;
            }
            for (int j = 0; j < nums.length; j++) {
                if (str.substring(i).startsWith(nums[j])) {
                    num += j + 1;
                    i += nums[j].length() - 2; // to account for overlapping substrings 85oneightxx -> 8518 (not 851)
                    break;
                }
            }
        }
        return num;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String filename = "input.txt"; // Replace with the actual filename

        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        int sum = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String num = wordsToNum(line);
            sum += Integer.parseInt(num.charAt(0) + "" + num.charAt(num.length() - 1));
        }

        System.out.println(sum);    //54824

        scanner.close();
    }
}
