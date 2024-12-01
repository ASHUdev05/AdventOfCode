import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class part2 {
    private static int sets(String game) {
        String[] sets = game.split(":")[1].split(";"); // removed the id from the string and split the sets
        int[] oldValues = new int[3];
        int[] newValues = new int[3];
        for (String set : sets) {
            newValues = checkCubes(set.trim());
            if (newValues == null) {
                return 0;
            }
            for (int i = 0; i < newValues.length; i++) {
                oldValues[i] = Math.max(oldValues[i], newValues[i]);
            }
        }

        int prod = oldValues[0] * oldValues[1] * oldValues[2];
        return prod;
    }

    private static int[] checkCubes(String set) {
        set += ",";

        String[] colors = new String[3];
        // initialize the array
        for (int i = 0; i < colors.length; i++) {
            colors[i] = "";
        }

        int j = 0;
        String color = "";
        for (int i = 0; i < set.length(); i++) {
            if (set.charAt(i) == ',') {
                colors[j] = color.trim();
                color = "";
                j++;
            } else {
                color += set.charAt(i);
            }
        }

        int redValue = 0, greenValue = 0, blueValue = 0;

        for (int i = 0; i < colors.length; i++) {
            if (!colors[i].equals("")) {
                if (colors[i].split(" ")[1].equals("red")) {
                    redValue = Integer.parseInt(colors[i].split(" ")[0]);
                } else if (colors[i].split(" ")[1].equals("green")) {
                    greenValue = Integer.parseInt(colors[i].split(" ")[0]);
                } else if (colors[i].split(" ")[1].equals("blue")) {
                    blueValue = Integer.parseInt(colors[i].split(" ")[0]);
                } else {
                    return null;
                }
            }
        }

        int[] values = { redValue, greenValue, blueValue };
        return values;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String filename = "input.txt"; // Replace with the actual filename

        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        int sum = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            sum += sets(line);
        }

        System.out.println(sum);    //74804

        scanner.close();
    }
}
