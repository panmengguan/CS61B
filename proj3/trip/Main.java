package trip;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;


/** Initial class for the 'trip' program.
 *  @author Kiet Lam
 */
public final class Main {

    /** Location of usage message resource. */
    private static final String USAGE = "trip/Usage.txt";

    /** Entry point for the CS61B trip program.  ARGS may contain options
     *  and targets:
     *      [ -m MAP ] [ -o OUT ] [ REQUEST ]
     *  where MAP (default Map) contains the map data, OUT (default standard
     *  output) takes the result, and REQUEST (default standard input) contains
     *  the locations along the requested trip.
     */
    public static void main(String... args) {
        String mapFileName = "Map";
        String requestFileName = null;
        _out = new PrintWriter(System.out);
        _err = new PrintWriter(System.err);

        int a;
        for (a = 0; a < args.length - 1; a += 1) {
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
                    _out = getOutput(args[a]);
                }
            } else if (args[a].equals("-e")) {
                a += 1;
                if (a == args.length) {
                    usage();
                } else {
                    _err = getOutput(args[a]);
                }
            } else if (args[a].startsWith("-")) {
                usage();
            }
        }

        if (a == args.length - 1) {
            _in = getInputStream(args[a]);
        } else if (a > args.length) {
            usage();
        }

        if (requestFileName != null) {
            _in = getInputStream(requestFileName);
        }

        trip(mapFileName);
        _out.close();
        _err.close();
        if (_hasError) {
            System.exit(1);
        }
    }

    /** Returns an inputsteam from file FILENAME.*/
    private static InputStream getInputStream(String filename) {
        InputStream in = null;
        try {
            in = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            return null;
        }

        return in;
    }

    /** Returns a printwriter from file FILENAME.*/
    private static PrintWriter getOutput(String filename) {
        PrintWriter pw = null;
        if (filename != null) {
            try {
                pw = new PrintWriter(new FileOutputStream(filename));
            } catch (FileNotFoundException e) {
                reportErrorExit("Could not open %s for writing.\n",
                                filename);
            }
        }

        return pw;
    }

    /** Print a trip for the request on the standard input to the stsndard
     *  output, using the map data in MAPFILENAME.
     */
    private static void trip(String mapFileName) {
        InputStream iStream = null;
        try {
            iStream = new FileInputStream(mapFileName);
        } catch (FileNotFoundException e) {
            reportErrorExit("Failed to read map file");
        }

        BufferedReader reader = new BufferedReader(new
                                                   InputStreamReader(iStream));

        Planner planner = new Planner(_out, _err);
        try {
            List<String> locations = parseLocations();

            String line = null;

            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {
                    continue;
                }

                String locationPat = Location.LOCATION_PATTERN;
                String distancePat = Distance.DISTANCE_PATTERN;

                if (line.matches(locationPat)) {
                    planner.addLocation(new Location(line));
                } else if (line.matches(distancePat)) {
                    planner.addDistance(new Distance(line));
                } else {
                    reportErrorExit("Invalid syntax");
                }
            }

            List<String> directions = planner.planTrip(locations);

            _out.printf("From %s:\n\n", locations.get(0));
            _out.flush();

            for (String direction: directions) {
                _out.println(direction);
                _out.flush();
            }

        } catch (NoSuchElementException e) {
            reportErrorExit("Failed to parse input");
        } catch (IOException e) {
            reportErrorExit("Failed to parse input");
        }
    }

    /** Return a list of locations from _in.*/
    private static List<String> parseLocations() {
        if (_in == null) {
            reportErrorExit("Could not open request file.");
        }

        Scanner scanner = new Scanner(_in);
        scanner.useDelimiter("\\s*,\\s*");

        List<String> locations = new ArrayList<String>();

        while (scanner.hasNext()) {
            locations.add(scanner.next().trim());
        }

        return locations;
    }

    /** Print a brief usage message and exit program abnormally. */
    private static void usage() {
        printHelpResource(USAGE, new PrintWriter(System.err));
        System.exit(1);
    }

    /** Print the contents of the resource named NAME on OUT.
     *  NAME will typically be a file name based in one of the directories
     *  in the class path.  */
    static void printHelpResource(String name, PrintWriter out) {
        try {
            InputStream resource =
                Main.class.getClassLoader().getResourceAsStream(name);
            BufferedReader str =
                new BufferedReader(new InputStreamReader(resource));
            for (String s = str.readLine(); s != null; s = str.readLine())  {
                out.println(s);
            }
            str.close();
            out.flush();
        } catch (IOException excp) {
            out.printf("No help found.");
            out.flush();
        }
    }

    /** Report an error and exit using S and ARGS.*/
    public static void reportErrorExit(String s, Object... args) {
        _err.printf(s + "\n", args);
        _err.flush();
        System.exit(1);
    }

    /** Report an error using WRITER, S and ARGS.*/
    public static void reportError(PrintWriter writer, String s, Object... args)
    {
        writer.printf(s + "\n", args);
        writer.flush();
        _hasError = true;
    }

    /** Report an error using S and ARGS.*/
    public static void reportError(String s, Object... args) {
        _err.printf(s + "\n", args);
        _err.flush();
        _hasError = true;
    }

    /** The input stream to get requests from.*/
    private static InputStream _in = new BufferedInputStream(System.in);

    /** The output writer to write outputs to.*/
    private static PrintWriter _out = new PrintWriter(System.out);

    /** The error writer to write error to.*/
    private static PrintWriter _err = new PrintWriter(System.err);

    /** Flag for if we have an error or not.*/
    private static boolean _hasError = false;
}
