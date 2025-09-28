package edu.aitu.dsa.geometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

class ClosestPairTest {
    @Test
    void smallExampleMatchesBrute() {
        Point[] pts = {
                new Point(2, 3),
                new Point(12, 30),
                new Point(40, 50),
                new Point(5, 1),
                new Point(12, 10),
                new Point(3, 4)
        };
        double fast  = ClosestPair.closest(pts);
        double brute = ClosestPair.brute(pts);
        assertEquals(brute, fast, 1e-9);
    }

    @Test
    void bruteVsFastUpTo2000() {
        Random rng = new Random(9);
        int n = 2000;
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++)
            pts[i] = new Point(rng.nextDouble()*1000, rng.nextDouble()*1000);
        double fast  = ClosestPair.closest(pts);
        double brute = ClosestPair.brute(pts);
        assertEquals(brute, fast, 1e-6);
    }
}

