package edu.aitu.dsa.algo.sort;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {
    @Test
    void sortsSmallFixed() {
        int[] a = {5, -2, 9, 0, 1, 8, 3, 3};
        int[] b = a.clone();
        Arrays.sort(b);
        MergeSort.sort(a);
        assertArrayEquals(b, a);
    }

    @Test
    void sortsRandomMany() {
        Random rng = new Random(1);
        for (int n : new int[]{1, 2, 3, 10, 100, 1000}) {
            int[] a = rng.ints(n, -1000, 1000).toArray();
            int[] b = a.clone();
            Arrays.sort(b);
            MergeSort.sort(a);
            assertArrayEquals(b, a);
        }
    }

    @Test
    void handlesDuplicatesAndReverse() {
        int[] d = {5,5,5,5,5,5,5};
        int[] r = {9,8,7,6,5,4,3,2,1,0,-1};
        int[] db = d.clone(), rb = r.clone();
        Arrays.sort(db); Arrays.sort(rb);
        MergeSort.sort(d); MergeSort.sort(r);
        assertArrayEquals(db, d); assertArrayEquals(rb, r);
    }
}