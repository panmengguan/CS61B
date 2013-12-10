package make;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.Map;
import java.util.HashMap;

/** Initial class for the 'make' program.
 *  @author Kiet Lam
 */
public final class Main {

    /** Location of usage message resource. */
    static final String USAGE = "make/Usage.txt";

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

        parseAndExecute(makefileName, fileInfoName, args);
    }

    /** Parse ARGS and execute the make using MAKEFILENAME and FILEINFONAME.*/
    private static void parseAndExecute(String makefileName,
                                        String fileInfoName, String... args) {
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
            } else if (args[a].equals("-o")) {
                a += 1;
                if (a == args.length) {
                    usage();
                } else {
                    try {
                        _out = new PrintWriter(new FileOutputStream(args[a]));
                    } catch (FileNotFoundException e) {
                        reportErrorExit("File not found");
                    }
                }
            } else if (args[a].equals("-e")) {
                a += 1;
                if (a == args.length) {
                    usage();
                } else {
                    try {
                        _err = new PrintWriter(new FileOutputStream(args[a]));
                    } catch (FileNotFoundException e) {
                        reportErrorExit("File not found");
                    }
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

        if (_hasError) {
            System.exit(1);
        }
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
            reportErrorExit("File info not found");
        }

        BufferedReader reader = new BufferedReader(new
                                                   InputStreamReader(iStream));

        int currentTime = 0;
        try {
            currentTime = Integer.parseInt(reader.readLine().replaceAll("\\s+",
                                                                        ""));
            Map<String, Integer> nameDateMap = createNameDateMap(reader);
            List<Rule> rules = parseRules(makefileName);

            Make maker = new Make(currentTime, nameDateMap, _out, _err);

            for (Rule rule: rules) {
                maker.addRule(rule);
            }

            for (String target: targets) {
                maker.build(target);
            }

        } catch (IOException e) {
            reportError("Failed to read from file");
        } catch (NumberFormatException e) {
            reportError("Invalid syntax");
        }
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

    /** Print a brief usage message and exit program abnormally. */
    private static void usage() {
        printHelpResource(USAGE, new PrintWriter(System.err));
        System.exit(1);
    }

    /** Parse and return a list of rules given a make file named FILENAME.*/
    private static List<Rule> parseRules(String filename) {
        List<Rule> rules = new ArrayList<Rule>();
        InputStream iStream = null;
        try {
            iStream = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            reportError("Make file not found");
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

                if (line.matches("^\\s*([^\\s:=#\\\\]+):(\\s*.*)")) {
                    if (currentRule != null) {
                        currentRule.addCommands(currentCommands);
                        rules.add(currentRule);
                        currentRule = null;
                    }

                    Pattern pattern =
                        Pattern.compile("^\\s*([^\\s:=#\\\\]+):(.*)");
                    Matcher matcher = pattern.matcher(line);
                    matcher.matches();

                    String name = matcher.group(1);
                    String delimiter = "[\\s :=#\\\\]";
                    String deps = matcher.group(2);
                    String[] dependencies = deps.trim().split(delimiter);

                    currentRule = new Rule(name, dependencies);
                    currentCommands = new ArrayList<String>();
                } else if (line.matches("^\\s*.*") && currentRule != null) {
                    currentCommands.add(line);
                } else {
                    reportErrorExit("Invalid syntax");
                }
            }

            if (currentRule != null) {
                currentRule.addCommands(currentCommands);
                rules.add(currentRule);
            }
        } catch (IllegalStateException e) {
            reportErrorExit("Invalid state");
        } catch (NoSuchElementException e) {
            reportErrorExit("Failed to parse file");
        } catch (IOException e) {
            reportErrorExit("Cannot read file");
        }
        return rules;
    }

    /** Create and return a map of objects to their created time in integer
     *  from READER.*/
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
            reportError("Invalid line");
        } catch (NumberFormatException e) {
            reportError("Cannot read number");
        } catch (IOException e) {
            reportError("Cannot read file");
        }

        return map;
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


    /** Our out printwriter.*/
    private static PrintWriter _out =
        new PrintWriter(new BufferedOutputStream(System.out));
    /** Our err printwriter.*/
    private static PrintWriter _err =
        new PrintWriter(new BufferedOutputStream(System.err));

    /** Flag to see if we have ran into an error or not.*/
    private static boolean _hasError = false;
}
