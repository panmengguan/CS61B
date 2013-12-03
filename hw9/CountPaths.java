import java.io.InputStreamReader;
import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

/** HW #10, problem 4.  Path-counting problem (translated and adapted by
 *  M. Dynin).
 *  @author Kiet Lam*/
public class CountPaths {

    /** Read in maze and print out number of paths. */
    public static void main(String[] ignored) {
        Scanner reader =
            new Scanner(new InputStreamReader(System.in));

        reader.useDelimiter("\\s+");
        int M = reader.nextInt();
        int N = reader.nextInt();
        int r = reader.nextInt();
        int c = reader.nextInt();

        String query = reader.next();

        List<String> strs = new ArrayList<String>();

        for (int i = 0; i < M; i += 1) {
            strs.add(reader.next());
        }

        int count = countPaths(0, strs, query, r, c);
        System.out.printf("There are %d paths.\n", count);
    }

    /** Returns the number of paths including N to create QUERY
     *  from STRS and starting position R, C.
     *  Calculate the next moves by computing the next steps (up to 8)*/
    private static int countPaths(int n, List<String> strs, String query,
                                  int r, int c) {
        if (r == strs.size() || c == strs.get(0).length()
            || query.equals("")) {
            return n;
        }

        List<Integer> nextRows = new ArrayList<Integer>();
        List<Integer> nextColumns = new ArrayList<Integer>();

        char nextChar = query.charAt(0);

        if (strs.get(r + 1).charAt(c - 1) == nextChar) {
            nextRows.add(r);
            nextColumns.add(c);
        }

        if (strs.get(r - 1).charAt(c) == nextChar) {
            nextRows.add(r - 1);
            nextColumns.add(c);
        }

        if (strs.get(r).charAt(c - 1) == nextChar) {
            nextRows.add(r);
            nextColumns.add(c - 1);
        }

        if (strs.get(r + 1).charAt(c) == nextChar) {
            nextRows.add(r + 1);
            nextColumns.add(c);
        }

        if (strs.get(r).charAt(c + 1) == nextChar) {
            nextRows.add(r);
            nextColumns.add(c);
        }

        if (strs.get(r - 1).charAt(c - 1) == nextChar) {
            nextRows.add(r);
            nextColumns.add(c);
        }

        if (strs.get(r + 1).charAt(c + 1) == nextChar) {
            nextRows.add(r);
            nextColumns.add(c);
        }

        if (strs.get(r - 1).charAt(c + 1) == nextChar) {
            nextRows.add(r - 1);
            nextColumns.add(c);
        }

        for (int i = 0; i < nextRows.size(); i += 1) {
            n += 1;
            n += countPaths(n, strs, query.substring(1, query.length() - 1),
                            nextRows.get(i), nextColumns.get(i));
        }

        return n;
    }
}
