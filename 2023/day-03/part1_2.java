import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class part1_2 {

    public static void main(String[] args) throws FileNotFoundException {
        String filename = "input.txt"; // Replace with the actual filename

        List<String> grid = readFileLines(filename);
        int N = grid.size();
        int M = grid.get(0).length();

        Predicate<Character> isSymbol = c -> !Character.isDigit(c) && c != '.';
        BiPredicate<Integer, Integer> isValid = (i, j) -> i >= 0 && i < N && j >= 0 && j < M;

        List<List<List<Integer>>> V = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            V.add(new ArrayList<>());
            for (int j = 0; j < M; j++) {
                V.get(i).add(new ArrayList<>());
            }
        }

        int part1 = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (Character.isDigit(grid.get(i).charAt(j))) {
                    int n = 0;
                    int l = j;
                    while (j < M && Character.isDigit(grid.get(i).charAt(j))) {
                        n = n * 10 + (grid.get(i).charAt(j++) - '0');
                    }
                    int r = j - 1;

                    boolean affected = false;
                    for (int k = l - 1; k < r + 2; k++) {
                        for (int ii = i - 1; ii < i + 2; ii++) {
                            if (isValid.test(ii, k) && isSymbol.test(grid.get(ii).charAt(k))) {
                                affected = true;
                                V.get(ii).get(k).add(n);
                            }
                        }
                    }
                    if (affected) {
                        part1 += n;
                    }
                }
            }
        }

        int part2 = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (grid.get(i).charAt(j) == '*' && V.get(i).get(j).size() == 2) {
                    part2 += V.get(i).get(j).get(0) * V.get(i).get(j).get(1);
                }
            }
        }

        System.out.println("Part 1: " + part1);
        System.out.println("Part 2: " + part2);
    }

    private static List<String> readFileLines(String filename) throws FileNotFoundException {
        List<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        return lines;
    }
}
