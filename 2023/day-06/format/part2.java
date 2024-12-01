import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class part2 {
    static long time = 0;
    static long dist = 0;

    private static long getDistance(long t, long pressed) {
        return (t-pressed) * pressed;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String filename = "input.txt"; // Replace with the actual filename

        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            String val="";
            if(parts[0].equals("Time")) {
                for(String s : parts[1].split(" "))
                    if(!s.equals(""))
                        val += s;
                time = Long.parseLong(val);
            }
            else if(parts[0].equals("Distance")) {
                val = "";
                for(String s : parts[1].split(" "))
                    if(!s.equals(""))
                        val += s;
                dist = Long.parseLong(val);
            }
        }

        int c = 0;

        for (long i = 0; i < time; i++) {
            if (getDistance(time, i) > dist) {
                c++;
            }
        }

        System.out.println(c);

        scanner.close();
    }
}
