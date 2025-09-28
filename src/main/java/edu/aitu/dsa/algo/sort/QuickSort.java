package edu.aitu.dsa.algo.sort;

import java.util.Random;
import edu.aitu.dsa.util.ArrayUtil;
import edu.aitu.dsa.metrics.Metrics;

public final class QuickSort {
    private static final Random RNG = new Random();
    private static final int CUTOFF = 16;
    private QuickSort(){}

    public static void sort(int[] a){ sort(a, null); }

    public static void sort(int[] a, Metrics m){
        if (a == null || a.length < 2) return;
        ArrayUtil.shuffle(a);
        sort(a, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int lo, int hi, Metrics m){
        while (lo < hi) {
            if (hi - lo + 1 <= CUTOFF) { insertion(a, lo, hi, m); return; }
            int p = lo + RNG.nextInt(hi - lo + 1);
            ArrayUtil.swap(a, p, hi); if (m != null) m.swaps++;
            int pivot = a[hi], i = lo - 1;
            for (int j = lo; j < hi; j++) {
                if (m != null) m.comparisons++;
                if (a[j] <= pivot) { i++; ArrayUtil.swap(a, i, j); if (m != null) m.swaps++; }
            }
            ArrayUtil.swap(a, i + 1, hi); if (m != null) m.swaps++;
            int mid = i + 1, left = mid - lo, right = hi - mid;
            if (left < right) { if (m != null) m.push(); sort(a, lo, mid - 1, m); if (m != null) m.pop(); lo = mid + 1; }
            else { if (m != null) m.push(); sort(a, mid + 1, hi, m); if (m != null) m.pop(); hi = mid - 1; }
        }
    }

    private static void insertion(int[] a, int lo, int hi, Metrics m){
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i], j = i - 1;
            while (j >= lo && a[j] > key) { a[j + 1] = a[j]; j--; if (m != null) m.comparisons++; }
            if (j >= lo && m != null) m.comparisons++;
            a[j + 1] = key;
        }
    }
}
