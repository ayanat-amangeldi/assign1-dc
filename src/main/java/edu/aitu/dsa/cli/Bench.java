package edu.aitu.dsa.cli;

import edu.aitu.dsa.algo.sort.MergeSort;
import edu.aitu.dsa.algo.sort.QuickSort;
import edu.aitu.dsa.algo.select.DeterministicSelect;
import edu.aitu.dsa.metrics.Metrics;
import edu.aitu.dsa.metrics.CsvLogger;
import edu.aitu.dsa.geometry.ClosestPair;
import edu.aitu.dsa.geometry.Point;

import java.util.Random;

public final class Bench {
    private static final Random RNG = new Random();

    public static void main(String[] args) {
        String algo = args.length > 0 ? args[0] : "mergesort";
        int nStart = args.length > 1 ? Integer.parseInt(args[1]) : 1000;
        int nEnd   = args.length > 2 ? Integer.parseInt(args[2]) : 10000;
        int step   = args.length > 3 ? Integer.parseInt(args[3]) : 1000;
        int reps   = args.length > 4 ? Integer.parseInt(args[4]) : 3;

        Metrics m = new Metrics();
        try (CsvLogger csv = new CsvLogger("metrics.csv", true)) {
            for (int n = nStart; n <= nEnd; n += step) {
                for (int r = 0; r < reps; r++) {
                    m.reset();
                    switch (algo) {
                        case "mergesort" -> {
                            int[] a = randInt(n);
                            m.startTimer(); MergeSort.sort(a, m); m.stopTimer();
                            csv.write("mergesort", n, m);
                        }
                        case "quicksort" -> {
                            int[] a = randInt(n);
                            m.startTimer(); QuickSort.sort(a, m); m.stopTimer();
                            csv.write("quicksort", n, m);
                        }
                        case "select" -> {
                            int[] a = randInt(n); int k = RNG.nextInt(n);
                            m.startTimer(); DeterministicSelect.select(a, k); m.stopTimer();
                            csv.write("select", n, m);
                        }
                        case "closest" -> {
                            Point[] pts = randPoints(n);
                            m.startTimer(); ClosestPair.closest(pts); m.stopTimer();
                            csv.write("closest", n, m);
                        }
                        default -> throw new IllegalArgumentException("Unknown algo: " + algo);
                    }
                }
            }
        }
        System.out.println("Done -> metrics.csv");
    }

    private static int[] randInt(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = RNG.nextInt(n * 2) - n;
        return a;
    }
    private static Point[] randPoints(int n) {
        Point[] p = new Point[n];
        for (int i = 0; i < n; i++) p[i] = new Point(RNG.nextDouble()*1000, RNG.nextDouble()*1000);
        return p;
    }
}
