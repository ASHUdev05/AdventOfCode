import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class part1 {
    private static char firstDigitInString(String str) {
        for (int i = 0; i < str.length(); i++) 
            if (Character.isDigit(str.charAt(i)))
                return str.charAt(i);
        return '0';
    }

    private static char lastDigitInString(String str) {
        for (int i = str.length() - 1; i >= 0; i--)
            if (Character.isDigit(str.charAt(i))) 
                return str.charAt(i);
        return '0';
    }

    public static void main(String[] args) throws FileNotFoundException {
        String filename = "input.txt"; // Replace with the actual filename

        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        int sum = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String num = firstDigitInString(line) + "" + lastDigitInString(line);
            sum += Integer.parseInt(num);
        }

        System.out.println(sum);    //55386

        scanner.close();
    }
}
