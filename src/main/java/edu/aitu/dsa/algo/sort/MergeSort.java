package edu.aitu.dsa.algo.sort;

import edu.aitu.dsa.util.InsertionSort;
import edu.aitu.dsa.metrics.Metrics;

public final class MergeSort {
    private static final int CUTOFF = 32;
    private MergeSort(){}

    public static void sort(int[] a){ sort(a, null); }

    public static void sort(int[] a, Metrics m){
        if (a == null || a.length < 2) return;
        int[] buf = new int[a.length];
        if (m != null) m.alloc(a.length);
        sort(a, 0, a.length - 1, buf, m);
    }

    private static void sort(int[] a, int lo, int hi, int[] buf, Metrics m){
        if (hi - lo + 1 <= CUTOFF) { InsertionSort.sort(a, lo, hi); return; }
        int mid = (lo + hi) >>> 1;
        if (m != null) m.push();
        sort(a, lo, mid, buf, m);
        sort(a, mid + 1, hi, buf, m);
        if (m != null) m.pop();
        if (a[mid] <= a[mid + 1]) return;
        merge(a, lo, mid, hi, buf, m);
    }

    private static void merge(int[] a, int lo, int mid, int hi, int[] buf, Metrics m){
        int n1 = mid - lo + 1;
        System.arraycopy(a, lo, buf, 0, n1);
        int i = 0, j = mid + 1, k = lo;
        while (i < n1 && j <= hi) {
            if (m != null) m.comparisons++;
            if (buf[i] <= a[j]) a[k++] = buf[i++]; else a[k++] = a[j++];
        }
        while (i < n1) a[k++] = buf[i++];
    }
}
