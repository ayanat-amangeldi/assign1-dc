package edu.aitu.dsa.algo.sort;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {
    @Test
    void sortsSmallFixed() {
        int[] a = {5, -2, 9, 0, 1, 8, 3, 3};
        int[] b = a.clone();
        Arrays.sort(b);
        QuickSort.sort(a);
        assertArrayEquals(b, a);
    }

    @Test
    void sortsRandomMany() {
        Random rng = new Random(2);
        for (int n : new int[]{1, 2, 3, 10, 100, 1000}) {
            int[] a = rng.ints(n, -1000, 1000).toArray();
            int[] b = a.clone();
            Arrays.sort(b);
            QuickSort.sort(a);
            assertArrayEquals(b, a);
        }
    }

    @Test
    void handlesDuplicatesAndNegatives() {
        int[] a = {0,0,0,0,-1,-1,2,2,2,1};
        int[] b = a.clone();
        Arrays.sort(b);
        QuickSort.sort(a);
        assertArrayEquals(b, a);
    }
}
