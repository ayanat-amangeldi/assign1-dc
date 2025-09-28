package edu.aitu.dsa.algo.select;

import java.util.Arrays;

public final class DeterministicSelect {
    private DeterministicSelect() {}

    public static int select(int[] a, int k) {
        if (a == null) throw new IllegalArgumentException("array is null");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        return select(a, 0, a.length - 1, k);
    }

    private static int select(int[] a, int lo, int hi, int k) {
        while (true) {
            if (lo == hi) return a[lo];
            int pivot = medianOfMediansPivot(a, lo, hi);
            int p = partition(a, lo, hi, pivot);
            int rank = p - lo;
            if (k == rank) return a[p];
            else if (k < rank) hi = p - 1;
            else { k -= rank + 1; lo = p + 1; }
        }
    }

    private static int medianOfMediansPivot(int[] a, int lo, int hi) {
        int n = hi - lo + 1;
        if (n <= 5) {
            Arrays.sort(a, lo, hi + 1);
            return a[lo + n / 2];
        }
        int groups = (n + 4) / 5;
        int[] meds = new int[groups];
        for (int g = 0; g < groups; g++) {
            int s = lo + g * 5;
            int e = Math.min(s + 4, hi);
            Arrays.sort(a, s, e + 1);
            meds[g] = a[s + (e - s) / 2];
        }
        return select(meds, groups / 2);
    }

    private static int partition(int[] a, int lo, int hi, int pivot) {
        int pi = lo;
        for (; pi <= hi; pi++) if (a[pi] == pivot) break;
        swap(a, pi, hi);
        int i = lo;
        for (int j = lo; j < hi; j++) if (a[j] < pivot) swap(a, i++, j);
        swap(a, i, hi);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
