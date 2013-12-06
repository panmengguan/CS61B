package trip;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

/** Initial class for the 'trip' program.
 *  @author
 */
public final class Main {

    /** Entry point for the CS61B trip program.  ARGS may contain options
     *  and targets:
     *      [ -m MAP ] [ -o OUT ] [ REQUEST ]
     *  where MAP (default Map) contains the map data, OUT (default standard
     *  output) takes the result, and REQUEST (default standard input) contains
     *  the locations along the requested trip.
     */
    public static void main(String... args) {
        String mapFileName;
        String outFileName;
        String requestFileName;

        mapFileName = "Map";
        outFileName = requestFileName = null;

        int a;
        for (a = 0; a < args.length; a += 1) {
            if (args[a].equals("-m")) {
                a += 1;
                if (a == args.length) {
                    usage();
                } else {
                    mapFileName = args[a];
                }
            } else if (args[a].equals("-o")) {
                a += 1;
                if (a == args.length) {
                    usage();
                } else {
                    outFileName = args[a];
                }
            } else if (args[a].startsWith("-")) {
                usage();
            } else {
                break;
            }
        }

        if (a == args.length - 1) {
            requestFileName = args[a];
        } else if (a > args.length) {
            usage();
        }

        if (requestFileName != null) {
            try {
                System.setIn(new FileInputStream(requestFileName));
            } catch  (FileNotFoundException e) {
                System.err.printf("Could not open %s.%n", requestFileName);
                System.exit(1);
            }
        }

        if (outFileName != null) {
            try {
                System.setOut(new PrintStream(new FileOutputStream(outFileName),
                                              true));
            } catch  (FileNotFoundException e) {
                System.err.printf("Could not open %s for writing.%n",
                                  outFileName);
                System.exit(1);
            }
        }

        trip(mapFileName);
    }

    /** Print a trip for the request on the standard input to the stsndard
     *  output, using the map data in MAPFILENAME.
     */
    private static void trip(String mapFileName) {
        InputStream iStream = null;
        try {
            iStream = new FileInputStream(mapFileName);
        } catch (FileNotFoundException e) {
            // TODO: Report error here...
        }

        BufferedReader reader = new BufferedReader(new
                                                   InputStreamReader(iStream));

        List<Location> locations = new ArrayList<Location>();
        List<Distance> distances = new ArrayList<Distance>();

        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {
                    continue;
                }

                String locationPat = "^L\\s+[^\\s]+\\s+[.0-9]+\\s+[.0-9]+\\s+";
                String distancePat = "^R\\s+([^\\s]+\\s+[.0-9]+\\s+[.0-9]+\\s+)+";

                if (line.matches(locationPat)) {
                    locations.add(new Location(line));
                } else if (line.matches(distancePat)) {
                    distances.add(new Distance(line));
                }
            }
        } catch (NoSuchElementException e) {
            // TODO: Handle exception
        } catch (IOException e) {
            // TODO: Handle exception
        }
    }

    /** Print a brief usage message and exit program abnormally. */
    private static void usage() {
        // FILL THIS IN
        System.exit(1);
    }
}
