package edu.aitu.dsa.algo.sort;

import edu.aitu.dsa.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class QuickSortDepthTest {
    @Test
    void depthIsLogarithmicOnRandom() {
        Random rng = new Random(42);
        for (int n : new int[]{100, 500, 1000, 5000, 10000}) {
            int[] a = rng.ints(n, -1_000_000, 1_000_000).toArray();
            Metrics m = new Metrics();
            m.startTimer(); QuickSort.sort(a, m); m.stopTimer();
            int log2 = 31 - Integer.numberOfLeadingZeros(n);
            int bound = 2 * log2 + 8; // мягкая граница
            assertTrue(m.maxDepth <= bound, "depth=" + m.maxDepth + " bound=" + bound + " n=" + n);
        }
    }
}
