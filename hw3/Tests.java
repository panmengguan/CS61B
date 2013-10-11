import org.junit.Test;
import static org.junit.Assert.*;

import java.io.Reader;
import java.io.IOException;
import java.io.StringReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Tests {

    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(Tests.class));
    }

    @Test public void trReader() {
        StringReader reader = new StringReader("high dareee what is up");
        TrReader trReader = new TrReader(reader, "ieee", "IEEE");

        assertEquals(readReader(trReader), "hIgh darEEE what Is up");

        StringReader reader1 = new StringReader("translate me please");
        TrReader emptyTr = new TrReader(reader1, "", "");

        assertEquals(readReader(emptyTr), "translate me please");

        StringReader reader2 = new StringReader("translate me please");
        TrReader trReader2 = new TrReader(reader2, "e", "a");

        assertEquals(readReader(trReader2), "translata ma plaasa");

        StringReader reader3 = new StringReader("");
        TrReader trReader3 = new TrReader(reader2, "e", "a");

        assertEquals(readReader(trReader2), "");
    }

    @Test public void trReaderRead() {
        String original = "high dareee what is up";

        StringReader reader = new StringReader(original);
        TrReader trReader = new TrReader(reader, "ieee", "IEEE");

        char[] chs = new char[original.length()];

        try {
            trReader.read(chs, 0, chs.length);
        } catch (IOException e) {
            return;
        }

        String output = new String(chs);

        assertEquals(output, "hIgh darEEE what Is up");
    }

    @Test public void trReaderRead2() {
        String original = "abcde";

        StringReader reader = new StringReader(original);
        TrReader trReader = new TrReader(reader, "edcab", "EDCAB");

        char[] chs = new char["ZZZZZZZ".length()];

        for (int i = 0; i < chs.length; i += 1) {
            chs[i] = 'Z';
        }

        try {
            trReader.read(chs, 3, 0);
        } catch (IOException e) {
            return;
        }

        String output = new String(chs);

        assertEquals(output, "ZZZZZZZ");
    }

    @Test public void trReaderRead3() {
        String original = "azbzczdz";

        StringReader reader = new StringReader(original);
        TrReader trReader = new TrReader(reader, "edcab", "EDCAB");

        char[] chs = new char["ZZZZZZZ".length()];

        try {
            trReader.read(chs, 0, 2);
        } catch (IOException e) {
            return;
        }

        String output = new String(chs);

        assertEquals(output, "BzZZZZZZ");
    }

    @Test public void translate() {
        String original = "high dareee what is up";
        String from = "ieee";
        String to = "IEEE";

        assertEquals("hIgh darEEE what Is up",
                     Translate.translate(original, from, to));
    }

    String readReader(Reader reader) {
        String str = "";

        try {
            while (true) {
                int c = reader.read();
                if (c == -1) {
                    break;
                }

                str += (char) c;
            }
        } catch (IOException e) {
            return str;
        }

        return str;
    }

    @Test public void length() {
        WeirdList l3 = new WeirdList(3, WeirdList.EMPTY);
        WeirdList l2 = new WeirdList(2, l3);
        WeirdList l1 = new WeirdList(1, l2);

        assertEquals(l1.length(), 3);
    }

    @Test public void sum() {
        WeirdList l3 = new WeirdList(3, WeirdList.EMPTY);
        WeirdList l2 = new WeirdList(2, l3);
        WeirdList l1 = new WeirdList(1, l2);

        assertEquals(User.sum(l1), 6);
    }

    @Test public void print() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        WeirdList l3 = new WeirdList(3, WeirdList.EMPTY);
        WeirdList l2 = new WeirdList(2, l3);
        WeirdList l1 = new WeirdList(1, l2);

        l1.print();

        String output = baos.toString();

        assertEquals("1 2 3 ", output);
    }
}
