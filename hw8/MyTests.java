import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;


public class MyTests {

    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MyTests.class));
    }

    @Test
    public void testUncovered() {
        int max = 2000;

        List<int[]> ranges = new ArrayList<int[]>();

        ranges.add(new int[] {2, 5});
        ranges.add(new int[] {12, 1000});

        List<int[]> re = Ranges.uncovered(ranges, max);

        assertEquals(re.get(0)[0], 0);
        assertEquals(re.get(0)[1], 1);
        assertEquals(re.get(1)[0], 6);
        assertEquals(re.get(1)[1], 11);
        assertEquals(re.get(2)[0], 1001);
        assertEquals(re.get(2)[1], 2000);
    }

    @Test
    public void testSort() {
        long[] ars = new long[] {5, 2, 7, 8, 5, 3, 5, 8, 9, 10};

        SortInts.sort(ars);

        assertEquals(ars[0], 2);
        assertEquals(ars[1], 3);
        assertEquals(ars[2], 5);
        assertEquals(ars[3], 5);
        assertEquals(ars[4], 5);
        assertEquals(ars[5], 7);
        assertEquals(ars[6], 8);
    }

    @Test
    public void testInversion() {
        int[] ars = new int[] {15, 14, 13, 12, 11, 10, 9, 8,
                               7, 6, 5, 4, 3, 2, 1, 0};

        List<Integer> ls = new ArrayList<Integer>();

        for (int i = 0; i < ars.length; i += 1) {
            ls.add(ars[i]);
        }

        assertEquals(120, Inversions.inversions(ls));
    }

    @Test
    public void testSum() {
        int[] r1 = new int[] {3, 5, 1, 7, 9, 11};
        int[] r2 = new int[] {55, 7, 2, 5, 4};

        assertTrue(Sum.sumsTo(r1, r2, 58));
        assertFalse(Sum.sumsTo(r1, r2, 100));
    }
}
