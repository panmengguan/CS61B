import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


/** P1 class for #1.
 *  @author Kiet Lam
 */
public class P1 {

    /** Parses input and output solution to #1.*/
    public static void main(String... ignored) {
        Scanner scanner = new Scanner(System.in);

        List<Image> images = new ArrayList<Image>();

        Image image = new Image();

        while (scanner.hasNextLine()) {
            String temp = scanner.nextLine();

            if (temp.equals("")) {
                images.add(image);
                image = new Image();
                continue;
            }

            int xs = countCharacter(temp, 'X');
            int spaces = countCharacter(temp, ' ');

            image.addRow(new Row(xs, spaces));
        }

        if (image.getRows().size() != 0) {
            images.add(image);
        }

        int counter = 1;
        for (Image img: images) {
            System.out.printf("Image %d: %d\n\n", counter, calculateArea(img));
            counter += 1;
        }
    }

    /** Returns the area for an IMAGE.*/
    private static int calculateArea(Image image) {
        int maxXs = 0;
        int spacesDelete = 0;

        for (Row row: image.getRows()) {
            if (row.getXs() > maxXs) {
                maxXs = row.getXs();
                spacesDelete = row.getSpaces();
            }
        }

        int area = 0;

        for (Row row: image.getRows()) {
            area += row.getSpaces() - spacesDelete;
        }

        return area;
    }

    /** Returns the number of CH in STR.*/
    private static int countCharacter(String str, char ch) {
        int numCount = 0;

        for (int i = 0; i < str.length(); i += 1) {
            if (str.charAt(i) == ch) {
                numCount += 1;
            }
        }

        return numCount;
    }

    /** Image class.*/
    private static class Image {

        /** Rows in an image.*/
        private List<Row> _rows;

        /** Construct an empty image.*/
        Image() {
            _rows = new ArrayList<Row>();
        }

        /** Add ROW to the image.*/
        void addRow(Row row) {
            _rows.add(row);
        }

        /** Returns the rows in this image.*/
        List<Row> getRows() {
            return _rows;
        }
    }

    /** Class representing row of an image.*/
    private static class Row {

        /** The Xs in this row.*/
        private int _xs;

        /** The spaces in this row.*/
        private int _spaces;

        /** Construct a row using XS and SPACES.*/
        Row(int xs, int spaces) {
            _xs = xs;
            _spaces = spaces;
        }

        /** Returns the number of Xs in this row.*/
        int getXs() {
            return _xs;
        }

        /** Returns the number of spaces in this row.*/
        int getSpaces() {
            return _spaces;
        }
    }
}
