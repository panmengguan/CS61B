import java.io.IOException;
import java.io.StringReader;

/** String translation.
 *  @author Kiet Lam
 */
public class Translate {
    /** Return the String S, but with all characters that occur in FROM
     *  changed to the corresponding characters in TO. FROM and TO must
     *  have the same length. */
    static String translate(String S, String from, String to) {
        /* NOTE: The try {...} catch is a technicality to keep Java happy. */
        char[] buffer = new char[S.length()];
        try {
            StringReader sReader = new StringReader(S);
            TrReader trReader = new TrReader(sReader, from, to);
            char[] chs = new char[S.length()];
            trReader.read(chs, 0, chs.length);

            return new String(chs);
        } catch (IOException e) { return null; }
    }
    /*
       REMINDER: translate must
      a. Be non-recursive
      b. Contain only 'new' operations, and ONE other method call, and no
         other kinds of statement (other than return).
      c. Use only the library classes String, and anything containing
         "Reader" in its name (browse the on-line documentation).
    */
}
