import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class part1 {
    static final int red = 12, green = 13, blue = 14;

    private static int sets(String game) {
        // the input is a string with multiple sets separated by ; get each set and pass
        // it to checkCubes(String set)
        int id = getId(game);
        String[] sets = game.split(":")[1].split(";"); // removed the id from the string and split the sets
        boolean valid = true;
        for (String set : sets) {
            valid = checkCubes(set.trim());
            if (!valid) {
                break;
            }
        }

        return valid ? id : 0;
    }

    private static int getId(String game) {
        String[] sets = game.split(":");

        // sets[0] also contains a string alonside the id, so we need to split it again
        String[] id = sets[0].split(" ");
        return Integer.parseInt(id[1]);
    }

    private static boolean checkCubes(String set) {
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
                    return false;
                }
            }
        }

        if (redValue > red || greenValue > green || blueValue > blue) {
            return false;
        } else {
            return true;
        }
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

        System.out.println(sum);    //2317

        scanner.close();
    }
}
