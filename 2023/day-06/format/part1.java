import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class part1 {
    static List<Integer> time = new ArrayList<Integer>();
    static List<Integer> dist = new ArrayList<Integer>();

    private static int getDistance(int t, int pressed) {
        return (t-pressed) * pressed;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String filename = "input.txt"; // Replace with the actual filename

        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            if(parts[0].equals("Time")) {
                for(String s : parts[1].split(" "))
                    if(!s.equals(""))
                        time.add(Integer.parseInt(s));
            }
            else if(parts[0].equals("Distance")) {
                for(String s : parts[1].split(" "))
                    if(!s.equals(""))
                        dist.add(Integer.parseInt(s));
            }
        }

        int c = 0;
        int prod = 1;
        for(int i=0; i<time.size(); i++) {
            int t = time.get(i);
            int d = dist.get(i);
            for(int j=0; j<time.get(i); j++)
                if(getDistance(t, j+1) > d)
                    c++;
            prod *= c;
            c = 0;
        }

        System.out.println(prod);

        scanner.close();
    }
}
