package make;

import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.Map;
import java.util.HashMap;

/** Initial class for the 'make' program.
 *  @author Kiet Lam
 */
public final class Main {

    /** Entry point for the CS61B make program.  ARGS may contain options
     *  and targets:
     *      [ -f MAKEFILE ] [ -D FILEINFO ] TARGET1 TARGET2 ...
     */
    public static void main(String... args) {
        String makefileName;
        String fileInfoName;

        if (args.length == 0) {
            usage();
        }

        makefileName = "Makefile";
        fileInfoName = "fileinfo";

        int a;
        for (a = 0; a < args.length; a += 1) {
            if (args[a].equals("-f")) {
                a += 1;
                if (a == args.length) {
                    usage();
                } else {
                    makefileName = args[a];
                }
            } else if (args[a].equals("-D")) {
                a += 1;
                if (a == args.length) {
                    usage();
                } else {
                    fileInfoName = args[a];
                }
            } else if (args[a].startsWith("-")) {
                usage();
            } else {
                break;
            }
        }

        ArrayList<String> targets = new ArrayList<String>();

        for (; a < args.length; a += 1) {
            targets.add(args[a]);
        }

        make(makefileName, fileInfoName, targets);
    }

    /** Carry out the make procedure using MAKEFILENAME as the makefile,
     *  taking information on the current file-system state from FILEINFONAME,
     *  and building TARGETS, or the first target in the makefile if TARGETS
     *  is empty.
     */
    private static void make(String makefileName, String fileInfoName,
                             List<String> targets) {
        InputStream iStream = null;
        try {
            iStream = new FileInputStream(fileInfoName);
        } catch (FileNotFoundException e) {
            // TODO: Report error here...
            return;
        }

        BufferedReader reader = new BufferedReader(new
                                                   InputStreamReader(iStream));

        int currentTime;
        try {
            currentTime = Integer.parseInt(reader.readLine().replaceAll("\\s+",
                                                                        ""));
        } catch (IOException e) {
            // TODO: Report error here...
        } catch (NumberFormatException e) {
            // TODO: Report error here...
        }

        Map<String, Integer> nameDateMap = createNameDateMap(reader);
        List<Rule> rules = parseRules(makefileName);
    }

    /** Print a brief usage message and exit program abnormally. */
    private static void usage() {
        // FILL THIS IN
        // System.exit(1);
    }

    private static List<Rule> parseRules(String filename) {
        List<Rule> rules = new ArrayList<Rule>();

        InputStream iStream = null;
        try {
            iStream = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            // TODO: Report error here...
            return rules;
        }

        BufferedReader reader = new BufferedReader(new
                                                   InputStreamReader(iStream));
        try {
            String line = null;
            Rule currentRule = null;
            List<String> currentCommands = new ArrayList<String>();

            while ((line = reader.readLine()) != null) {
                if (line.equals("") || line.startsWith("#")) {
                    continue;
                }

                if (line.matches("(^[^\\s:=#\\]+):(\\s*.*)+")) {
                    if (currentRule != null) {
                        currentRule.addCommands(currentCommands);
                    }

                    Pattern pattern =
                        Pattern.compile("(^[^\\s:=#\\]+):(\\s*.*)+");
                    Matcher matcher = pattern.matcher(line);

                    String name = matcher.group(1);
                    String delimiter = "[\\s :=#\\]";
                    String[] dependencies = matcher.group(2).split(delimiter);

                    currentRule = new Rule(name, dependencies);
                    currentCommands = new ArrayList<String>();
                } else if (line.matches("^[ \t].*")) {
                    currentCommands.add(line);
                }
            }
        } catch (NoSuchElementException e) {
            // TODO: Handle exception
        } catch (IOException e) {
            // TODO: Handle exception
        }

        return rules;
    }

    private static Map<String, Integer> createNameDateMap(BufferedReader
                                                          reader) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] strs = line.split("\\s+");
                map.put(strs[0], Integer.parseInt(strs[1]));
            }
        } catch (NoSuchElementException e) {
            // TODO: handle exception
        } catch (NumberFormatException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return map;
    }
}
