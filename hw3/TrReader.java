import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author Kiet Lam
 */
public class TrReader extends Reader {

    /** The Reader.*/
    private Reader _reader;

    /** The from.*/
    private String _from;

    /** The to.*/
    private String _to;

    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(0) to TO.charAt(0), etc., leaving other characters
     *  unchanged.  FROM and TO must have the same length. READER*/
    public TrReader(Reader reader, String from, String to) {
        _reader = reader;
        _from = from;
        _to = to;
    }

    /** Close the stream.*/
    public void close() {
        try {
            _reader.close();
        } catch (IOException e) {
            return;
        }
    }

    /** Returns a translated character.*/
    public int read() throws IOException {
        int ch = 'A';

        ch = _reader.read();

        if (ch == -1) {
            return -1;
        }

        int pos = _from.indexOf((char) ch);

        if (pos == -1) {
            return ch;
        } else {
            return _to.charAt(pos);
        }
    }

    /** Returns the reader using CBUF, OFF, and LEN.*/
    public int read(char[] cbuf, int off, int len) throws IOException {

        int ch = read();

        int numRead = 0;

        if (ch == -1) {
            return -1;
        } else {
            cbuf[off + numRead] = (char) ch;
        }

        while (true) {

            if (numRead == len) {
                break;
            }

            if (ch == -1) {
                break;
            }

            cbuf[off + numRead] = (char) ch;

            ch = read();
            numRead += 1;
        }

        return numRead;
    }
}
