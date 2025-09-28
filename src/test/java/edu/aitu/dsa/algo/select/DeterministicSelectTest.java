package edu.aitu.dsa.algo.select;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class DeterministicSelectTest {
    @Test
    void hundredTrials() {
        Random rng = new Random(7);
        for (int t = 0; t < 100; t++) {
            int n = 50 + rng.nextInt(200);
            int[] a = rng.ints(n, -10_000, 10_000).toArray();
            int[] b = a.clone();
            Arrays.sort(b);
            int k = rng.nextInt(n);
            int kth = DeterministicSelect.select(a.clone(), k);
            assertEquals(b[k], kth, "trial="+t+" n="+n+" k="+k);
        }
    }

    @Test
    void errorsOnBadArgs() {
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(null, 0));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{1,2,3}, -1));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{1,2,3}, 3));
    }
}
